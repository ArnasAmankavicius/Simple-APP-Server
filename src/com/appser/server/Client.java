package com.appser.server;

import com.appser.utils.Debug;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class Client
{
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    private Thread t;

    private boolean isConnected;
    private boolean shouldDownload;

    private int attempt = 0;
    private int max_attempts = 5;

    public Client(Socket s)
    {
        isConnected = setup(s);
        if(isConnected)
        {
            t = new Thread(this::update);
            t.start();
        }
    }

    private boolean setup(Socket s)
    {
        try
        {
            this.s = s;
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e)
        {
            Debug.err("Failed client setup");
            Debug.err(e.getMessage());
            return false;
        }
        return true;
    }

    private void update()
    {
        while(isConnected)
        {
            try
            {
                String content = dis.readUTF();

                Debug.print("[" + s.getInetAddress() + "]: " + content);

                if(content.startsWith("updatedb"))
                {
                    ArrayList<FileInfo> fi = Database.getDownloadableFiles();
                    dos.write(Protocol.DATA);
                    StringBuffer sb = new StringBuffer();
                    for (FileInfo f : fi)
                        sb.append(f.toString() + "\n");
                    dos.flush();
                }

                if(content.startsWith("download"))
                {
                    String name = content.substring(content.indexOf(" ")).trim();
                    Debug.log(name);
                    FileInfo fileToSend = null;
                    for(FileInfo fi: Database.getDownloadableFiles())
                    {
                        if(fi.name.contains(name))
                        {
                            fileToSend = fi;
                            shouldDownload = true;
                            break;
                        }
                    }

                    if(fileToSend == null)
                    {
                        dos.writeUTF("File not found");
                    } else {

                        // Send meta-data on the file
                        dos.writeUTF(fileToSend.toString());
                        dos.writeLong(fileToSend.size);
                        dos.flush();
                        // --------------------------

                        while(!dis.readUTF().equals("ack"));
                        // Send file
                        byte[] bytes = new byte[(int)fileToSend.file.length()];
                        FileInputStream dis = new FileInputStream(fileToSend.file);
                        BufferedInputStream bis = new BufferedInputStream(dis);
                        bis.read(bytes, 0, bytes.length);

                        Debug.log("Sending file: " + name);
                        dos.write(bytes, 0, bytes.length);
                        dos.flush();
                        Debug.log("File sent");
                        // ---------
                        shouldDownload = false;
                    }
                }

            }catch(IOException e)
            {
                Server.removeClient(this);
                close();
                e.printStackTrace();
            }
        }

        close();
    }


    private void close()
    {

        try
        {
            dis.close();
            dos.close();
            if(s != null)
                s.close();
            t.join();
        } catch (Exception e)
        {
            Debug.err(e.getMessage());
        }
    }
}
