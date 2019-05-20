package com.onaple.wandrously.actions;

import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import com.onaple.wandrously.exceptions.SpellNotFoundException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.World;

public class SpellAction {
    public static SpellAction getMatchingSpellAction(String spellName) throws SpellNotFoundException {
        switch (spellName.toLowerCase()) {
            case "spike":
            case "spikes":
                return new SpikeAction();
            default:
                throw new SpellNotFoundException(spellName.toLowerCase());
        }
    }

    protected World world;
    protected Living living;
    protected double damage = 1;
    protected double knockback = 1;
    protected double speed = 5;
    protected double range = 7;
    protected double duration = 2;

    public void setWorld(World world) {
        this.world = world;
    }
    public void setLiving(Living living) {
        this.living = living;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
    public void setKnockback(double knockback) {
        this.knockback = knockback;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setRange(double range) {
        this.range = range;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void cast() throws MissingSpellParameterException {

    }
}
