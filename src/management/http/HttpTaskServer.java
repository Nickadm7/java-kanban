package management.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    HttpTaskManager manager;
    HttpServer httpServer;
    public static final int PORT = 8080;
    Gson gson = new Gson();

    public HttpTaskServer(HttpTaskManager manager) throws IOException {
        this.manager = manager;
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", this::tasksHandler);
        httpServer.createContext("/tasks/task", new TaskHandler(manager));
        httpServer.createContext("/tasks/history", this::historyHandler);

        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }


    private void tasksHandler(HttpExchange httpExchange) throws IOException {
        String response = gson.toJson(manager.getPrioritizedTasks());
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void historyHandler (HttpExchange httpExchange) throws IOException {
        String response = gson.toJson(manager.getCurrentHistoryOnlyId());
        System.out.println("Метод historyHandler возвращает историю методом getCurrentHistoryOnlyId " + manager.getCurrentHistoryOnlyId());
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void stop() {
        httpServer.stop(1);
    }
}
