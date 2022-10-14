package management.http.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import elements.Task;
import management.http.TasksToGsonTime;
import management.utilinterface.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TaskHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final TaskManager manager;
    private final Gson gson = TasksToGsonTime.gson;

    public TaskHandler(TaskManager manager) {
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
                   response = gson.toJson(manager.getListOfAllTask().values());
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    response = gson.toJson(manager.getTaskById(idHttp));
                    if (manager.getTaskById(idHttp) != null) {
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
                    Task task = gson.fromJson(body, Task.class);
                    if (exchange.getRequestURI().getQuery() == null) {
                        Task newTask = new Task(task.getName(), task.getDescription(), task.getStatus(), task.getStartTime(), task.getDuration());
                        manager.writeNewTask(newTask);
                        exchange.sendResponseHeaders(201, 0);
                    } else {
                        int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                        if (manager.getTaskById(idHttp) == null) {
                            exchange.sendResponseHeaders(404, 0);
                            exchange.close();
                        } else {
                            manager.updateTaskById(idHttp, task);
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
                    response = "Все задачи уделены";
                    manager.deleteAllTasks();
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    int idHttp = Integer.parseInt(requestUriQuery.split("=")[1]);
                    if (manager.getTaskById(idHttp) == null) {
                        exchange.sendResponseHeaders(404, 0);
                        exchange.close();
                    }
                    response = "Задачи с id " + idHttp + " успешно удалена";
                    manager.deleteTaskById(idHttp);
                    exchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                    }
                }
        }
        exchange.close();
    }
}

