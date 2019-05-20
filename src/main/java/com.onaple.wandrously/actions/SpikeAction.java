package com.onaple.wandrously.actions;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.onaple.wandrously.Wandrously;
import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.extent.Extent;

import java.util.concurrent.TimeUnit;

public class SpikeAction extends SpellAction {
    @Override
    public void cast() throws MissingSpellParameterException {
        if (living == null) {
            throw new MissingSpellParameterException("Entity");
        }
        popSpikeRange();
    }

    private void popSpikeRange() {
        double rotation = living.getHeadRotation().getY();
        Vector2d rotationVector = Vector2d.createDirectionDeg(rotation);
        Vector3d directionVector = new Vector3d(-rotationVector.getY()*1.25f, 0, rotationVector.getX()*1.25f);
        Vector3d currentPosition = living.getLocation().getPosition().add(directionVector);
        int i = 0;
        do {
            final Vector3d temporaryPosition = currentPosition;
            Task.builder().execute(() -> popSpike(temporaryPosition)).delay(4500/(int)speed * i, TimeUnit.MILLISECONDS).name("Poping spike range spell").submit(Wandrously.getInstance());
            currentPosition = currentPosition.add(directionVector);
            i++;
        } while (i < range);
    }

    private void popSpike(Vector3d initialPosition) {
        Vector3d currentPosition = initialPosition.sub(0, 3, 0);
        int i = 0;
        do {
            final Vector3d temporaryPosition = currentPosition;
            Task.builder().execute(() -> popSpikeElement(temporaryPosition)).delay((int)(230 - speed * 10) * i, TimeUnit.MILLISECONDS).name("Poping spike spell").submit(Wandrously.getInstance());
            currentPosition = currentPosition.add(0, 1, 0);
            i++;
        } while (i < range);
    }

    private void popSpikeElement(Vector3d position) {
        ParticleEffect particle = ParticleEffect.builder().type(ParticleTypes.BREAK_BLOCK).quantity(50).build();
        world.spawnParticles(particle, position);
        Vector3i minPosition = new Vector3i(position.getX()-1, position.getY(), position.getZ()-1);
        Vector3i maxPosition = new Vector3i(position.getX()+1, position.getY(), position.getZ()+1);
        Extent spikeArea = world.getExtentView(minPosition, maxPosition);
        spikeArea.getEntities().forEach(entity -> {
            if (!entity.equals(living)) {
                applyEffect(entity);
            }
        });
    }

    private void applyEffect(Entity entity) {
        PotionEffect slowEffect = PotionEffect.builder()
                .potionType(PotionEffectTypes.SLOWNESS)
                .duration((int)duration)
                .build();
        entity.getOrCreate(PotionEffectData.class).ifPresent(effects -> {
            effects.addElement(slowEffect);
            entity.offer(effects);
        });
        entity.setVelocity(new Vector3d(0, 0.75f, 0));
        if (damage > 0) {
            entity.damage(damage, DamageSource.builder().magical().type(DamageTypes.MAGIC).build());
        }
    }
}
