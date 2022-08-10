package management;

import elements.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void remove(int id);

    ArrayList<InMemoryHistoryManager.Node> getHistory();

    void add(Integer id, Task task);
}
