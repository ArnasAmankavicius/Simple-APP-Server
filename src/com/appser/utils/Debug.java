package com.appser.utils;

public class Debug
{
    public static void log(String log) { System.out.println("[Log]: " + log); }
    public static void warn(String warn) { System.out.println("[Warn]: " + warn); }
    public static void err(String err) { System.err.println("[Err]: " + err); }
    public static void print(String print) { System.out.println(print); }
}
