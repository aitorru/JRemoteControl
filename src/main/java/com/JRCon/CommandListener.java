package com.JRCon;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.net.httpserver.*;

import com.logger.AdminLogger;

public class CommandListener extends Thread implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(CommandListener.class.getName());
    private Logger log = new AdminLogger(LOGGER, "logs/servidor.log").getLOGGER();
    
    @Override
    public void run(){
        log.info("Servidor encendido. Escuchando");
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8008), 0);
            server.createContext("/api", new CommandListener());
            server.setExecutor(null); // creates a default executor
            server.start();
            LOGGER.info("Server running");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.toString());
        }
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "Server is running!";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}