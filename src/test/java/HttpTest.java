package test.java;

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

import static main.java.elements.Сonstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTest {

    private HttpTaskServer httpTaskServer;
    private HttpTaskManager manager;
    private final Gson gson = TasksToGsonTime.gson;
    private KVServer server;


    @BeforeEach
    void startServerCreateTask() throws IOException, InterruptedException {
        server = new KVServer();
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
        manager.getEpicById(3);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
    }

    @AfterEach
    void serverStop() {
        httpTaskServer.httpServerStop();
        server.stop();
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
        assertEquals(historyFromHttp, "\"2,1,3,4,5\"", "История просмотров не совпадает");
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
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
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
    void getTasksFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
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
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void getSubtaskFromHttpByIdNoExist() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/?id=111");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void getEpicFromHttpByIdNoExist() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/?id=111");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
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
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
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
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
            List<Task> prepareSubtask = new ArrayList<>(manager.subtasks.values());
            String currentSubtask = gson.toJson(prepareSubtask);
            String SubtaskFromHttp = httpResponse.body();
            assertEquals(currentSubtask, SubtaskFromHttp, "Неверный возврат Subtask");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSubtaskFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/?id=4");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
        Subtask currentSubtask = manager.getSubtaskById(4);
        JsonElement jsonElement = JsonParser.parseString(httpResponse.body());
        Task taskFromHttp = gson.fromJson(httpResponse.body(), Subtask.class);
        assertEquals(currentSubtask, taskFromHttp, "Неверный возврат задачи по id номеру");
    }

    @Test
    void getEpicFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, httpResponse.statusCode(), "Ответ от сервера не 200");
        Epic currentEpic = manager.getEpicById(3);
        JsonElement jsonElement = JsonParser.parseString(httpResponse.body());
        Task taskFromHttp = gson.fromJson(httpResponse.body(), Epic.class);
        assertEquals(currentEpic, taskFromHttp, "Неверный возврат задачи по id номеру");
    }

    @Test
    void deleteTasksFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse1 = httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse1.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void deleteSubtaskFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/subtask/?id=4");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse1 = httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse1.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void deleteEpicFromHttpByIdCurrent() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/epic/?id=3");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> httpResponse1 = httpClient.send(httpRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, httpResponse1.statusCode(), "Ответ от сервера не 400");
    }

    @Test
    void updateTaskByIdFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Task testTaskTest = new Task(
                "Update Test name",
                "Test description",
                Status.NEW,
                "17.10.2021 14:03",
                45);
        String json = gson.toJson(testTaskTest);
        URI uri = URI.create("http://localhost:8080/tasks/task?id=1");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Task taskFromHttp = gson.fromJson(jsonElement, Task.class);
            assertEquals("Update Test name", taskFromHttp.getName(), "Неверный возврат задачи");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateEpicByIdFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epicTest = new Epic(
                "Update Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        String json = gson.toJson(epicTest);
        URI uri = URI.create("http://localhost:8080/tasks/epic?id=3");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Epic epicFromHttp = gson.fromJson(jsonElement, Epic.class);
            assertEquals("Update Test name Epic", epicFromHttp.getName(), "Неверный возврат epic");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateSubtaskByIdFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Subtask subtaskTest = new Subtask(
                "Update Задача № 1",
                "Вымыть посуду на кухне",
                Status.NEW,
                "07.10.2021 14:03",
                15,
                3);
        String json = gson.toJson(subtaskTest);
        URI uri = URI.create("http://localhost:8080/tasks/subtask?id=4");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Epic subtaskFromHttp = gson.fromJson(jsonElement, Epic.class);
            assertEquals("Update Задача № 1", subtaskFromHttp.getName(), "Неверный возврат subtask");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void postNewTaskFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Task testTask = new Task(
                "Update Test name",
                "Test description",
                Status.NEW,
                "17.10.2021 14:03",
                45);
        String json = gson.toJson(testTask);
        URI uri = URI.create("http://localhost:8080/tasks/task");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        URI uri1 = URI.create("http://localhost:8080/tasks/task?id=6");
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri1)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Task taskFromHttp = gson.fromJson(jsonElement, Task.class);
            assertEquals(6, taskFromHttp.getId(), "Неверное добавление задачи");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void postNewEpicFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epicTest = new Epic(
                "Update Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        String json = gson.toJson(epicTest);
        URI uri = URI.create("http://localhost:8080/tasks/epic");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        URI uri1 = URI.create("http://localhost:8080/tasks/epic?id=6");
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri1)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Epic epicFromHttp = gson.fromJson(jsonElement, Epic.class);
            assertEquals(6, epicFromHttp.getId(), "Неверное добавление epic");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void postNewSubtaskFromHttp() {
        HttpClient httpClient = HttpClient.newHttpClient();
        Subtask subtaskTest = new Subtask(
                "Update Задача № 1",
                "Вымыть посуду на кухне",
                Status.NEW,
                "07.10.2021 14:03",
                15,
                3);
        String json = gson.toJson(subtaskTest);
        URI uri = URI.create("http://localhost:8080/tasks/subtask");
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, httpResponse.statusCode(), "Неверный статус");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        URI uri1 = URI.create("http://localhost:8080/tasks/subtask?id=6");
        HttpRequest GetRequest = HttpRequest.newBuilder()
                .uri(uri1)
                .GET()
                .build();
        try {
            HttpResponse<String> GetResponse = httpClient.send(GetRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, GetResponse.statusCode(), "Неверный статус");
            JsonElement jsonElement = JsonParser.parseString(GetResponse.body());
            Subtask subtaskFromHttp = gson.fromJson(jsonElement, Subtask.class);
            assertEquals(6, subtaskFromHttp.getId(), "Неверное добавление subtask");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}