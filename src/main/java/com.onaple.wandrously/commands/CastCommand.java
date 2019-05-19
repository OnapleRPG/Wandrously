package com.onaple.wandrously.commands;

import com.onaple.wandrously.Wandrously;
import com.onaple.wandrously.actions.FireBallAction;
import com.onaple.wandrously.actions.SpellAction;
import com.onaple.wandrously.exceptions.MissingSpellParameterException;
import com.onaple.wandrously.exceptions.SpellNotFoundException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class CastCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> spellNameOptional = args.getOne("spellName");
        if (!spellNameOptional.isPresent()) {
            src.sendMessage(Text.of("You need to specify a spell name."));
            return CommandResult.empty();
        }

        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Command executor must be a player."));
            return CommandResult.empty();
        }
        Player player = (Player)src;

        try {
            SpellAction spellAction = SpellAction.getMatchingSpellAction(spellNameOptional.get());
            spellAction.setPlayer(player);
            spellAction.setWorld(player.getWorld());
            spellAction.cast();
        } catch (SpellNotFoundException | MissingSpellParameterException e) {
            src.sendMessage(Text.of(e.getMessage()));
        }

        return CommandResult.success();
    }
}
