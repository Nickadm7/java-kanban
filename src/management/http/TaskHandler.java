package management.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class TaskHandler implements HttpHandler {
    HttpTaskManager httpTaskManager;

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            httpTaskManager = new HttpTaskManager("http://localhost:8078");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Началась обработка запроса от клиента.");
        String response = "Hey! Glad to see you on our server.";
        String[] splitURL = httpExchange.getRequestURI().getPath().split("/");
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            System.out.println(response);
        }

        switch (splitURL[2]) {
            case "task":
                response = "task";
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                System.out.println(response);
        }
    }
}