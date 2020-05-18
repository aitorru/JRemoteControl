package com.JRCon;

import com.cryp.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private static String OS = System.getProperty(("os.name").toLowerCase());
    public static void main(String[] args) {
        File fileUpdater = new File("LatetsUpdate");
        if (!fileUpdater.exists()) {
            try {
                FileWriter DateWriter = new FileWriter(fileUpdater);
                long unixTime = Instant.now().getEpochSecond();
                String s = String.valueOf(unixTime);
                DateWriter.append(s);
                DateWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Scanner LatestUpdate;
            try {
                LatestUpdate = new Scanner(fileUpdater);
                String ReadedTime = LatestUpdate.next();
                long dayL = Long.valueOf(ReadedTime);
                long resta = Instant.now().getEpochSecond() - dayL;
                if(resta >= 604800){ // Una semana sin actualizar
                    if(OS.indexOf("win") >= 0){
                        Runtime.getRuntime().exec("Cpp_Updater\\updater.exe");
                        System.exit(0);
                    } else if (OS.indexOf("nux") >= 0){
                        Runtime.getRuntime().exec("Cpp_Updater/updater.out");
                        System.exit(0);
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        GenerateKeys gk = new GenerateKeys();
        gk.checkLogic();
        Thread cl = new Thread(new CommandListener());
        cl.start();
    }
}
