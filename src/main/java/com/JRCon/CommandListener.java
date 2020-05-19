package com.JRCon;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.*;
import com.cryp.AsymmetricCryptography;
import com.logger.AdminLogger;

public class CommandListener extends Thread implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(CommandListener.class.getName());
    private Logger log = new AdminLogger(LOGGER, "logs/servidor.log").getLOGGER();

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
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        LOGGER.info(requestURI.getAuthority() + " Issued a command");
        String response = ejecutarComando(query);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String ejecutarComando(String q) {
        try {
            AsymmetricCryptography as = new AsymmetricCryptography();
            PrivateKey privateKey = as.getPrivate("KeyPair/privateKey");
            String cmd = as.decryptText(q, privateKey);
            Runtime.getRuntime().exec(cmd);
            LOGGER.log(Level.INFO, "Ejecutando comando: " + cmd);
            return cmd;
        }  catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, e.toString());
        }
        
        return "test";
    }
}