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
        List<Task> prepareTasks = new ArrayList<>(tasks.values());
        System.out.println(prepareTasks);
        kvTaskClient.put("Tasks", gson.toJson(prepareTasks));




    }
}