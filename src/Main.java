import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Уборка", "Вымыть посуду на кухне", "NEW");
        Task task2 = new Task("Стирка", "Убрать вещи из шкафа", "IN_PROGRESS");
        ArrayList<Integer> epic1Subtasks = new ArrayList<>();
        epic1Subtasks.add(5);
        epic1Subtasks.add(6);
        Epic epic1 = new Epic("Чистата в квартире", "Порядок в квартире", "IN_PROGRESS", epic1Subtasks);
        ArrayList<Integer> epic2Subtasks = new ArrayList<>();
        epic2Subtasks.add(6);
        Epic epic2 = new Epic("Новая большая задача Чистата в доме", "Порядок в доме", "NEW", epic2Subtasks);
        Subtask subtask1 = new Subtask("Мини уборка", "Мини вымыть посуду на кухне", "NEW", 3);
        Subtask subtask2 = new Subtask("Мини уборка", "Мини вымыть посуду на кухне", "NEW", 3);
        Subtask subtask3 = new Subtask("Мини уборка 1", "Мини вымыть посуду на кухне 1", "NEW", 4);
        Subtask subtask4 = new Subtask("Мини уборка", "Мини вымыть посуду на кухне", "DONE", 3);

        manager.writeNewTask(task1);
        manager.writeNewTask(task2);
        manager.writeNewEpic(epic1);
        manager.writeNewEpic(epic2);
        manager.writeNewSubtask(subtask1);
        manager.writeNewSubtask(subtask2);
        manager.writeNewSubtask(subtask3);
        manager.printAllTask();
        manager.printAllEpic();
        manager.printAllSubtask();
        manager.updateSubtaskById(6, subtask4);
        manager.getSubtaskForEpicById(3);
        manager.printAllEpic();



    }
}
