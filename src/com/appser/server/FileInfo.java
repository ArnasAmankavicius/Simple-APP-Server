package com.appser.server;

import java.io.File;

public class FileInfo
{
    public static String META = "META-=-DATA";

    public String name;
    public String info;
    public File file;

    public String path;

    public long size;


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(META + "\n");
        builder.append("{\n");
        builder.append("\tname:" + name + ",\n");
        builder.append("\tinfo:" + info + ",\n");
        builder.append("\tsize:" + size + ",\n");
        builder.append("}");
        return builder.toString();
    }
}
