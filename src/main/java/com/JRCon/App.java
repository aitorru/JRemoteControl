package com.JRCon;

import com.cryp.*;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Scanner;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

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
                if (resta >= 604800) { // One week without updating
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
    public static void sendREQ(){
        AsymmetricCryptography as;
        try {
            as = new AsymmetricCryptography();               
            Scanner consoleReader = new Scanner(System.in);
            System.out.println("Enter IP:\r");
            String IP = consoleReader.nextLine();
            System.out.println("Enter PORT:\r");
            String PORT = consoleReader.nextLine();
            System.out.println("Enter command to be issued:\r");
            String query = consoleReader.nextLine();
            System.out.println("Enter patch to public key");
            String rute = "";
            File publicFile;
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal ==JFileChooser.APPROVE_OPTION){
                publicFile = chooser.getSelectedFile();
                rute = publicFile.getAbsolutePath();
            }
            PublicKey publickey = as.getPublic(rute);
            String encripted = as.encryptText(query, publickey);
            URL url = new URL("http://" + IP + ":" + PORT + "/api?" + encripted);
            System.out.println(url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if(status==200){
                System.out.println("Response code 200. Command filed.");
                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String tmpReader;
                while ((tmpReader = bf.readLine()) != null){
                    System.out.println(tmpReader);
                }
                bf.close();
            }
            consoleReader.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            
        }
        if (args.length == 0) {
            System.out.println("Running on slave");
            checkUpdate();
            GenerateKeys gk = new GenerateKeys();
            gk.checkLogic();
            Thread cl = new Thread(new CommandListener());
            cl.start();
        } else if (args[0].equals("admin")) {
            System.out.println("Running on admin");
            sendREQ();
        } else {
            System.out.println("Error: Worng prefix");
            System.out.println("Usage:\n\t0 args or admin");
        }
    }
}
