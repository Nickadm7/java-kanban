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

    @Override
    public Task getTaskById(Integer idTask) {
        System.out.println("Ищем задачу под номером " + idTask);
        if (tasks.get(idTask) != null) {
            System.out.print("id = " + idTask + " ");
            System.out.println(tasks.get(idTask));
            historyManager.add(idTask, tasks.get(idTask));
            save();
            return tasks.get(idTask);
        } else {
            System.out.println("Такой задачи нет!");
            return null;
        }
    }

    @Override
    public Epic getEpicById(Integer idEpic) {
        System.out.println("Ищем эпик под номером " + idEpic);
        if (epics.get(idEpic) != null) {
            System.out.print("id = " + idEpic + " ");
            System.out.println(epics.get(idEpic));
            historyManager.add(idEpic, epics.get(idEpic));
            save();
            return epics.get(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
            return null;
        }
    }

    @Override
    public Subtask getSubtaskById(Integer idSubtask) {
        System.out.println("Ищем подзадачу под номером " + idSubtask);
        if (subtasks.get(idSubtask) != null) {
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtasks.get(idSubtask));
            historyManager.add(idSubtask, subtasks.get(idSubtask));
            save();
            return subtasks.get(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
            return null;
        }
    }

    @Override
    public void deleteTaskById(Integer idTask) {
        super.deleteTaskById(idTask);
        save();
    }

    @Override
    public void deleteEpicById(Integer idEpic) {
        super.deleteEpicById(idEpic);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer idSubtask) {
        super.deleteSubtaskById(idSubtask);
        save();
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write("id,type,name,status,description,epic");
            bufferedWriter.newLine();


            for (Map.Entry<Integer, Task> entry : getTasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + ",");
                bufferedWriter.newLine();
            }

            for (Map.Entry<Integer, Epic> entry : getEpics().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + ",");
                bufferedWriter.newLine();
            }

            for (Map.Entry<Integer, Subtask> entry : getSubtasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," + value.getStatus() + "," + value.getDescription() + "," + ((Subtask) value).getLinkEpic());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write(getCurrentHistoryOnlyId());
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }


}
