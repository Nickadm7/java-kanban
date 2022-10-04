package test;

import elements.utilenum.Status;
import elements.Task;
import management.Managers;
import management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();

    @Test
    void EmptyHistory() {
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "", "Проверка пустой истории");
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
    void DeleteFirstTaskHistory() {
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
        taskManager.deleteTaskById(1);
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "2", "Удаление истории в начале");
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