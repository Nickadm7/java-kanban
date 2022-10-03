package elements;

import management.InMemoryTaskManager;
import management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewEpicWithEmptySubtasks() {
        ArrayList<Integer> epicSubtasks1 = new ArrayList<>();
        Epic epic = new Epic("Эпик без подзадач", "Эпик без подзадач", Status.NEW, epicSubtasks1);
        taskManager.writeNewEpic(epic);
        assertEquals(Status.NEW, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithEmptySubtasksWrongStatus() {
        ArrayList<Integer> epicSubtasks1 = new ArrayList<>();
        Epic epic = new Epic("Эпик без подзадач", "Эпик без подзадач", Status.IN_PROGRESS, epicSubtasks1);
        taskManager.writeNewEpic(epic);
        assertEquals(Status.NEW, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithAllSubtaskStatusNEW() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Эпик c подзадачами статус NEW", "Тест", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask("Задача № 1", "Вымыть посуду на кухне", Status.NEW, 1);
        Subtask subtask2 = new Subtask("Задача № 2", "Вымыть посуду на кухне", Status.NEW, 1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(Status.NEW, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithAllSubtaskStatusDONE() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Эпик с подзадачами статус DONE", "Тест", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask("Задача № 1", "Вымыть посуду на кухне", Status.DONE, 1);
        Subtask subtask2 = new Subtask("Задача № 2", "Вымыть посуду на кухне", Status.DONE, 1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(Status.DONE, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithSubtaskStatusNEWAndDONE() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Эпик с подзадачами статус NEW и DONE", "Тест", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask("Задача № 1", "Вымыть посуду на кухне", Status.NEW, 1);
        Subtask subtask2 = new Subtask("Задача № 2", "Вымыть посуду на кухне", Status.DONE, 1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithSubtaskAllStatusINPROGRESS() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Эпик с подзадачами статус NEW и DONE", "Тест", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask("Задача № 1", "Вымыть посуду на кухне", Status.IN_PROGRESS, 1);
        Subtask subtask2 = new Subtask("Задача № 2", "Вымыть посуду на кухне", Status.IN_PROGRESS, 1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус Эпиков не совпадают.");
    }
}