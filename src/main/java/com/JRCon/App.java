package com.JRCon;

import com.cryp.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;

/**
 * Hello world!
 *
 */
public class App {
    private static String OS = System.getProperty(("os.name").toLowerCase());

    public static void checkUpdate() {
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
                if (resta >= 604800) { // Una semana sin actualizar
                    if (OS.indexOf("win") >= 0) {
                        Runtime.getRuntime().exec("Cpp_Updater\\updater.exe");
                        System.exit(0);
                    } else if (OS.indexOf("nux") >= 0) {
                        Runtime.getRuntime().exec("Cpp_Updater/updater.out");
                        System.exit(0);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Running on slave");
            checkUpdate();
            GenerateKeys gk = new GenerateKeys();
            gk.checkLogic();
            Thread cl = new Thread(new CommandListener());
            cl.start();
        } else if (args[0].equals("admin")) {
            System.out.println("Running on admin");
            AsymmetricCryptography as;
            try {
                as = new AsymmetricCryptography();
                PublicKey publickey = as.getPublic("../KeyPair/publicKey");
                System.out.println(as.encryptText("touch man", publickey));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            //WIP
        } else {
            System.out.println("Error: Worng prefix");
            System.out.println("Usage:\n\t0 args or admin");
        }
    }
}
