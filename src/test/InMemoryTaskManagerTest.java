package test;

import elements.Epic;
import elements.Subtask;
import elements.utilenum.Status;
import management.InMemoryTaskManager;
import management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagerTest {
    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewSubtaskWrongLinkEpic() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                11);
        taskManager.writeNewSubtask(subtask1);
        assertEquals(subtask1.getLinkEpic(), 11, "Связанный эпик проставлен");
        assertEquals(taskManager.getEpicById(11), null, "Эпика с данным id не должно быть");
    }

    @Test
    void addNewSubtaskNoLinkEpic() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask1 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                null);
        taskManager.writeNewSubtask(subtask1);
        assertEquals(subtask1.getLinkEpic(), null, "Связанный эпик проставлен");
        assertEquals(taskManager.getSubtaskById(2), null, "Связанный эпик проставлен");
    }

}