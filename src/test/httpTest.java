package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import elements.Epic;
import elements.Subtask;
import elements.Task;
import elements.utilenum.Status;
import management.http.HttpTaskManager;
import management.http.HttpTaskServer;
import management.http.KVServer;
import management.http.TasksToGsonTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class httpTest {

    public HttpTaskManager manager;
    public HttpTaskServer httpTaskServer;
    Gson gson = TasksToGsonTime.gson;

    @BeforeEach
    void startServerCreateTask() throws IOException, InterruptedException {
        KVServer server = new KVServer();
        server.start();
        manager = new HttpTaskManager("http://localhost:8078");
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
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        manager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.NEW,
                "07.10.2021 14:03",
                15,
                3);
        Subtask subtask2 = new Subtask(
                "Задача № 2",
                "Вымыть посуду на кухне",
                Status.NEW,
                "11.10.2021 14:03",
                15,
                3);
        manager.writeNewSubtask(subtask1);
        manager.writeNewSubtask(subtask2);
    }

    @AfterEach
    void serverStop() {
        httpTaskServer.stop();
    }

    @Test
    void historyGetFromHttpAndCurrent() {
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
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            historyFromHttp = (httpResponse.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(historyFromHttp, "\"2,1\"", "История просмотров не совпадает");
    }

    @Test
    void getAllTasksFromHttpAndCurrentPrioritized() {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            Set<Task> tasks = manager.getPrioritizedTasks();
            String currentTasks = gson.toJson(tasks);
            String tasksFromHttp = httpResponse.body();
            assertEquals(currentTasks, tasksFromHttp, "Неверный возврат задач");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTasksFromHttpByIdAndCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            Task currentTask = manager.getTaskById(1);
            JsonElement jsonElement = JsonParser.parseString(httpResponse.body());
            System.out.println("jsonElement в методе теста" + jsonElement);
            Task taskFromHttp = gson.fromJson(httpResponse.body(), Task.class);
        assertEquals(currentTask, taskFromHttp, "Неверный возврат задачи по id номеру");
    }

    @Test
    void getTasksFromHttpByIdNoExist() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=111");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void getAllEpicFromHttpAndCurrent() {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            List<Task> prepareEpic = new ArrayList<>(manager.epics.values());
            String currentEpic = gson.toJson(prepareEpic);
            String EpicsFromHttp = httpResponse.body();
            assertEquals(currentEpic, EpicsFromHttp, "Неверный возврат Epic");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllSubtaskFromHttpAndCurrent() {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            List<Task> prepareSubtask = new ArrayList<>(manager.subtasks.values());
            String currentSubtask = gson.toJson(prepareSubtask);
            String SubtaskFromHttp = httpResponse.body();
            assertEquals(currentSubtask, SubtaskFromHttp, "Неверный возврат Subtask");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}