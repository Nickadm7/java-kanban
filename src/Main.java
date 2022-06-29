import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Уборка", "Вымыть посуду на кухне", "NEW");
        Task task2 = new Task("Стирка", "Убрать вещи из шкафа", "NEW");
        Task task3 = new Task("Готовка", "Сделать лазанью на ужин", "NEW");
        Task task4 = new Task("Прогулка", "Минимум 5000 шагов в день", "NEW");
        Task task5 = new Task("Подтягивания", "Минимум 10 раз в день", "NEW");

        ArrayList<Integer> epic1Subtasks = new ArrayList<>();
        epic1Subtasks.add(4);
        epic1Subtasks.add(5);
        Epic epic1 = new Epic("Чистата в квартире", "Порядок в квартире", "NEW", epic1Subtasks);

        ArrayList<Integer> epic2Subtasks = new ArrayList<>();
        epic2Subtasks.add(5);
        Epic epic2 = new Epic("Новая большая задача Чистата в доме", "Порядок в доме", "NEW", epic2Subtasks);

        manager.writeNewTask(task1);
        manager.writeNewTask(task2);
        manager.writeNewTask(task3);
        manager.printAllTask();
        manager.deleteAllTasks();
        manager.printAllTask();
        manager.writeNewTask(task1);
        manager.writeNewTask(task4);
        manager.printAllTask();
        manager.getTaskById(3);
        manager.getTaskById(5);
        manager.updateTaskById(5, task5);
        manager.deleteTaskById(111);
        manager.deleteTaskById(4);
        manager.printAllTask();

        manager.writeNewEpic(epic1);
        manager.printAllEpic();
        manager.deleteAllEpics();
        manager.printAllEpic();
        manager.writeNewEpic(epic1);
        manager.getEpicById(7);
        manager.updateEpicById(7, epic2);


    }
}
