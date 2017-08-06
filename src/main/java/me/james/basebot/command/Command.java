package me.james.basebot.command;

import java.util.*;
import java.util.logging.*;
import sx.blah.discord.handle.obj.*;

public abstract class Command
{
    private static HashMap< String, Class< Command > > cmds = new HashMap<>();

    public static void registerClass( String name, Class clazz )
    {
        cmds.putIfAbsent( name, clazz );
        Logger.getLogger( "BaseBot" ).info( "Registered command '" + name + "'." );
    }

    public static Command getCommand( String name )
    {
        try
        {
            return ( cmds.containsKey( name ) ? cmds.get( name ).newInstance() : null );
        } catch ( InstantiationException | IllegalAccessException e )
        {
            e.printStackTrace();
        }
        return null;
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

    public abstract String doCommand( String[] args, IUser issuer, IChannel issuerChan );
}
