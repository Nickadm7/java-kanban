package test;

import elements.Task;
import elements.utilenum.Status;
import management.http.HttpTaskManager;
import management.http.HttpTaskServer;
import management.http.KVServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class httpTest {

    public HttpTaskManager manager;
    public HttpTaskServer httpTaskServer;

    @BeforeEach
    void startServerCreateTask() throws IOException, InterruptedException {
        KVServer server = new KVServer();
        server.start();
        manager = new HttpTaskManager("http://localhost:8078");
        HttpExchange httpExchange;
        httpTaskServer = new HttpTaskServer(manager);
        Task testTask = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        manager.writeNewTask(testTask);
        Task testTask1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.11.2021 14:03",
                45);
        manager.writeNewTask(testTask1);
        manager.getTaskById(2);
        manager.getTaskById(1);
    }

    @Test
    void testHistory() {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse;
        String historyFromHttp = null;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Неверный статус");
            historyFromHttp = (httpResponse.body());
            System.out.println(historyFromHttp);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(historyFromHttp, "\"2,1\"", "История просмотров не совпадает");
    }

    @Test
    void getAllTasks() {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri1 = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(uri1)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}