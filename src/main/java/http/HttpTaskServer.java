package main.java.http;

import com.sun.net.httpserver.HttpServer;
import main.java.http.handlers.*;
import static main.java.elements.Сonstant.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    HttpTaskManager manager;
    HttpServer httpServer;

    public HttpTaskServer(HttpTaskManager manager) throws IOException {
        this.manager = manager;
        httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(PORT_8080)), 0);
        httpServer.createContext("/tasks", new TaskHandlerPrioritized(manager));
        httpServer.createContext("/tasks/task", new TaskHandler(manager));
        httpServer.createContext("/tasks/epic", new EpicHandler(manager));
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(manager));
        httpServer.createContext("/tasks/history", new HistoryHandler(manager));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT_8080 + " порту!");
    }

    public void httpServerStop() {
        httpServer.stop(0);
    }
}