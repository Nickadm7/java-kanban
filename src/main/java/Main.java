package main.java;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import main.java.elements.Epic;
import main.java.elements.Subtask;
import main.java.elements.Task;
import main.java.elements.utilenum.Status;
import main.java.http.HttpTaskManager;
import main.java.http.HttpTaskServer;
import main.java.http.KVServer;
import main.java.http.TasksToGsonTime;

import static main.java.elements.Сonstant.*;
import static main.java.management.Managers.getNewKVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main {
    public static HttpTaskManager manager;
    public static HttpTaskServer httpTaskServer;

    private static final Gson gson = TasksToGsonTime.gson;

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer server = getNewKVServer();
        server.start();
        manager = new HttpTaskManager(URL_ADRESS + PORT_8078);
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

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create(URL_ADRESS + port8080 + "/tasks/task/?id=1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            Task currentTask = manager.getTaskById(1);
            JsonElement jsonElement = JsonParser.parseString(httpResponse.body());
            Task taskFromHttp = gson.fromJson(httpResponse.body(), Task.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}