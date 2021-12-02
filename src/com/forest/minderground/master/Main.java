package com.forest.minderground.master;

import com.forest.minderground.UndergroundServer;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.net.Administration.*;
import mindustry.world.blocks.storage.*;

// -----------------------------------------------------------------
// | THIS IS NOT COMPLETE AND COMMENTS SHOULD ACT AS A REFERENCE!! |
// -----------------------------------------------------------------

public class Main extends Plugin {
    // UndergroundServer variable
    public underServ UndergroundServer;

    // Called when game initializes
    @Override
    public void init() {
        // Start server for communication
        this.underServ = new UndergroundServer();

        // On tick event
        Events.on(Trigger.update.class, event -> {
            // Ping underground server to see if anything
            // needs to be done
            //if() {
                // From the list of mapped coordinates remove an
                // item from the target inventory and
                // ping the server saying that <item> should be put
                // into the target conveyor

                // send a message
            //}
        });
    }

    // register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("reactors", "List all thorium reactors in the map.", args -> {
            for(int x = 0; x < Vars.world.width(); x++) {
                for(int y = 0; y < Vars.world.height(); y++) {
                    // loop through and log all found reactors
                    // make sure to only log reactor centers
                    if(Vars.world.tile(x, y).block() == Blocks.thoriumReactor && Vars.world.tile(x, y).isCenter()) {
                        Log.info("Reactor at @, @", x, y);
                    }
                }
            }
        });
    }

    // Register client commands
    @Override
    public void registerClientCommands(CommandHandler handler) {

        // Register the command that connects the player to the underground
        handler.<Player>register("underground", "A simple ping command that echoes a player's text.", (player) -> {
            Call.connect(player.con, "0.0.0.0", 9999);
        });

        // register a whisper command which can be used to send other players messages
        handler.<Player>register("whisper", "<player> <text...>", "Whisper text to another player.", (args, player) -> {
            // find player by name
            Player other = Groups.player.find(p -> p.name.equalsIgnoreCase(args[0]));

            // give error message with scarlet-colored text if player isn't found
            if(other == null) {
                player.sendMessage("[scarlet]No player by that name found!");
                return;
            }

            // send the other player a message, using [lightgray] for gray text color and [] to reset color
            other.sendMessage("[lightgray](whisper) " + player.name + ":[] " + args[1]);
        });
    }
}
