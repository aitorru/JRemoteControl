package com.JRCon;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;
import com.tree.FileAssert;
import com.cryp.AsymmetricCryptography;
import com.logger.AdminLogger;

public class CommandListener extends Thread implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(CommandListener.class.getName());
    private Logger log = new AdminLogger(LOGGER, "logs/server.log").getLOGGER();
    private static String OS = System.getProperty(("os.name").toLowerCase());
    private boolean running = true;

    @Override
    public void run() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8008), 0);
            server.createContext("/api", new CommandListener());
            server.setExecutor(null); // creates a default executor
            server.start();
            log.info("Servidor encendido. Escuchando");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            LOGGER.info(requestURI.getAuthority() + " Issued a command");
            String response = "";
            AsymmetricCryptography as = new AsymmetricCryptography();
            PrivateKey privateKey = as.getPrivate("KeyPair/privateKey");
            String cmd = as.decryptText(query, privateKey);
            if (cmd.startsWith("!")) {
                response = executeModule(query);
            } else if (cmd.startsWith("tree")) {
                response = new FileAssert().printDirectoryTree(new File(cmd.split(" ")[1]));
            } else if(cmd.startsWith("shutdown")) {
                response = "Goodbye";
            } else {
                response = executeCommand(query);
            }
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            if(!running){
                log.info("Shut down");
                System.exit(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String executeCommand(String cmd) {
        try {

            Runtime.getRuntime().exec(cmd);
            LOGGER.log(Level.INFO, "Ejecutando comando: " + cmd);
            return "Normal runner: Command ran successfully";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, e.toString());
        }

        return "Normal runner: Command failed";
    }

    public String executeModule(String q) {
        String rute = "Modules" + System.lineSeparator() + q;
        try {
            if (OS.indexOf("win") >= 0) {
                rute = rute + ".exe";
                Runtime.getRuntime().exec(rute);
            } else if (OS.indexOf("nux") >= 0) {
                rute = rute + ".out";
                Runtime.getRuntime().exec(rute);
            }
            return "Module runner: Command ran successfully";
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.log(Level.SEVERE, e.toString());
        }

        return "Module runner: Command failed";
    }
}