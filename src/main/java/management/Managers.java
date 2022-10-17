package main.java.management;

import main.java.http.HttpTaskManager;
import main.java.management.utilinterface.HistoryManager;
import main.java.management.utilinterface.TaskManager;

import java.io.File;
import java.io.IOException;

public class Managers {
    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getFileBackedTasksManager() {
        return new FileBackedTasksManager(new File("src/main.resources/tasks.csv"));
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.loadAllFromFile(file);
        return fileBackedTasksManager;
    }
}