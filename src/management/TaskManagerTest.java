package management;

import elements.Epic;
import elements.Status;
import elements.Subtask;
import elements.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTest<T extends TaskManager> {
    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewEpicWithSubtaskAllStatusINPROGRESS() {
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
                1);
        Subtask subtask2 = new Subtask("Задача № 2",
                "Вымыть посуду на кухне",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус Эпиков не совпадают.");
    }

    @Test
    void addNewEpicWithSubtaskCheckLink() {
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
                Status.NEW,
                "01.10.2021 14:03",
                15,
                1);
        Subtask subtask2 = new Subtask(
                "Задача № 2",
                "Вымыть посуду на кухне",
                Status.NEW,
                "01.10.2021 14:03",
                15,
                1);
        taskManager.writeNewSubtask(subtask1);
        taskManager.writeNewSubtask(subtask2);
        assertEquals(subtask1.getLinkEpic(), epic.getId(), "Подзадачи и эпики неправильно связаны");
        assertEquals(subtask2.getLinkEpic(), epic.getId(), "Подзадачи и эпики неправильно связаны");
    }

    @Test
    void addNewTask() {
        Task task = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task);
        assertNotNull(task, "Задача не найдена.");
        assertEquals(task.getId(), 1, "id не совпадают.");
        assertEquals(task.getName(), "Test name", "name не совпадают.");
        assertEquals(task.getDescription(), "Test description", "description не совпадают.");
        assertEquals(Status.NEW, task.getStatus(), "status не совпадают.");
    }

    @Test
    void addNewEpic() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        assertNotNull(epic, "Эпик не найдена.");
        assertEquals(epic.getId(), 1, "id не совпадают.");
        assertEquals(epic.getName(), "Test name Epic", "name не совпадают.");
        assertEquals(epic.getDescription(), "Test description Epic", "description не совпадают.");
        assertEquals(epic.getStatus(), epic.getStatus(), "status не совпадают.");
    }

    @Test
    void addNewSubtask() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        assertNotNull(subtask, "Подзадача не найдена.");
        assertEquals(subtask.getId(), 2, "id не совпадают.");
        assertEquals(subtask.getName(), "Test name", "name не совпадают.");
        assertEquals(subtask.getDescription(), "Test description", "description не совпадают.");
        assertEquals(Status.NEW, subtask.getStatus(), "status не совпадают.");
    }

    @Test
    void getListOfAllTask() {
        assertEquals(taskManager.getListOfAllTask(), null, "Список таксов пуст, но выводиться.");
        HashMap<Integer, Task> tasksNew = new HashMap<>();
        Task task1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task1);
        tasksNew.put(1, task1);
        assertEquals(taskManager.getListOfAllTask(), tasksNew, "Список из одного Таска не выводиться.");
        Task task2 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task2);
        tasksNew.put(2, task2);
        assertEquals(taskManager.getListOfAllTask(), tasksNew, "Список из двух Тасков не выводиться.");
    }

    @Test
    void getListOfAllSubtask() {
        assertEquals(taskManager.getListOfAllSubtask(), null, "Список Сабтасков пуст, но выводиться.");
        HashMap<Integer, Subtask> subtaskNew = new HashMap<>();
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
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask1);
        subtaskNew.put(2, subtask1);
        assertEquals(taskManager.getListOfAllSubtask(), subtaskNew, "Список из одного Сабтаска не выводиться.");
        Subtask subtask2 = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask2);
        subtaskNew.put(3, subtask2);
        assertEquals(taskManager.getListOfAllSubtask(), subtaskNew, "Список из одного Сабтаска не выводиться.");
    }

    @Test
    void getListOfAllEpic() {
        assertEquals(taskManager.getListOfAllEpic(), null, "Список Эпиков пуст, но выводиться.");
        HashMap<Integer, Epic> epicNew = new HashMap<>();
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic1 = new Epic("Test name Epic", "Test description Epic", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic1);
        epicNew.put(1, epic1);
        assertEquals(taskManager.getListOfAllEpic(), epicNew, "Список из одного Эпика не выводиться.");
        Epic epic2 = new Epic("Test name Epic", "Test description Epic", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic2);
        epicNew.put(2, epic2);
        assertEquals(taskManager.getListOfAllEpic(), epicNew, "Список из двух Эпиков не выводиться.");
    }

    @Test
    void getTaskById() {
        assertEquals(taskManager.getTaskById(1), null, "Не существует Таска по id.");
        Task task1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task1);
        assertEquals(taskManager.getTaskById(1), task1, "список не совпадают.");
    }

    @Test
    void getSubtaskById() {
        assertEquals(taskManager.getSubtaskById(1), null, "список не совпадают.");
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        assertEquals(taskManager.getSubtaskById(2), subtask, "список не совпадают.");
    }

    @Test
    void getEpicById() {
        assertEquals(taskManager.getEpicById(1), null, "список не совпадают.");
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Test name", "Test description", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        assertEquals(taskManager.getEpicById(1), epic, "список не совпадают.");
    }

    @Test
    void updateTaskById() {
        Task task = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task);
        Task newTask = new Task(
                "NEW Test name",
                "NEW Test description",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45);
        taskManager.updateTaskById(1, newTask);
        assertEquals(taskManager.getTaskById(1).getName(), "NEW Test name", "name не совпадают.");
        assertEquals(taskManager.getTaskById(1).getDescription(), "NEW Test description", "description не совпадают.");
        assertEquals(Status.IN_PROGRESS, taskManager.getTaskById(1).getStatus(), "status не совпадают.");
    }

    @Test
    void updateSubtaskById() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        Subtask newSubtask = new Subtask(
                "NEW Test name",
                "NEW Test description",
                Status.IN_PROGRESS,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.updateSubtaskById(2, newSubtask);
        assertEquals(taskManager.getSubtaskById(2).getName(), "NEW Test name", "name не совпадают.");
        assertEquals(taskManager.getSubtaskById(2).getDescription(), "NEW Test description", "description не совпадают.");
        assertEquals(Status.IN_PROGRESS, taskManager.getSubtaskById(2).getStatus(), "status не совпадают.");
    }

    @Test
    void updateEpicById() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Test name", "Test description", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        Epic newEpic = new Epic("NEW Test name", "NEW Test description", Status.NEW, epicSubtasks);
        taskManager.updateEpicById(1, newEpic);
        assertEquals(taskManager.getEpicById(1).getName(), "NEW Test name", "name не совпадают.");
        assertEquals(taskManager.getEpicById(1).getDescription(), "NEW Test description", "description не совпадают.");
    }

    @Test
    void getSubtaskForEpicById() {
        assertEquals(taskManager.getSubtaskForEpicById(1), null, "Неправильный id для Эпика.");
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic1 = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic1);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        epicSubtasks.add(2);
        assertEquals(taskManager.getSubtaskForEpicById(1), epicSubtasks, "Список из одного Эпика не выводиться.");
    }

    @Test
    void deleteTaskById() {
        Task task = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45);
        taskManager.writeNewTask(task);
        taskManager.deleteTaskById(1);
        assertEquals(taskManager.getTaskById(1), null, "Получаем не существующий Таск.");
    }

    @Test
    void deleteSubtaskById() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        taskManager.deleteSubtaskById(2);
        assertEquals(taskManager.getSubtaskById(2), null, "Получаем не существующий Таск.");
    }

    @Test
    void deleteEpicById() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic("Test name", "Test description", Status.NEW, epicSubtasks);
        taskManager.writeNewEpic(epic);
        taskManager.deleteEpicById(1);
        assertEquals(taskManager.getEpicById(1), null, "Получаем не существующий Эпик.");
    }

    @Test
    void deleteAllTasks() {
        Task task = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.11.2022 15:03",
                45);
        Task task1 = new Task(
                "Test name",
                "Test description",
                Status.NEW,
                "01.12.2022 15:03",
                45);
        taskManager.writeNewTask(task);
        taskManager.writeNewTask(task1);
        taskManager.deleteAllTasks();
        assertEquals(taskManager.getTaskById(1), null, "Получаем не существующий Таск.");
        assertEquals(taskManager.getTaskById(2), null, "Получаем не существующий Таск.");
    }

    @Test
    void deleteAllSubtask() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name Epic",
                "Test description Epic",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        Subtask subtask = new Subtask(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                1);
        taskManager.writeNewSubtask(subtask);
        taskManager.deleteAllSubtask();
        assertEquals(taskManager.getSubtaskById(2), null, "Получаем не существующий Сабтаск.");
    }

    @Test
    void deleteAllEpic() {
        ArrayList<Integer> epicSubtasks = new ArrayList<>();
        Epic epic = new Epic(
                "Test name",
                "Test description",
                Status.NEW,
                "01.10.2021 14:03",
                45,
                epicSubtasks);
        taskManager.writeNewEpic(epic);
        taskManager.deleteAllEpics();
        assertEquals(taskManager.getEpicById(1), null, "Получаем не существующий Эпик.");
    }

}