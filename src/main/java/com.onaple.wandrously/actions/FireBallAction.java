package com.onaple.wandrously.actions;

import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import org.spongepowered.api.entity.projectile.explosive.fireball.Fireball;

public class FireBallAction extends SpellAction {
    @Override
    public void cast() throws MissingSpellParameterException {
        if (player == null) {
            throw new MissingSpellParameterException("Player");
        }
        player.launchProjectile(Fireball.class).ifPresent(projectile -> {
            // TODO: Search how to implement Fireball
        });
    }
}
