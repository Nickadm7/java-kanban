package main.java.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.elements.Epic;
import main.java.http.TasksToGsonTime;
import main.java.management.utilinterface.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class EpicHandler implements HttpHandler {

    TaskManager manager;
    Gson gson = TasksToGsonTime.gson;

    public EpicHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        String requestUriQuery = exchange.getRequestURI().getQuery();
        String response;
        switch (method) {
            case "GET":
                if (exchange.getRequestURI().getQuery() == null) {
                    response = gson.toJson(manager.getListOfAllEpic().values());
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    response = gson.toJson(manager.getEpicById(idHttp));
                    if (manager.getEpicById(idHttp) != null) {
                        exchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        exchange.sendResponseHeaders(404, 0);
                        exchange.close();
                    }
                }
                break;
            case "POST":
                try {
                    Epic epic = gson.fromJson(body, Epic.class);
                    if (exchange.getRequestURI().getQuery() == null) {
                        Epic newEpic = new Epic(epic.getName(), epic.getDescription(), epic.getStatus(), epic.getStartTime(), epic.getDuration(), epic.getListOfSubtasks());
                        manager.writeNewEpic(newEpic);
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                        if (manager.getEpicById(idHttp) == null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        } else {
                            manager.updateEpicById(idHttp, epic);
                            exchange.sendResponseHeaders(201, 0);
                        }
                    }
                } catch (IOException e) {
                    exchange.sendResponseHeaders(400, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(e.getMessage().getBytes(StandardCharsets.UTF_8));
                    }
                }
                exchange.close();
                break;
            case "DELETE":
                if (exchange.getRequestURI().getQuery() == null) {
                    response = "Все epic уделены";
                    manager.deleteAllEpics();
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    if (manager.getEpicById(idHttp) == null) {
                        exchange.sendResponseHeaders(404, 0);
                        exchange.close();
                    }
                    response = "Epic с id " + idHttp + " успешно удалена";
                    manager.deleteEpicById(idHttp);
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                }
        }
        exchange.close();
    }
}