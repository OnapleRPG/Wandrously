package com.onaple.wandrously;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.inject.Inject;
import java.util.logging.Logger;

@Plugin(id = "wandrously", name = "Wandrously", version = "0.1.0")
public class Wandrously {
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
		getLogger().info("WANDROUSLY initialized.");
	}
}
