package main.java.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.elements.Subtask;
import main.java.http.TasksToGsonTime;
import main.java.management.utilinterface.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class SubtaskHandler implements HttpHandler {
    TaskManager manager;
    Gson gson = TasksToGsonTime.gson;

    public SubtaskHandler(TaskManager manager) {
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
                    response = gson.toJson(manager.getListOfAllSubtask().values());
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    response = gson.toJson(manager.getSubtaskById(idHttp));
                    if (manager.getSubtaskById(idHttp) != null) {
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
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    if (exchange.getRequestURI().getQuery() == null) {
                        Subtask newSubtask = new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus(), subtask.getStartTime(), subtask.getDuration(), subtask.getLinkEpic());
                        manager.writeNewSubtask(newSubtask);
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                        if (manager.getSubtaskById(idHttp) == null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        } else {
                            manager.updateSubtaskById(idHttp, subtask);
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
                    response = "Все subtask уделены";
                    manager.deleteAllSubtask();
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    if (manager.getSubtaskById(idHttp) == null) {
                        exchange.sendResponseHeaders(404, 0);
                        exchange.close();
                    }
                    response = "Subtask с id " + idHttp + " успешно удалена";
                    manager.deleteSubtaskById(idHttp);
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                }
        }
        exchange.close();
    }
}