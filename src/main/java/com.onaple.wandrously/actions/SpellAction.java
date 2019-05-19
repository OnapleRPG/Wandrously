package com.onaple.wandrously.actions;

import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import com.onaple.wandrously.exceptions.SpellNotFoundException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class SpellAction {
    public static SpellAction getMatchingSpellAction(String spellName) throws SpellNotFoundException {
        switch (spellName.toLowerCase()) {
            case "fireball":
                return new FireBallAction();
            case "knockball":
                return new KnockBallAction();
            default:
                throw new SpellNotFoundException(spellName.toLowerCase());
        }
    }

    protected World world;
    protected Player player;

    public void setWorld(World world) {
        this.world = world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void cast() throws MissingSpellParameterException {

    }
}
