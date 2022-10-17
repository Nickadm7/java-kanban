package test.java;

import main.java.management.Managers;
import main.java.management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTasksManagerTest extends TaskManagerTest {
    TaskManager taskManager = Managers.loadFromFile(new File(
            "src" + File.separator
                    + "main" + File.separator
                    + "resources" + File.separator
                    + "loadfortest.csv"));

    @Test
    void checkTackStartTime() {
        assertEquals(taskManager.getTaskById(1).getStartTime(), "01.10.2020 14:03", "ошибка Task поле startTime");
    }

    @Test
    void checkTackEndTime() {
        assertEquals(taskManager.getTaskById(1).getDuration(), 30, "ошибка Task поле duration");
    }

    @Test
    void checkEpicStartTime() {
        assertEquals(taskManager.getEpicById(3).getStartTime(), "01.10.2020 14:03", "ошибка Task поле startTime");
    }

    @Test
    void checkEpicEndTime() {
        assertEquals(taskManager.getEpicById(3).getDuration(), 30, "ошибка Task поле duration");
    }

    @Test
    void checkSubtaskStartTime() {
        assertEquals(taskManager.getSubtaskById(5).getStartTime(), "01.10.2020 14:03", "ошибка Subtask поле startTime");
    }

    @Test
    void checkSubtaskEndTime() {
        assertEquals(taskManager.getSubtaskById(5).getDuration(), 30, "ошибка Subtask поле duration");
    }

    @Test
    void checkSubtaskLinkEpic() {
        assertEquals(taskManager.getSubtaskById(5).getLinkEpic(), 3, "ошибка Subtask связь с Epic");
    }
}