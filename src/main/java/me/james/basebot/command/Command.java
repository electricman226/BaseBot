package me.james.basebot.command;

import java.util.*;
import java.util.logging.*;
import sx.blah.discord.handle.obj.*;

public abstract class Command
{
    private static HashMap< String, Command > cmds = new HashMap<>();

    @Deprecated
    public static void registerClass( String name, Command cmd )
    {
        registerCommand( name, cmd );
    }

    public static void registerCommand( String name, Command cmd )
    {
        cmds.putIfAbsent( name, cmd );
        Logger.getLogger( "BaseBot" ).info( "Registered command '" + name + "'." );
    }

    public static void removeCommand( String name )
    {
        Logger.getLogger( "BaseBot" ).info( "Removing command '" + name + "'." );
        cmds.remove( name );
    }

    public static void removeCommand( Command cmd )
    {
        Logger.getLogger( "BaseBot" ).info( "Removing command class '" + cmd.getClass().getSimpleName() + "'." );
        cmds.values().remove( cmd );
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
