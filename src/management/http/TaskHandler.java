package management.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import elements.Task;
import management.utilinterface.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHandler implements HttpHandler {
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final TaskManager manager;

    private final Gson gson = TasksToGsonTime.gson;


    public TaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //извлекаем метод
        String method = exchange.getRequestMethod();
        System.out.println("Началась обработка " + method + " /tasks/task запроса от клиента.");
        //извлекаем тело из запроса
        //InputStream inputStream = exchange.getRequestBody();


        System.out.println("Началась обработка " + method + " /tasks/task запроса от клиента.");
        String response = "/tasks/task";


        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }


    }
}
