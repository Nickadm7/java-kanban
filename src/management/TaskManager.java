package management;

import elements.Epic;
import elements.Status;
import elements.Subtask;
import elements.Task;

import java.util.ArrayList;

public interface TaskManager {
    void writeNewTask(Task newTask);

    void writeNewEpic(Epic newEpic);

    void writeNewSubtask(Subtask newSubtask);

    ArrayList<Task> getListOfAllTask();

    public ArrayList<Epic> getListOfAllEpic();

    public ArrayList<Subtask> getListOfAllSubtask();

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

}
