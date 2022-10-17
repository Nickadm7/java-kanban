package main.java.http;

import com.sun.net.httpserver.HttpServer;
import main.java.http.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    HttpTaskManager manager;
    HttpServer httpServer;
    public static final int PORT = 8080;

    public HttpTaskServer(HttpTaskManager manager) throws IOException {
        this.manager = manager;
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandlerPrioritized(manager));
        httpServer.createContext("/tasks/task", new TaskHandler(manager));
        httpServer.createContext("/tasks/epic", new EpicHandler(manager));
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(manager));
        httpServer.createContext("/tasks/history", new HistoryHandler(manager));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void httpServerStop() {
        httpServer.stop(0);
    }
}