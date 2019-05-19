package com.onaple.wandrously.exceptions;

public class SpellNotFoundException extends Throwable {
    public SpellNotFoundException(String spellName) {
        super("Spell with name " + spellName + " was not found.");
    }
}
