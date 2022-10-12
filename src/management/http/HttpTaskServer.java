package management.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    HttpTaskManager httpTaskManager;
    HttpServer httpServer;
    public static final int PORT = 8076;

    public HttpTaskServer(HttpTaskManager httpTaskManager) throws IOException {
        this.httpTaskManager = httpTaskManager;
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(0);
    }
}
