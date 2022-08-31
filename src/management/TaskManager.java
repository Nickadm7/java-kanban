package management;

import elements.Epic;

import elements.Subtask;
import elements.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    void writeNewTask(Task newTask);

    void writeNewEpic(Epic newEpic);

    void writeNewSubtask(Subtask newSubtask);

    HashMap<Integer, Task> getListOfAllTask();

    HashMap<Integer, Epic> getListOfAllEpic();

    HashMap<Integer, Subtask> getListOfAllSubtask();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtask();

    Task getTaskById(Integer idTask);

    Epic getEpicById(Integer idEpic);

    Subtask getSubtaskById(Integer idSubtask);

    void updateTaskById(Integer idTask, Task newTask);

    void updateEpicById(Integer idEpic, Epic newEpic);

    void updateSubtaskById(Integer idSubtask, Subtask newSubtask);

    void deleteTaskById(Integer idTask);

    void deleteEpicById(Integer idEpic);

    void deleteSubtaskById(Integer idSubtask);

    void getSubtaskForEpicById(Integer idEpic);

    int generateNewId();

    ArrayList<InMemoryHistoryManager.Node> getCurrentHistory();

    String getCurrentHistoryOnlyId();

}
