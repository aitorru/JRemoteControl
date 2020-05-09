package com.JRCon;

import com.JRCon.CommandListener;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Thread cl = new Thread(new CommandListener());
        cl.start();
    }
}
