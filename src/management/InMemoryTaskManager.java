package management;

import elements.Epic;
import elements.Status;
import elements.Subtask;
import elements.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private Integer id;
    private HashMap<Integer, Task> tasks; //храним все задачи
    private HashMap<Integer, Epic> epics; //храним все epic
    private HashMap<Integer, Subtask> subtasks; //храним все подзадачи
    private int counterSubtasks; // счетчик подзадач
    private int counterSubtasksStatusNew; // счетчик подзадач со статусом NEW
    private int counterSubtasksStatusDone; // счетчик подзадач со статусом DONE
    private int currentId; //текущий номер id
    private HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        id = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    @Override
    public void writeNewTask(Task newTask) {
        tasks.put(generateNewId(), newTask);
    }

    @Override
    public void writeNewEpic(Epic newEpic) {
        epics.put(generateNewId(), newEpic);
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void writeNewSubtask(Subtask newSubtask) {
        currentId = generateNewId();
        subtasks.put(currentId, newSubtask);
        epics.get(subtasks.get(currentId).getLinkEpic()).getListOfSubtasks().add(currentId); // добавляем к эпику ссылку на новую подзадачу
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public HashMap<Integer, Task> getListOfAllTask() {
        if (tasks.isEmpty()) {
            System.out.println("Таблица elements.Task пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Task:");
            System.out.println(tasks.values());
            return tasks;
        }
    }

    @Override
    public HashMap<Integer, Epic> getListOfAllEpic() {
        if (epics.isEmpty()) {
            System.out.println("Таблица elements.Epic пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Epic:");
            System.out.println(epics.values());
            return epics;
        }
    }

    @Override
    public HashMap<Integer, Subtask> getListOfAllSubtask() {
        if (subtasks.isEmpty()) {
            System.out.println("Таблица elements.Subtask пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Subtask:");
            System.out.println(subtasks.values());
            return subtasks;
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        System.out.println("Все задачи успешно удалены!");
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); //если удалить все эпики, то подзадачи тоже нужно удалить
        System.out.println("Все эпики успешно удалены!");
    }

    @Override
    public void deleteAllSubtask() {
        subtasks.clear();
        System.out.println("Все подзадачи успешно удалены!");
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public Task getTaskById(Integer idTask) {
        System.out.println("Ищем задачу под номером " + idTask);
        if (tasks.get(idTask) != null) {
            System.out.print("id = " + idTask + " ");
            System.out.println(tasks.get(idTask));
            historyManager.add(tasks.get(idTask));
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
            historyManager.add(epics.get(idEpic));
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
            historyManager.add(subtasks.get(idSubtask));
            return subtasks.get(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
            return null;
        }
    }

    @Override
    public void updateTaskById(Integer idTask, Task newTask) {
        System.out.println("Обновляем задачу под id= " + idTask + " новое значение: ");
        if (tasks.get(idTask) != null) {
            tasks.put(idTask, newTask);
            System.out.print("id = " + idTask + " ");
            System.out.println(tasks.get(idTask));
        } else {
            System.out.println("Такой задачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void updateEpicById(Integer idEpic, Epic newEpic) {
        System.out.println("Обновляем эпик под id= " + idEpic + " новое значение: ");
        if (epics.get(idEpic) != null) {
            epics.put(idEpic, newEpic);
            System.out.print("id = " + idEpic + " ");
            System.out.println(epics.get(idEpic));
        } else {
            System.out.println("Такого эпика нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void updateSubtaskById(Integer idSubtask, Subtask newSubtask) {
        System.out.println("Обновляем подзадачу под id= " + idSubtask + " новое значение: ");
        if (subtasks.get(idSubtask) != null) {
            subtasks.put(idSubtask, newSubtask);
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtasks.get(idSubtask));
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void deleteTaskById(Integer idTask) {
        System.out.println("Удаляем задачу под id= " + idTask);
        if (tasks.get(idTask) != null) {
            tasks.remove(idTask);
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    @Override
    public void deleteEpicById(Integer idEpic) {
        //перед удалением эпика, сначала удаляем подзадачи, которые были с ним связаны
        for (Integer deleteSubtasksId : epics.get(idEpic).getListOfSubtasks()) {
            subtasks.remove(deleteSubtasksId);
        }
        System.out.println("Удаляем эпик под id= " + idEpic);
        if (epics.get(idEpic) != null) {
            epics.remove(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    @Override
    public void deleteSubtaskById(Integer idSubtask) {
        System.out.println("Удаляем подзадачи под id= " + idSubtask);
        if (subtasks.get(idSubtask) != null) {
            epics.get(subtasks.get(idSubtask).getLinkEpic()).getListOfSubtasks().remove(idSubtask); // удаляем из эпика id нашей подзадачи
            subtasks.remove(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    @Override
    public void getSubtaskForEpicById(Integer idEpic) {
        System.out.println("Получаем список всех подзадач эпика по id= " + idEpic);
        if (epics.get(idEpic) != null) {
            System.out.println("Для эпика с id " + idEpic + " подзадачи " + epics.get(idEpic).getListOfSubtasks());
        } else {
            System.out.println("Такого эпика нет!");
        }
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
    }

    @Override
    public int generateNewId() {
        return ++id;
    }

    @Override
    public List<Task> getCurrentHistory() {
        return historyManager.getHistory();
    }
}
