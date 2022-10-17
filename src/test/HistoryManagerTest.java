package test;

import main.java.elements.Epic;
import main.java.elements.Subtask;
import main.java.elements.utilenum.Status;
import main.java.elements.Task;
import main.java.management.Managers;
import main.java.management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();

    HistoryManagerTest() throws IOException, InterruptedException {
    }

    @Test
    void DuplicationHistory() {
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
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "2,1", "Дубликаты в истории не допустимы");
    }

    @Test
    void DeleteFirstSubtaskHistory() {
        Subtask subtask1 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                3);
        taskManager.writeNewSubtask(subtask1);
        Subtask subtask2 = new Subtask(
                "Задача № 1",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.11.2021 14:03",
                45,
                3);
        taskManager.writeNewSubtask(subtask2);
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        taskManager.getSubtaskById(1);
        taskManager.getSubtaskById(2);
        taskManager.getEpicById(3);
        taskManager.deleteSubtaskById(1);
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "2,3", "Эпика с данным id не должно быть");
    }

    @Test
    void DeleteMiddleTaskHistory() {
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
        Task task3 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.11.2022 14:03",
                90);
        taskManager.writeNewTask(task3);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.deleteTaskById(2);
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "1,3", "Удаление истории в середине");
    }

    @Test
    void DeleteLastTaskHistory() {
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
        Task task3 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "05.11.2022 14:03",
                90);
        taskManager.writeNewTask(task3);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(3);
        taskManager.deleteTaskById(3);
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "1,2", "Удаление истории в конце");
    }
}