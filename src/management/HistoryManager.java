package management;

import elements.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public interface HistoryManager {
    void remove(int id);

    ArrayList<InMemoryHistoryManager.Node> getHistory();

    void add(Integer id, Task task);

    String getTasksHistoryId();
}
