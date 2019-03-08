package com.appser.server;

import java.io.File;
import java.util.ArrayList;

public class Database
{
    private static ArrayList<FileInfo> files = new ArrayList<>();

    public static void update_db()
    {
        File folder = new File("test1");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null)
            return;
        for(File f : listOfFiles)
        {
            if(!doesContain(f))
            {
                FileInfo info = new FileInfo();
                info.name = f.getName();
                info.path = f.getPath();
                info.file = f;
                info.size = f.length();
                files.add(info);
            }
        }
    }

    private static boolean doesContain(File file)
    {
        for(FileInfo fi: files)
        {
            if(fi.file.equals(file))
                return true;
        }
        return false;
    }

    public static ArrayList<FileInfo> getDownloadableFiles()
    {
        return files;
    }
}
