package management.http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import elements.Task;
import management.http.TasksToGsonTime;
import management.utilinterface.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TaskHandlerPrioritized implements HttpHandler {
    TaskManager manager;
    Gson gson = TasksToGsonTime.gson;

    public TaskHandlerPrioritized(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = gson.toJson(manager.getPrioritizedTasks());
        exchange.sendResponseHeaders(200, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}