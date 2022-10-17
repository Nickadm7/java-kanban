package main.java.http;

import com.google.gson.*;
import main.java.elements.Epic;
import main.java.elements.Subtask;
import main.java.elements.Task;
import main.java.management.FileBackedTasksManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    public KVTaskClient kvTaskClient;
    Gson gson = TasksToGsonTime.gson;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
        load();
    }

    private void load() {
        String loadTasks = kvTaskClient.load("Tasks");
        String loadEpics = kvTaskClient.load("Epics");
        String loadSubtasks = kvTaskClient.load("Subtasks");
        String loadHistory = kvTaskClient.load("History");

        if (!loadTasks.isEmpty()) {
            JsonElement jsonTasks = JsonParser.parseString(loadTasks);
            JsonArray TasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonElement : TasksArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Task task = gson.fromJson(jsonObject, Task.class);
                writeNewTask(task);
            }
        }
        if (!loadEpics.isEmpty()) {
            JsonElement jsonTasks = JsonParser.parseString(loadEpics);
            JsonArray EpicsArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonElement : EpicsArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Epic epic = gson.fromJson(jsonObject, Epic.class);
                writeNewEpic(epic);
            }
        }
        if (!loadSubtasks.isEmpty()) {
            JsonElement jsonSubtasks = JsonParser.parseString(loadSubtasks);
            JsonArray SubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonElement : SubtasksArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Subtask subtask = gson.fromJson(jsonObject, Subtask.class);
                writeNewSubtask(subtask);
            }
        }
        if (!loadHistory.isEmpty()) {
            JsonElement jsonHistory = JsonParser.parseString(loadHistory);
            JsonArray jsonArray = jsonHistory.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                Integer historyLinkId = gson.fromJson(jsonElement, Integer.class);
                if (tasks.containsKey(historyLinkId)) {
                    getTaskById(historyLinkId);
                }
                if (epics.containsKey(historyLinkId)) {
                    getEpicById(historyLinkId);
                }
                if (subtasks.containsKey(historyLinkId)) {
                    getSubtaskById(historyLinkId);
                }
            }
        }
    }

    @Override
    protected void save() {
        if (!tasks.isEmpty()) {
            List<Task> prepareTasks = new ArrayList<>(tasks.values());
            kvTaskClient.put("Tasks", gson.toJson(prepareTasks));
        }
        if (!epics.isEmpty()) {
            List<Task> prepareEpic = new ArrayList<>(epics.values());
            kvTaskClient.put("Epics", gson.toJson(prepareEpic));
        }
        if (!subtasks.isEmpty()) {
            List<Task> prepareSubtask = new ArrayList<>(subtasks.values());
            kvTaskClient.put("Subtasks", gson.toJson(prepareSubtask));
        }
    }
}