package management;

import elements.*;
import management.utilexception.ManagerSaveException;
import management.utilinterface.TaskManager;

import java.io.*;
import java.time.format.DateTimeFormatter;

import static elements.TaskType.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

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
            bufferedWriter.write("id,type,name,status,description,startTime,duration,epic");
            bufferedWriter.newLine();
            for (Task value : getTasks().values()) {
                bufferedWriter.write(value.getId() + "," +
                        value.getTaskType() + "," +
                        value.getName() + "," +
                        value.getStatus() + "," +
                        value.getDescription() + "," +
                        value.getStartTime() + "," +
                        value.getDuration());
                bufferedWriter.newLine();
            }
            for (Epic value : getEpics().values()) {
                bufferedWriter.write(value.getId() + "," +
                        value.getTaskType() + "," +
                        value.getName() + "," +
                        value.getStatus() + "," +
                        value.getDescription() + "," +
                        value.getStartTime() + "," +
                        value.getDuration());
                bufferedWriter.newLine();
            }
            for (Subtask value : getSubtasks().values()) {
                bufferedWriter.write(value.getId() + "," +
                        value.getTaskType() + "," +
                        value.getName() + "," +
                        value.getStatus() + "," +
                        value.getDescription() + "," +
                        value.getStartTime() + "," +
                        value.getDuration() + "," +
                        ((Subtask) value).getLinkEpic());
                bufferedWriter.newLine();
            }
            bufferedWriter.write(" ");
            bufferedWriter.newLine();
            bufferedWriter.write(getCurrentHistoryOnlyId());
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    void loadAllFromFile(File file) {
        String inputFileName = file.toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            String lastLine = null; //последняя линия history
            while ((line = reader.readLine()) != null) {
                if (line.length() > 10) {
                    if (line.split(",")[1].equals("TASK") | line.split(",")[1].equals("EPIC") | line.split(",")[1].equals("SUBTASK")) {
                        fromString(line); //создаем задачи из строки
                    }
                    lastLine = line;
                }
            }
            historyFromString(lastLine);
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    void historyFromString(String value) {
        String strArr[] = value.split(",");
        int numArr[] = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            numArr[i] = Integer.parseInt(strArr[i]);
        }
        for (int i = 0; i < numArr.length; i++) {
            if (tasks.containsKey(numArr[i])) {
                getTaskById(numArr[i]);
            }
            if (epics.containsKey(numArr[i])) {
                getEpicById(numArr[i]);
            }
            if (subtasks.containsKey(numArr[i])) {
                getSubtaskById(numArr[i]);
            }
        }
    }

    public Task fromString(String value) {
        Integer bufferId = Integer.parseInt(value.split(",")[0]);
        String bufferName = (value.split(",")[2]);
        String bufferDescription = (value.split(",")[4]);
        Status bufferStatus = Status.NEW;
        if (value.split(",")[3].equals("NEW")) {
            bufferStatus = Status.NEW;
        } else if (value.split(",")[3].equals("IN_PROGRESS")) {
            bufferStatus = Status.IN_PROGRESS;
        } else if (value.split(",")[3].equals("DONE")) {
            bufferStatus = Status.DONE;
        }
        String bufferStartTime = (value.split(",")[5]);
        Integer bufferDuration = Integer.parseInt(value.split(",")[6]);
        if (value.split(",")[1].equals("TASK")) {
            Task bufferTask = new Task(bufferName, bufferDescription, bufferStatus, bufferStartTime, bufferDuration);
            bufferTask.setId(bufferId);
            bufferTask.setTaskType(TASK);
            tasks.put(bufferId, bufferTask);
            return bufferTask;
        }
        if (value.split(",")[1].equals("EPIC")) {
            Epic bufferEpic = new Epic(bufferName, bufferDescription, bufferStatus, bufferStartTime, bufferDuration);
            bufferEpic.setId(bufferId);
            bufferEpic.setTaskType(EPIC);
            epics.put(bufferId, bufferEpic);
            return bufferEpic;
        }
        if (value.split(",")[1].equals("SUBTASK")) {
            Integer bufferLinkEpic = Integer.parseInt(value.split(",")[7]);
            Subtask bufferSubtask = new Subtask(bufferName, bufferDescription, bufferStatus, bufferStartTime, bufferDuration, bufferLinkEpic);
            bufferSubtask.setId(bufferId);
            bufferSubtask.setTaskType(SUBTASK);
            subtasks.put(bufferId, bufferSubtask);
            return bufferSubtask;
        }
        return null;
    }

    public static void main(String[] args) {
        TaskManager taskManager = Managers.loadFromFile(new File("src/resources/backup.csv"));
        //TaskManager taskManager = Managers.getDefault();
        //taskManager.getListOfAllTask();
        taskManager.getListOfAllSubtask();
        //taskManager.getListOfAllEpic();
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        taskManager.getEpicById(3);
        taskManager.getTaskById(1);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(5);
    }
}
