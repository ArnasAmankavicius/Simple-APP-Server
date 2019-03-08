package com.appser.main;

import com.appser.server.Database;
import com.appser.server.Server;
import com.appser.utils.Debug;

import java.io.IOException;

public class Main
{
    private Server server;
    private Main()
    {
        server = new Server();

        Debug.log("Starting app server.");

        try{ server.start(); }
        catch(IOException e)
        {
            Debug.err("Failed to start.");
            Debug.err(e.getMessage());
        }

        Debug.log("Server Started.");
        Debug.log("Listening commenced.");

        server.listen();
        Debug.log("Stopping the server.");

        try{ server.stop(); }
        catch (IOException e)
        {
            Debug.err("Failed to stop.");
            Debug.err(e.getMessage());
        }

        Debug.log("App server stopped.");
    }

    public static void main(String[] args)
    {
        new Main();
    }
}
