package me.james.basebot.command;

import java.util.*;
import java.util.logging.*;
import sx.blah.discord.handle.obj.*;

public abstract class Command
{
    private static HashMap< String, Command > cmds = new HashMap<>();

    public static void registerClass( String name, Command cmd )
    {
        cmds.putIfAbsent( name, cmd );
        Logger.getLogger( "BaseBot" ).info( "Registered command '" + name + "'." );
    }

    public static Command getCommand( String name )
    {
        return cmds.getOrDefault( name, null );
    }

    public boolean isPrivateMessageRequired()
    {
        return false;
    }

    public boolean isBotOwnerSenderRequired()
    {
        return false;
    }

    public boolean isGuildOwnerSenderRequired()
    {
        return false;
    }

    public abstract String doCommand( String[] args, IUser issuer, IChannel issuerChan, IMessage msg );
}
