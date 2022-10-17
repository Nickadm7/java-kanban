package main.java.management;

import main.java.http.HttpTaskManager;
import main.java.http.KVServer;
import main.java.management.utilinterface.HistoryManager;
import main.java.management.utilinterface.TaskManager;

import java.io.File;
import java.io.IOException;

import static main.java.elements.Сonstant.PORT_8078;
import static main.java.elements.Сonstant.URL_ADRESS;

public class Managers {
    public static TaskManager getDefault() throws IOException, InterruptedException {
        return new HttpTaskManager(URL_ADRESS + PORT_8078);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static KVServer getNewKVServer() throws IOException {
        return new KVServer();
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        fileBackedTasksManager.loadAllFromFile(file);
        return fileBackedTasksManager;
    }
}