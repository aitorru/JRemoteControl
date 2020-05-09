package com.JRCon;

import com.JRCon.CommandListener;
import com.cryp.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GenerateKeys gk = new GenerateKeys();
        gk.checkLogic();
        Thread cl = new Thread(new CommandListener());
        cl.start();
    }
}
