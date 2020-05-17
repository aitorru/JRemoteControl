package com.JRCon;

import com.cryp.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        File fileUpdater = new File("LatetsUpdate.txt");
        if (!fileUpdater.exists()) {
            try {
                FileWriter DateWriter = new FileWriter(fileUpdater);
                String s = java.time.LocalDate.now().toString();
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
                char[] timeChar = ReadedTime.toCharArray();
                String dayS = String.valueOf(timeChar[8]);
                dayS = dayS + String.valueOf(timeChar[9]);
                int dayI = Integer.valueOf(dayS);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        GenerateKeys gk = new GenerateKeys();
        gk.checkLogic();
        Thread cl = new Thread(new CommandListener());
        cl.start();
    }
}
