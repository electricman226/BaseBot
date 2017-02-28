package me.james.basebot;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.logging.*;
import sx.blah.discord.api.*;
import sx.blah.discord.api.events.*;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.util.*;

public abstract class BaseBot
{
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String token;
    private final Logger LOGGER = Logger.getLogger( "BaseBot" );
    private IDiscordClient bot;

    public BaseBot( String token )
    {
        this.token = token;
        getLogger().setUseParentHandlers( false );
        getLogger().addHandler( new Handler()
        {
            @Override
            public void publish( LogRecord record )
            {
                if ( getFormatter() == null )
                {
                    setFormatter( new SimpleFormatter() );
                }

                try
                {
                    String message = getFormatter().format( record );
                    if ( record.getLevel().intValue() >= Level.WARNING.intValue() )
                    {
                        System.err.write( message.getBytes() );
                    } else
                    {
                        System.out.write( message.getBytes() );
                    }
                } catch ( Exception exception )
                {
                    reportError( null, exception, ErrorManager.FORMAT_FAILURE );
                }

            }

            @Override
            public void close() throws SecurityException {}

            @Override
            public void flush() {}
        } );
        getLogger().info( "BaseBot init" );
        try
        {
            login();
            getBot().getDispatcher().registerListener( this );
        } catch ( DiscordException e )
        {
            getLogger().severe( "Unable to login with given token. (" + e.getErrorMessage() + ")" );
            System.exit( -1 );
        }
    }

    public BaseBot( File tokenFile )
    {
        this( getTokenFromFile( tokenFile ) );
    }

    private static String getTokenFromFile( File tokenFile )
    {
        try
        {
            return Files.readAllLines( Paths.get( tokenFile.getPath() ) ).get( 0 );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject fileToJSON( String path )
    {
        try
        {
            return new JsonParser().parse( new FileReader( path ) ).getAsJsonObject();
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    @EventSubscriber
    public void onReady( ReadyEvent e )
    {
        getLogger().info( "Discord4J sent ready event." );
        init();
    }

    /**
     * This is called once Discord4J issues the {@link ReadyEvent}.
     *
     * @see ReadyEvent
     */
    public abstract void init();

    private void login() throws DiscordException
    {
        getLogger().info( "Logging in..." );
        bot = new ClientBuilder().withToken( token ).login();
    }

    public Logger getLogger()
    {
        return LOGGER;
    }

    public IDiscordClient getBot()
    {
        return bot;
    }

    public Gson getGSON()
    {
        return GSON;
    }

}
