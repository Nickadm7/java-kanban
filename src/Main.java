import elements.Epic;
import elements.Status;
import elements.Subtask;
import elements.Task;
import management.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Эпик с подзадачами статус NEW и DONE",
                "Тест",
                Status.NEW,
                "01.10.2022 14:03",
                0,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                1);
        Subtask subtask2 = new Subtask(
                "Задача № 2",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2020 14:03",
                30,
                1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        taskManager.getListOfAllSubtask();
        Task task1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.05.2022 14:03",
                90);
        Task task2 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.05.2022 14:05",
                90);
        taskManager.writeNewTask(task1);
        taskManager.writeNewTask(task2);
        taskManager.getListOfAllTask();
        taskManager.getListOfAllEpic();
        taskManager.getPrioritizedTasks();
    }
}
