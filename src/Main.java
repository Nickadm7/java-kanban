import elements.Status;
import elements.Task;
import management.*;
import management.utilinterface.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.05.2022 14:03",
                90);
        taskManager.writeNewTask(task1);
        Task task2 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.09.2022 14:03",
                90);
        taskManager.writeNewTask(task2);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        System.out.println(taskManager.getCurrentHistoryOnlyId());
    }
}
