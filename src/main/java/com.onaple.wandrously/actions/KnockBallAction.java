package com.onaple.wandrously.actions;

import com.flowpowered.math.vector.Vector3d;
import com.onaple.wandrously.data.beans.KnockBallBean;
import com.onaple.wandrously.data.dao.KnockBallDao;
import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.event.entity.CollideEntityEvent;

import java.util.Optional;

public class KnockBallAction extends SpellAction {
    @Override
    public void cast() throws MissingSpellParameterException {
        if (player == null) {
            throw new MissingSpellParameterException("Player");
        }

        player.launchProjectile(Snowball.class).ifPresent(projectile -> {
            KnockBallBean knockBall = new KnockBallBean();
            knockBall.setUuid(projectile.getUniqueId().toString());
            knockBall.setCasterUuid(player.getUniqueId().toString());
            KnockBallDao.addKnockBall(knockBall);
        });
    }

    public static void applyEffect(Snowball snowball, CollideEntityEvent event) {
        Optional<KnockBallBean> knockBallBeanOptional = KnockBallDao.getKnockBallByUuid(snowball.getUniqueId().toString());
        knockBallBeanOptional.ifPresent(knockBall -> {
            for (Entity entity : event.getEntities()) {
                if (!knockBall.getCasterUuid().equals(entity.getUniqueId().toString())) {
                    Vector3d velocity = entity.getLocation().getPosition().sub(snowball.getLocation().getPosition());
                    velocity.add(0, -velocity.getY() + 0.25f, 0);
                    entity.setVelocity(velocity);
                }
            }
            KnockBallDao.deleteMonsterByUuid(snowball.getUniqueId().toString());
        });
        snowball.remove();
    }
}
