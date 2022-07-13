package management;

import elements.Epic;
import elements.Status;
import elements.Subtask;
import elements.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private Integer id;
    private HashMap<Integer, Task> tasks; //храним все задачи
    private HashMap<Integer, Epic> epics; //храним все epic
    private HashMap<Integer, Subtask> subtasks; //храним все подзадачи
    private ArrayList<Task> listOfAllTasks;
    private ArrayList<Epic> listOfAllEpic;
    private ArrayList<Subtask> listOfAllSubtask;
    private int counterSubtasks; // счетчик подзадач
    private int counterSubtasksStatusNew; // счетчик подзадач со статусом NEW
    private int counterSubtasksStatusDone; // счетчик подзадач со статусом DONE
    private int currentId; //текущий номер id

    public Manager() {
        id = 0;
        listOfAllTasks = new ArrayList<>();
        listOfAllEpic = new ArrayList<>();
        listOfAllSubtask = new ArrayList<>();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public void writeNewTask(Task newTask) {
        tasks.put(generateNewId(), newTask);
    }

    public void writeNewEpic(Epic newEpic) {
        epics.put(generateNewId(), newEpic);
        determineAndSetCorrectEpicStatus();
    }

    public void writeNewSubtask(Subtask newSubtask) {
        currentId = generateNewId();
        subtasks.put(currentId, newSubtask);
        epics.get(subtasks.get(currentId).getLinkEpic()).getListOfSubtasks().add(currentId); // добавляем к эпику ссылку на новую подзадачу
        determineAndSetCorrectEpicStatus();
    }

    public ArrayList<Task> getListOfAllTask() {
        if (tasks.isEmpty()) {
            System.out.println("Таблица elements.Task пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Task:");
            for (Integer key : tasks.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(tasks.get(key));
                listOfAllTasks.add(tasks.get(key));
            }
            return listOfAllTasks;
        }
    }

    public ArrayList<Epic> getListOfAllEpic() {
        if (epics.isEmpty()) {
            System.out.println("Таблица elements.Epic пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Epic:");
            for (Integer key : epics.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(epics.get(key));
                listOfAllEpic.add(epics.get(key));
            }
            return listOfAllEpic;
        }
    }

    public ArrayList<Subtask> getListOfAllSubtask() {
        if (subtasks.isEmpty()) {
            System.out.println("Таблица elements.Subtask пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Subtask:");
            for (Integer key : subtasks.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(subtasks.get(key));
                listOfAllSubtask.add(subtasks.get(key));
            }
            return listOfAllSubtask;
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
        System.out.println("Все задачи успешно удалены!");
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); //если удалить все эпики, то подзадачи тоже нужно удалить
        System.out.println("Все эпики успешно удалены!");
    }

    public void deleteAllSubtask() {
        subtasks.clear();
        System.out.println("Все подзадачи успешно удалены!");
        determineAndSetCorrectEpicStatus();
    }

    public Task getTaskById(Integer idTask) {
        System.out.println("Ищем задачу под номером " + idTask);
        if (tasks.get(idTask) != null) {
            System.out.print("id = " + idTask + " ");
            System.out.println(tasks.get(idTask));
            return tasks.get(idTask);
        } else {
            System.out.println("Такой задачи нет!");
            return null;
        }
    }

    public Epic getEpicById(Integer idEpic) {
        System.out.println("Ищем эпик под номером " + idEpic);
        if (epics.get(idEpic) != null) {
            System.out.print("id = " + idEpic + " ");
            System.out.println(epics.get(idEpic));
            return epics.get(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
            return null;
        }
    }

    public Subtask getSubtaskById(Integer idSubtask) {
        System.out.println("Ищем подзадачу под номером " + idSubtask);
        if (subtasks.get(idSubtask) != null) {
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtasks.get(idSubtask));
            return subtasks.get(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
            return null;
        }
    }

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

    public void deleteTaskById(Integer idTask) {
        System.out.println("Удаляем задачу под id= " + idTask);
        if (tasks.get(idTask) != null) {
            tasks.remove(idTask);
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

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
                    if (subtasks.get(currentSubtask).getStatus().equals("NEW")) {
                        counterSubtasksStatusNew++;
                    }
                    if (subtasks.get(currentSubtask).getStatus().equals("DONE")) {
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

    public int generateNewId() {
        return ++id;
    }

}