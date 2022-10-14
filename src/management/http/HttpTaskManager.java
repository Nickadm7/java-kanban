package management.http;

import com.google.gson.Gson;
import elements.Task;
import management.FileBackedTasksManager;

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
        //String loadTasks = kvTaskClient.load("Tasks");
        //String loadHistory = kvTaskClient.load("History");


    }

    @Override
    protected void save() {
        if (!tasks.isEmpty()){
            List<Task> prepareTasks = new ArrayList<>(tasks.values());
            System.out.println("prepareTasks" + prepareTasks);
            kvTaskClient.put("Tasks", gson.toJson(prepareTasks));
            System.out.println("Метод put кладем" + gson.toJson(prepareTasks));
        }
        if (!epics.isEmpty()) {
            List<Task> prepareEpic = new ArrayList<>(epics.values());
            System.out.println("prepareEpic" + prepareEpic);
            kvTaskClient.put("Epics", gson.toJson(prepareEpic));
            System.out.println("Метод put кладем" + gson.toJson(prepareEpic));
        }
        if (!subtasks.isEmpty()) {
            List<Task> prepareSubtask = new ArrayList<>(subtasks.values());
            System.out.println("prepareSubtask" + prepareSubtask);
            kvTaskClient.put("Subtasks", gson.toJson(prepareSubtask));
            System.out.println("Метод put кладем" + gson.toJson(prepareSubtask));
        }
    }
}