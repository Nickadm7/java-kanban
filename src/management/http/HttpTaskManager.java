package management.http;

import com.google.gson.Gson;
import management.FileBackedTasksManager;

import java.io.File;
import java.io.IOException;

public class HttpTaskManager extends FileBackedTasksManager {

    public KVTaskClient kvTaskClient;

    public HttpTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
        load();
    }

    private void load() {
        String loadTasks = kvTaskClient.load("Tasks");
    }

    @Override
    protected void save() {
        System.out.println("Переопределенный метод save");
    }
}