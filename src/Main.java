import elements.Status;
import elements.Epic;
import elements.Subtask;
import elements.Task;
import management.InMemoryTaskManager;
import management.Managers;
import management.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Уборка", "Вымыть посуду на кухне", Status.NEW);
        Task task2 = new Task("Стирка", "Убрать вещи из шкафа", Status.IN_PROGRESS);
        ArrayList<Integer> epicSubtasks1 = new ArrayList<>();
        Epic epic1 = new Epic("Построить дом", "Построить дом 140 м2 из газобетона", Status.NEW, epicSubtasks1);
        ArrayList<Integer> epicSubtasks2 = new ArrayList<>();
        Epic epic2 = new Epic("Купить новую квартиру", "Покупка трехкомнатной квартиры в новостройке", Status.NEW, epicSubtasks2);
        Subtask subtask1 = new Subtask("Участок", "Найти подходящий участок", Status.NEW, 3);
        Subtask subtask2 = new Subtask("Проект", "Разработать проект дома", Status.NEW, 3);
        Subtask subtask3 = new Subtask("Финансы", "Собрать нужную сумму денег", Status.NEW, 4);
        Subtask subtask4 = new Subtask("Проект", "Разработать проект дома", Status.DONE, 3);

        taskManager.writeNewTask(task1);
        taskManager.writeNewTask(task2);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getListOfAllTask();
        taskManager.writeNewEpic(epic1);
        taskManager.writeNewEpic(epic2);
        taskManager.getEpicById(4);
        taskManager.getEpicById(4);
        taskManager.getListOfAllEpic();
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        taskManager.writeNewSubtask(subtask3);
        taskManager.getSubtaskById(5);
        taskManager.getListOfAllSubtask();
        taskManager.getListOfAllEpic();
        taskManager.updateSubtaskById(6, subtask4);
        taskManager.getSubtaskForEpicById(3);
        taskManager.getListOfAllEpic();
        taskManager.deleteSubtaskById(6);
        taskManager.getListOfAllEpic();
        taskManager.deleteSubtaskById(5);
        taskManager.getListOfAllEpic();
        taskManager.deleteEpicById(4);
        taskManager.getListOfAllEpic();
        taskManager.deleteAllEpics();
        taskManager.getListOfAllEpic();
        taskManager.getListOfAllSubtask();
        taskManager.getHistory();

    }
}
