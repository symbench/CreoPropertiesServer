package org.symbench.creopropertiesserver.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.symbench.creopropertiesserver.utils.LoggerFactory;


public class JSONRequestHandler implements HttpHandler {
    private JSONHandler handler = new JSONHandler();

    private static final Logger logger = LoggerFactory.getLogger(JSONRequestHandler.class.getName());

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len;
        while ((len = is.read(buffer))>0) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        String data = bos.toString(StandardCharsets.UTF_8);
        logger.info("Request String: " + data);

        // pass the data to the handler and receive a response
        String response = handler.handleRequest(data);

        // format and return the response to the user
        t.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        t.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
