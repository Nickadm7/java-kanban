package management;

import elements.Node;
import elements.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void remove(int id);

    ArrayList<Node> getHistory();

    void add(Integer id, Task task);
}
