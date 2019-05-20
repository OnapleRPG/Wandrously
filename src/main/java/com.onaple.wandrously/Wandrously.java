package com.onaple.wandrously;

import com.onaple.wandrously.commands.CastCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;

@Plugin(id = "wandrously", name = "Wandrously", version = "0.1.0")
public class Wandrously {
    private static final String WANDROUSLY_PERMISSION = "wandrously.command";
    private static final String CAST_PERMISSION = "wandrously.command.cast";


    private static Wandrously instance;
    public static Wandrously getInstance() {
        return instance;
    }

    private static Logger logger;
    @Inject
    private void setLogger(Logger logger) {
        Wandrously.logger = logger;
    }
    public static Logger getLogger() {
        return logger;
    }

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
        Wandrously.instance = this;

        CommandSpec castCommand = CommandSpec.builder()
                .description(Text.of("Cast a spell"))
                .permission(CAST_PERMISSION)
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("spellName")))
                )
                .executor(new CastCommand()).build();

        CommandSpec wandrouslyCommand = CommandSpec.builder()
                .description(Text.of("Wandrously commands - spell casting utilities"))
                .permission(WANDROUSLY_PERMISSION)
                .child(castCommand, "cast")
                .build();
        Sponge.getCommandManager().register(this, wandrouslyCommand, "wandrously");

		getLogger().info("WANDROUSLY initialized.");
	}
}
