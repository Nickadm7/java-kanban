package management;

import elements.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new LinkedList<>(); //хранит историю просмотров
    private final int NUMBER_OF_ENTRIES = 10; //количество записей хранимых в истории

    @Override
    public List<Task> getHistory() {
        System.out.println("История просмотров пользователя:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + " " + history.get(i));
        }
        return history;
    }

    @Override
    public void add(Task task) {
        if (history.isEmpty()) {
            history.add(task);
        } else {
            if (history.size() < NUMBER_OF_ENTRIES) {
                history.add(task);
            } else {
                history.remove(0);
                history.add(task);
            }
        }
    }
}
