/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.symbench.creopropertiesserver;

import com.sun.net.httpserver.HttpServer;
import org.symbench.creopropertiesserver.creo.CREOEnvironment;
import org.symbench.creopropertiesserver.http.JSONRequestHandler;
import org.symbench.creopropertiesserver.utils.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.logging.Logger;


public class App {
    private static final String ENDPOINT = "/symbench-creoson";

    private static final String PORT = "interferenceserver.port";

    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        String port = System.getProperty(PORT);
        if (port == null) {
            port = "8000";
        }

        CREOEnvironment.verifyCREOEnvironment();

        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);

        server.createContext(ENDPOINT, new JSONRequestHandler());
        logger.info("Starting server, listening on port " + port + ".");
        server.start();
    }
}