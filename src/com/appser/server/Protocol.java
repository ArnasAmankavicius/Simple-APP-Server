package com.appser.server;

public class Protocol
{

    // Related to database communications
    public static final int db_EMPTY = 404;
    public static final int UPDATE_db = 223;
    // ----------------------------------

    // Related to file transfer
    public static final int FILE_TRANSFER = 224;
    public static final int NO_FILE = 225;
    public static final int DOWNLOAD_FAIL = 440;
    // -------------------------


    public static final int DATA = 234;
    public static final int OK = 900;
    public static final int ERR = 1010;

}
