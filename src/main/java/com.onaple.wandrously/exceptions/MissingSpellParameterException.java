package com.onaple.wandrously.exceptions;

public class MissingSpellParameterException extends Throwable {
    public MissingSpellParameterException(String spellParameter) {
        super("Spell is missing parameter " + spellParameter + ".");
    }
}
