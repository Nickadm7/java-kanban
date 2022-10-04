package management.utilinterface;

import elements.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void remove(int id);

    ArrayList<Integer> getHistory();

    void add(Integer id, Task task);

    String getTasksHistoryId();

    static String historyToString(HistoryManager manager){
        String historyToStringOut = manager.getTasksHistoryId();
        return historyToStringOut;
    }
}