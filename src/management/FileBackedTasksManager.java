package management;

import elements.Epic;
import elements.Subtask;
import elements.Task;
import elements.TaskType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void writeNewTask(Task newTask) {
        super.writeNewTask(newTask);
        save();
    }

    @Override
    public void writeNewEpic(Epic newEpic) {
        super.writeNewEpic(newEpic);
        save();
    }

    @Override
    public void writeNewSubtask(Subtask newSubtask) {
        super.writeNewSubtask(newSubtask);
        save();
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic");
            bufferedWriter.newLine();


            for (Map.Entry<Integer, Task> entry : getTasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(key + "," + TaskType.TASK + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + ",");
                bufferedWriter.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : getEpics().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(key + "," + TaskType.EPIC + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + "," + ((Epic) value).getListOfSubtasks());
                bufferedWriter.newLine();
            }

            for (Map.Entry<Integer, Subtask> entry : getSubtasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(key + "," + TaskType.SUBTASK + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + "," + ((Subtask) value).getLinkEpic());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }


}
