import management.*;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // TaskManager taskManager = Managers.getDefault();
        TaskManager taskManager = Managers.loadFromFile(new File("src/resources/tasks.csv"));
        /*
        Task task1 = new Task("1 Уборка", "Вымыть посуду на кухне", Status.NEW);
        Task task2 = new Task("2 Стирка", "Убрать вещи из шкафа", Status.IN_PROGRESS);
        //Task task3 = new Task("СтиркаNew", "Убрать вещи из шкафаNew", Status.DONE);
        ArrayList<Integer> epicSubtasks1 = new ArrayList<>();
        Epic epic1 = new Epic("3 Построить дом", "Построить дом 140 м2 из газобетона", Status.NEW, epicSubtasks1);
        ArrayList<Integer> epicSubtasks2 = new ArrayList<>();
        Epic epic2 = new Epic("4 Купить новую квартиру", "Покупка трехкомнатной квартиры в новостройке", Status.NEW, epicSubtasks2);
        Subtask subtask1 = new Subtask("6 Участок", "Найти подходящий участок", Status.NEW, 3);
        Subtask subtask2 = new Subtask("7 Проект", "Разработать проект дома", Status.NEW, 3);
        Subtask subtask3 = new Subtask("8 Финансы", "Собрать нужную сумму денег", Status.NEW, 3);
        //Subtask subtask4 = new Subtask("9 Проект", "Разработать проект дома", Status.DONE, 5);
        // Тестирование
        taskManager.writeNewTask(task1);
        taskManager.writeNewTask(task2);
        taskManager.writeNewEpic(epic1);
        taskManager.writeNewEpic(epic2);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        taskManager.writeNewSubtask(subtask3);
        // Запрашиваем Task и проверяем порядок
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(7);
        taskManager.getSubtaskById(5);
        taskManager.getTaskById(1);
        //taskManager.getTaskById(1);
        //taskManager.getEpicById(3);
        //taskManager.getEpicById(4);
        // Удаляем Epic и проверяем удаление его Subtask
        //taskManager.getCurrentHistory();
        //taskManager.deleteEpicById(3);
        System.out.println("Статичный метод historyToString " + HistoryManager.historyToString(InMemoryTaskManager.historyManager));
         */
        taskManager.getListOfAllTask();
        taskManager.getListOfAllEpic();
        taskManager.getListOfAllSubtask();
        taskManager.getCurrentHistory();
        taskManager.deleteTaskById(2);
        taskManager.getCurrentHistory();
    }
}
