package management.utilinterface;

import elements.Task;
import management.InMemoryHistoryManager;

import java.util.ArrayList;

public interface HistoryManager {
    void remove(int id);

    ArrayList<InMemoryHistoryManager.Node> getHistory();

    void add(Integer id, Task task);

    String getTasksHistoryId();

    static String historyToString(HistoryManager manager){
        String historyToStringOut = manager.getTasksHistoryId();
        return historyToStringOut;
    }
}