package test;

import management.Managers;
import management.utilinterface.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {
    TaskManager taskManager = Managers.loadFromFile(new File("src/resources/loadfortest.csv"));
    @Test
    void loadFileCheckHistory() {
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "6,7,4,3,5,1", "Ошибка в загрузке истории");
    }

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