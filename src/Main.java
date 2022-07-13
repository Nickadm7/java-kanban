import elements.Status;
import elements.Epic;
import elements.Subtask;
import elements.Task;
import management.Manager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
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

        manager.writeNewTask(task1);
        manager.writeNewTask(task2);
        manager.getListOfAllTask();
        manager.writeNewEpic(epic1);
        manager.writeNewEpic(epic2);
        manager.getListOfAllEpic();
        manager.writeNewSubtask(subtask1);
        manager.writeNewSubtask(subtask2);
        manager.writeNewSubtask(subtask3);
        manager.getListOfAllSubtask();
        manager.getListOfAllEpic();
        manager.updateSubtaskById(6, subtask4);
        manager.getSubtaskForEpicById(3);
        manager.getListOfAllEpic();
        manager.deleteSubtaskById(6);
        manager.getListOfAllEpic();
        manager.deleteSubtaskById(5);
        manager.getListOfAllEpic();
        manager.deleteEpicById(4);
        manager.getListOfAllEpic();
        manager.deleteAllEpics();
        manager.getListOfAllEpic();
        manager.getListOfAllSubtask();

    }
}
