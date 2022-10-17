package main.java.management;

import main.java.elements.*;
import main.java.elements.utilenum.Status;
import main.java.management.utilinterface.HistoryManager;
import main.java.management.utilinterface.TaskManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static main.java.elements.utilenum.TaskType.*;

public class InMemoryTaskManager implements TaskManager {
    private Integer id;
    public HashMap<Integer, Task> tasks; //храним все задачи
    public HashMap<Integer, Epic> epics; //храним все epic
    public HashMap<Integer, Subtask> subtasks; //храним все подзадачи
    private int counterSubtasks; // счетчик подзадач
    private int counterSubtasksStatusNew; // счетчик подзадач со статусом NEW
    private int counterSubtasksStatusDone; // счетчик подзадач со статусом DONE
    public static HistoryManager historyManager = Managers.getDefaultHistory();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public InMemoryTaskManager() {
        id = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void writeNewTask(Task newTask) {
        Integer bufferId = generateNewId();
        newTask.setId(bufferId);
        newTask.setTaskType(TASK);
        if (validateTime(newTask.getStartTime(), newTask.getDuration())) {
            tasks.put(bufferId, newTask);
            historyManager.add(bufferId, tasks.get(bufferId));
        } else {
            System.out.println("Данное время уже занято другой задачей!");
        }
    }

    @Override
    public void writeNewEpic(Epic newEpic) {
        Integer bufferId = generateNewId();
        newEpic.setId(bufferId);
        newEpic.setTaskType(EPIC);
        epics.put(bufferId, newEpic);
        historyManager.add(bufferId, epics.get(bufferId));
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void writeNewSubtask(Subtask newSubtask) {
        if (newSubtask.getLinkEpic() != null) {
            Integer bufferId = generateNewId();
            newSubtask.setId(bufferId);
            newSubtask.setTaskType(SUBTASK);
            if (validateTime(newSubtask.getStartTime(), newSubtask.getDuration())) {
                subtasks.put(bufferId, newSubtask);
                if (epics.get(subtasks.get(bufferId).getLinkEpic()) != null) {
                    epics.get(subtasks.get(bufferId).getLinkEpic()).getListOfSubtasks().add(bufferId); // добавляем к эпику ссылку на новую подзадачу
                    historyManager.add(bufferId, subtasks.get(bufferId));
                    determineAndSetCorrectEpicStatus();
                }
            } else {
                System.out.println("Данное время уже занято другой задачей!");
            }
        } else {
            System.out.println("У Subtack обязательно должен быть связанный Epic!");
        }
    }

    @Override
    public HashMap<Integer, Task> getListOfAllTask() {
        if (tasks.isEmpty()) {
            return null;
        } else {
            System.out.println(tasks);
            return tasks;
        }
    }

    @Override
    public HashMap<Integer, Epic> getListOfAllEpic() {
        if (epics.isEmpty()) {
            return null;
        } else {
            System.out.println(epics);
            return epics;
        }
    }

    @Override
    public HashMap<Integer, Subtask> getListOfAllSubtask() {
        if (subtasks.isEmpty()) {
            return null;
        } else {
            System.out.println(subtasks);
            return subtasks;
        }
    }

    @Override
    public void deleteAllTasks() {
        for (Integer key : tasks.keySet()) {
            historyManager.remove(key);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        deleteAllSubtask();
        for (Integer key : epics.keySet()) {
            historyManager.remove(key);
        }
        epics.clear();
    }

    @Override
    public void deleteAllSubtask() {
        for (Integer key : subtasks.keySet()) {
            historyManager.remove(key);
        }
        subtasks.clear();
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public Task getTaskById(Integer idTask) {
        if (tasks.get(idTask) != null) {
            historyManager.add(idTask, tasks.get(idTask));
            return tasks.get(idTask);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpicById(Integer idEpic) {
        if (epics.get(idEpic) != null) {
            historyManager.add(idEpic, epics.get(idEpic));
            return epics.get(idEpic);
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtaskById(Integer idSubtask) {
        if (subtasks.get(idSubtask) != null) {
            historyManager.add(idSubtask, subtasks.get(idSubtask));
            return subtasks.get(idSubtask);
        } else {
            return null;
        }
    }


    @Override
    public void updateTaskById(Integer idTask, Task newTask) {
        if (tasks.get(idTask) != null) {
            if (validateTime(newTask.getStartTime(), newTask.getDuration())) {
                tasks.put(idTask, newTask);
                newTask.setId(idTask);
                determineAndSetCorrectEpicStatus();
            } else {
                System.out.println("Данное время уже занято другой задачей!");
            }
        }
    }

    @Override
    public void updateEpicById(Integer idEpic, Epic newEpic) {
        if (epics.get(idEpic) != null) {
            epics.put(idEpic, newEpic);
            newEpic.setId(idEpic);
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void updateSubtaskById(Integer idSubtask, Subtask newSubtask) {
        if (subtasks.get(idSubtask) != null) {
            if (validateTime(newSubtask.getStartTime(), newSubtask.getDuration())) {
                subtasks.put(idSubtask, newSubtask);
                newSubtask.setId(idSubtask);
                determineAndSetCorrectEpicStatus();
            } else {
                System.out.println("Данное время уже занято другой задачей!");
            }
        }
    }

    @Override
    public void deleteTaskById(Integer idTask) {
        if (tasks.get(idTask) != null) {
            historyManager.remove(idTask);
            tasks.remove(idTask);
        }
    }

    @Override
    public void deleteEpicById(Integer idEpic) {
        //перед удалением эпика, сначала удаляем подзадачи, которые были с ним связаны и историю
        for (Integer deleteSubtasksId : epics.get(idEpic).getListOfSubtasks()) {
            historyManager.remove(deleteSubtasksId);
            subtasks.remove(deleteSubtasksId);
        }
        if (epics.get(idEpic) != null) {
            historyManager.remove(idEpic);
            epics.remove(idEpic);
        }
    }

    @Override
    public void deleteSubtaskById(Integer idSubtask) {
        if (subtasks.get(idSubtask) != null) {
            epics.get(subtasks.get(idSubtask).getLinkEpic()).getListOfSubtasks().remove(idSubtask); // удаляем из эпика id нашей подзадачи
            historyManager.remove(idSubtask);
            subtasks.remove(idSubtask);
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public List<Integer> getSubtaskForEpicById(Integer idEpic) {
        if (epics.get(idEpic) != null) {
            return epics.get(idEpic).getListOfSubtasks();
        } else {
            return null;
        }
    }

    public Set<Task> getPrioritizedTasks() {
        Set<Task> tasksByPriority = new TreeSet<>
                (Comparator.comparing(Task::getStartTime,
                        Comparator.nullsLast(Comparator.naturalOrder())));
        tasksByPriority.addAll(tasks.values());
        tasksByPriority.addAll(subtasks.values());
        return tasksByPriority;
    }

    public boolean validateTime(String startTime, Integer duration) {
        LocalDateTime currentDataTime = LocalDateTime.parse(startTime, formatter);
        for (Task task : getPrioritizedTasks()) {
            LocalDateTime bufferStartTime = LocalDateTime.parse(task.getStartTime(), formatter);
            LocalDateTime bufferEndTime = LocalDateTime.parse(task.getEndTime(), formatter);
            if (currentDataTime.isAfter(bufferStartTime) && currentDataTime.isBefore(bufferEndTime)) {
                return false;
            }
        }
        return true;
    }

    public void determineAndSetCorrectEpicStatus() {
        for (Integer key : epics.keySet()) {
            counterSubtasks = 0; // счетчик подзадач
            counterSubtasksStatusNew = 0; // счетчик подзадач со статусом NEW
            counterSubtasksStatusDone = 0; // счетчик подзадач со статусом DONE

            epics.get(key).setStatus(Status.IN_PROGRESS); // по-умолчанию статус IN_PROGRESS
            if (epics.get(key).getListOfSubtasks().isEmpty()) {
                epics.get(key).setStatus(Status.NEW);
            }
            for (Integer currentSubtask : epics.get(key).getListOfSubtasks()) {
                counterSubtasks++;
                if (subtasks.containsKey(currentSubtask)) {
                    if (subtasks.get(currentSubtask).getStatus() == Status.NEW) {
                        counterSubtasksStatusNew++;
                    }
                    if (subtasks.get(currentSubtask).getStatus() == Status.DONE) {
                        counterSubtasksStatusDone++;
                    }
                }
            }
            if (counterSubtasks != 0 && counterSubtasks == counterSubtasksStatusNew) {
                epics.get(key).setStatus(Status.NEW);
            }
            if (counterSubtasks != 0 && counterSubtasks == counterSubtasksStatusDone) {
                epics.get(key).setStatus(Status.DONE);
            }
        }

        for (Integer key : epics.keySet()) {
            int currentEpic = 0;
            LocalDateTime currentStartTime = LocalDateTime.of(9999, 1, 1, 0, 0);
            LocalDateTime currentEndTime = LocalDateTime.of(1, 1, 1, 0, 0);
            for (Integer currentSubtask : epics.get(key).getListOfSubtasks()) {
                if (subtasks.get(currentSubtask) != null) {
                    currentEpic = currentEpic + subtasks.get(currentSubtask).getDuration();
                }
                if (subtasks.get(currentSubtask) != null) {
                    LocalDateTime bufferStartTime = LocalDateTime.parse(subtasks.get(currentSubtask).getStartTime(), formatter);
                    LocalDateTime bufferEndTime = LocalDateTime.parse(subtasks.get(currentSubtask).getEndTime(), formatter);

                    if (bufferStartTime.isBefore(currentStartTime)) {
                        currentStartTime = bufferStartTime;
                    }
                    if (currentEndTime.isBefore(bufferEndTime)) {
                        currentEndTime = bufferEndTime;
                    }
                }
            }
            String formatStartTime = currentStartTime.format(formatter);
            String formatEndTime = currentEndTime.format(formatter);
            epics.get(key).setDuration(currentEpic); // устанавливаем правильную продолжительность Эпика
            epics.get(key).setStartTime(formatStartTime); // устанавливаем правильное время начала Эпика
            epics.get(key).setEndTime(formatEndTime); // устанавливаем правильное время конца Эпика
        }
    }

    @Override
    public int generateNewId() {
        return ++id;
    }

    @Override
    public ArrayList<Integer> getCurrentHistory() {
        return historyManager.getHistory();
    }

    public String getCurrentHistoryOnlyId() {
        return historyManager.getTasksHistoryId();
    }
}