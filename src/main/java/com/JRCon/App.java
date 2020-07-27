package com.JRCon;

import com.cryp.*;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Scanner;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import java.net.*;

/**
 * Server Starter
 *
 */
public class App {
    private static String OS = System.getProperty(("os.name").toLowerCase());
    private static SystemTray tray = SystemTray.getSystemTray();
    private static Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
    private static TrayIcon trayIcon = new TrayIcon(image, "JRemoteControl");

    public static void checkUpdate() {
        File fileUpdater = new File("LatestUpdate");
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
                LatestUpdate.close();
                long dayL = Long.valueOf(ReadedTime);
                long resta = Instant.now().getEpochSecond() - dayL;
                if (resta >= 604800) { // One week without updating
                    File f = new File(".flag");
                    f.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void sendFile() {
        AsymmetricCryptography as;
        try {
            as = new AsymmetricCryptography();
            Scanner consoleReader = new Scanner(System.in);
            System.out.println("Enter IP:\r");
            String IP = consoleReader.nextLine();
            System.out.println("Enter PORT:\r");
            String PORT = consoleReader.nextLine();
            System.out.println("Enter patch to public key");
            String rute = "";
            File publicFile;
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                publicFile = chooser.getSelectedFile();
                rute = publicFile.getAbsolutePath();
            }
            PublicKey publickey = as.getPublic(rute);

            String charset = "UTF-8";
            String CRLF = "\r\n";
            String boundary = Long.toHexString(System.currentTimeMillis());

            System.out.println("\nEnter patch to file to send");
            File FileToSend = null;
            chooser = null;
            chooser = new JFileChooser();
            returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                FileToSend = chooser.getSelectedFile();
            }
            System.out.println("Enter the route to the file on server:\r");
            String route = consoleReader.nextLine();
            consoleReader.close();

            String routecry = as.encryptText(route, publickey);

            String url = "http://" + IP + ":" + PORT + "/api?" + routecry;

            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream output = connection.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);) {
                Files.copy(FileToSend.toPath(), output);
                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

                output.flush(); // Important before continuing with writer!
                writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

            }
            int responseCode = ((HttpURLConnection) connection).getResponseCode();
            if (responseCode == 200) {
                System.out.println("Response code 200. Upload done.");
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String tmpReader;
                while ((tmpReader = bf.readLine()) != null) {
                    System.out.println(tmpReader);
                }
                bf.close();
            } else {
                System.out.println("Response code 500. Server error.");
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String tmpReader;
                while ((tmpReader = bf.readLine()) != null) {
                    System.out.println(tmpReader);
                }
                bf.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void sendREQ() {
        AsymmetricCryptography as;
        boolean loop = true;
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
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                publicFile = chooser.getSelectedFile();
                rute = publicFile.getAbsolutePath();
            }
            PublicKey publickey = as.getPublic(rute);
            while (loop) {
                String encripted = as.encryptText(query, publickey);
                URL url = new URL("http://" + IP + ":" + PORT + "/api?" + encripted);
                System.out.println("Request done. Sending (" + query + ")...");
                System.out.println("_______________________\n");
                System.out.println("http://" + IP + ":" + PORT + "/api?" + encripted);
                System.out.println("_______________________");
                Thread.sleep(10);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int status = con.getResponseCode();
                if (status == 200) {
                    System.out.println("Response code 200. Command filed.");
                    BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String tmpReader;
                    while ((tmpReader = bf.readLine()) != null) {
                        System.out.println(tmpReader);
                    }
                    bf.close();
                }

                System.out.print("Do you want to send more commands to the same host? Y/N: ");
                String entry = consoleReader.nextLine();
                if (entry.equals("Y") || entry.equals("y")) {
                    System.out.println("Enter command to be issued:\r");
                    query = consoleReader.nextLine();
                } else if ((entry.equals("N") || entry.equals("n"))) {
                    loop = false;
                } else {
                    System.out.println("Not understood. Exiting...");
                }
            }
            consoleReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {

        }
        if (args.length == 0) {
            if (SystemTray.isSupported()) {
                try {
                    new App().firstRun("Server is running!");
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("System tray not supported!");
            }
            System.out.println("Running on slave");
            checkUpdate();
            GenerateKeys gk = new GenerateKeys();
            gk.checkLogic();
            Thread cl = new Thread(new CommandListener());
            cl.start();
        } else if (args[0].equals("admin")) {
            System.out.println("Running on admin");
            sendREQ();
        } else if (args[0].equals("file")) {
            System.out.println("Sending file");
            sendFile();
        } else {
            System.out.println("Error: Worng prefix");
            System.out.println("Usage:\n\t0 args or admin (For )\nfile (for file sending)");
        }
    }

    public void firstRun(String text) throws AWTException {
        // Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        // Set tooltip text for the tray icon
        trayIcon.setToolTip("JRemoteControl");

        MenuItem exitItem = new MenuItem("Exit");
        final PopupMenu popup = new PopupMenu();
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);

        tray.add(trayIcon);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });

        trayIcon.displayMessage("JRemoteControl", text, MessageType.INFO);

    }

    public void trayNotification(String text) {
        trayIcon.displayMessage("JRemoteControl", text, MessageType.INFO);
    }
}
