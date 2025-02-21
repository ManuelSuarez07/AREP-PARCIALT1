package com.eci.Calculator.Server;

import com.eci.Calculator.Rest.Request;
import com.eci.Calculator.Rest.Response;
import com.eci.Calculator.Rest.Service;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class HttpServer {

    private static final Map<String, Service> services = new HashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException {
        services.put("/calculate", (req, resp) -> ReflexCalculator.calcular(req, resp));

        try (ServerSocket serverSocket = new ServerSocket(35500)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String inputLine = in.readLine();
                    if (inputLine == null) {
                        continue;
                    }

                    String[] requestParts = inputLine.split(" ");
                    String method = requestParts[0];
                    String file = requestParts[1];
                    URI resourceURI = new URI(file);

                    String response = switch (method) {
                        case "GET" ->
                            manejarReq(resourceURI, clientSocket.getOutputStream());
                        default ->
                            "Error";
                    };

                    out.println(response);
                } catch (Exception e) {
                    System.err.println("Error" + e.getMessage());
                }
            }
        }
    }

    private static String manejarReq(URI resourceURI, OutputStream out) throws IOException {
        String path = resourceURI.getPath();

        if (path.equals("/") || path.isEmpty()) {
            return obtArchivo("/index.html", out);
        }

        if (path.startsWith("/calculate")) {
            String query = resourceURI.getQuery();
            if (query == null) {
                return "Error";
            }
            Request req = new Request(query);
            Response resp = new Response();
            Service service = services.get("/calculate");
            if (service != null) {
                String responseBody = service.getValue(req, resp);
                return "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + responseBody;
            } else {
                return "Error";
            }
        }
        return "Error";
    }

    public static String obtArchivo(String path, OutputStream out) throws IOException {
        String file = path.equals("/") ? "index.html" : path.substring(1);
        String extension = file.contains(".") ? file.substring(file.lastIndexOf('.') + 1) : "";
        String filePath = "src/main/resources/" + file;
        String responseHeader = "HTTP/1.1 200 OK\r\nContent-Type: " + obtTipoArchivo(extension) + "\r\n\r\n";
        String notFoundResponse = "Error";
        try {
            File requestedFile = new File(filePath);
            if (requestedFile.exists()) {
                return responseHeader + new String(Files.readAllBytes(requestedFile.toPath()));
            } else {
                return notFoundResponse;
            }
        } catch (IOException e) {
            return "Error" + e.getMessage();
        }
    }

    public static String obtTipoArchivo(String extension) {
        switch (extension) {
            case "html", "css" -> {
                return "text/" + extension;
            }
            default -> {
                return "text/plain";
            }
        }
    }
}
