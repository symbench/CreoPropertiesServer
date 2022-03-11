package org.symbench.creointerferenceserver.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HTTPRequestHandler implements HttpHandler {
    private InterferenceRequestHandler interferenceRequestHandler = new InterferenceRequestHandler();

    private static Gson gson = getGSONInstance();

    private static Gson getGSONInstance() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        return builder.create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        if("GET".equals(exchange.getRequestMethod())) {
            System.out.println("Apple Ball Cat");
            String stringResponse = gson.toJson("Can't handle get request", String.class);
            exchange.sendResponseHeaders(200, stringResponse.getBytes(StandardCharsets.UTF_8).length);
            exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");

            OutputStream os = exchange.getResponseBody();
            os.write(stringResponse.getBytes(StandardCharsets.UTF_8));
            os.close();
        } else if("POST".equals(exchange.getRequestMethod())){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;

            while((len = is.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
//            InterferenceRequest request = gson.fromJson(bos.toString(), InterferenceRequest.class);
            try {
                String stringResponse = gson.toJson(this.interferenceRequestHandler.handle(null));
                exchange.sendResponseHeaders(200, stringResponse.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");

                OutputStream os = exchange.getResponseBody();
                os.write(stringResponse.getBytes(StandardCharsets.UTF_8));
                os.close();
            } catch (Exception e) {
                System.out.println(e);
                exchange.sendResponseHeaders(500, e.getMessage().getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseHeaders().set("Content-Type", "application/json, charset=UTF-8");
                OutputStream os = exchange.getResponseBody();
                os.write(e.getMessage().getBytes(StandardCharsets.UTF_8));
                os.close();
            }
        }

    }
}
