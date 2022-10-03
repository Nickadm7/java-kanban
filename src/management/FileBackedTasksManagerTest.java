package management;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {

    @Test
    void loadFileCheckHistory() {
        TaskManager taskManager = Managers.loadFromFile(new File("src/resources/history673145.csv"));
        assertEquals(taskManager.getCurrentHistoryOnlyId(), "6,7,3,1,4,5", "Ошибка в загрузке истории");
    }
}