package com.appser.server;

import com.appser.Config;
import com.appser.utils.Debug;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private ServerSocket serverSocket;

    private static ArrayList<Client> clients;

    private boolean isRunning = false;

    public Server() { }

    public void start() throws IOException
    {
        clients = new ArrayList<>();
        serverSocket = new ServerSocket(Config.PORT);
        serverSocket.setSoTimeout(5000);
        isRunning = true;
    }

    public void listen()
    {
        while(isRunning)
        {
            try
            {
                Socket socket = serverSocket.accept();
                if(socket != null)
                {
                    Debug.log("Client connected!");
                    clients.add(new Client(socket));
                }
            } catch (IOException e)
            {
                Database.update_db();
            }
        }
    }

    public static void removeClient(Client c)
    {
        clients.remove(c);
    }

    public void stop() throws IOException
    {
        serverSocket.close();
    }
}
