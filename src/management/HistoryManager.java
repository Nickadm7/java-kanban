package management;

import elements.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task); //страя история на 10 просмотров с повторами

    void remove(int id);

    List<Task> getHistory();

    void addToHistoryNew(Integer id, Task task);
}
