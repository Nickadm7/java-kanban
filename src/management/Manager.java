package management;

import elements.Epic;
import elements.Subtask;
import elements.Task;

import java.util.ArrayList;

public class Manager {

    private Integer id;
    private Task task;
    private Epic epic;
    private Subtask subtask;
    private ArrayList<Task> listOfAllTasks;
    private ArrayList<Epic> listOfAllEpic;
    private ArrayList<Subtask> listOfAllSubtask;
    private int counterSubtasks; // счетчик подзадач
    private int counterSubtasksStatusNew; // счетчик подзадач со статусом NEW
    private int counterSubtasksStatusDone; // счетчик подзадач со статусом DONE
    private int currentId; //текущий номер id

    public Manager() {
        id = 0;
        task = new Task();
        epic = new Epic();
        subtask = new Subtask();
        listOfAllTasks = new ArrayList<>();
        listOfAllEpic = new ArrayList<>();
        listOfAllSubtask = new ArrayList<>();
    }

    public void writeNewTask(Task newTask) {
        task.getTasks().put(generateNewId(), newTask);
    }

    public void writeNewEpic(Epic newEpic) {
        epic.getEpics().put(generateNewId(), newEpic);
        determineAndSetCorrectEpicStatus();
    }

    public void writeNewSubtask(Subtask newSubtask) {
        currentId = generateNewId();
        subtask.getSubtasks().put(currentId, newSubtask);
        epic.getEpics().get(subtask.getSubtasks().get(currentId).getLinkEpic()).getListOfSubtasks().add(currentId); // добавляем к эпику ссылку на новую подзадачу
        determineAndSetCorrectEpicStatus();
    }

    public ArrayList<Task> getListOfAllTask() {
        if (task.getTasks().isEmpty()) {
            System.out.println("Таблица elements.Task пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Task:");
            for (Integer key : task.getTasks().keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(task.getTasks().get(key));
                listOfAllTasks.add(task.getTasks().get(key));
            }
            return listOfAllTasks;
        }
    }

    public ArrayList<Epic> getListOfAllEpic() {
        if (epic.getEpics().isEmpty()) {
            System.out.println("Таблица elements.Epic пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Epic:");
            for (Integer key : epic.getEpics().keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(epic.getEpics().get(key));
                listOfAllEpic.add(epic.getEpics().get(key));
            }
            return listOfAllEpic;
        }
    }

    public ArrayList<Subtask> getListOfAllSubtask() {
        if (subtask.getSubtasks().isEmpty()) {
            System.out.println("Таблица elements.Subtask пуста!");
            return null;
        } else {
            System.out.println("Печатаю все elements.Subtask:");
            for (Integer key : subtask.getSubtasks().keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(subtask.getSubtasks().get(key));
                listOfAllSubtask.add(subtask.getSubtasks().get(key));
            }
            return listOfAllSubtask;
        }
    }

    public void deleteAllTasks() {
        task.getTasks().clear();
        System.out.println("Все задачи успешно удалены!");
    }

    public void deleteAllEpics() {
        epic.getEpics().clear();
        subtask.getSubtasks().clear(); //если удалить все эпики, то подзадачи тоже нужно удалить
        System.out.println("Все эпики успешно удалены!");
    }

    public void deleteAllSubtask() {
        subtask.getSubtasks().clear();
        System.out.println("Все подзадачи успешно удалены!");
        determineAndSetCorrectEpicStatus();
    }

    public Task getTaskById(Integer idTask) {
        System.out.println("Ищем задачу под номером " + idTask);
        if (task.getTasks().get(idTask) != null) {
            System.out.print("id = " + idTask + " ");
            System.out.println(task.getTasks().get(idTask));
            return task.getTasks().get(idTask);
        } else {
            System.out.println("Такой задачи нет!");
            return null;
        }
    }

    public Epic getEpicById(Integer idEpic) {
        System.out.println("Ищем эпик под номером " + idEpic);
        if (epic.getEpics().get(idEpic) != null) {
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.getEpics().get(idEpic));
            return epic.getEpics().get(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
            return null;
        }
    }

    public Subtask getSubtaskById(Integer idSubtask) {
        System.out.println("Ищем подзадачу под номером " + idSubtask);
        if (subtask.getSubtasks().get(idSubtask) != null) {
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtask.getSubtasks().get(idSubtask));
            return subtask.getSubtasks().get(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
            return null;
        }
    }

    public void updateTaskById(Integer idTask, Task newTask) {
        System.out.println("Обновляем задачу под id= " + idTask + " новое значение: ");
        if (task.getTasks().get(idTask) != null) {
            task.getTasks().put(idTask, newTask);
            System.out.print("id = " + idTask + " ");
            System.out.println(task.getTasks().get(idTask));
        } else {
            System.out.println("Такой задачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void updateEpicById(Integer idEpic, Epic newEpic) {
        System.out.println("Обновляем эпик под id= " + idEpic + " новое значение: ");
        if (epic.getEpics().get(idEpic) != null) {
            epic.getEpics().put(idEpic, newEpic);
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.getEpics().get(idEpic));
        } else {
            System.out.println("Такого эпика нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void updateSubtaskById(Integer idSubtask, Subtask newSubtask) {
        System.out.println("Обновляем подзадачу под id= " + idSubtask + " новое значение: ");
        if (subtask.getSubtasks().get(idSubtask) != null) {
            subtask.getSubtasks().put(idSubtask, newSubtask);
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtask.getSubtasks().get(idSubtask));
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void deleteTaskById(Integer idTask) {
        System.out.println("Удаляем задачу под id= " + idTask);
        if (task.getTasks().get(idTask) != null) {
            task.getTasks().remove(idTask);
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void deleteEpicById(Integer idEpic) {
        //перед удалением эпика, сначала удаляем подзадачи, которые были с ним связаны
        for (Integer deleteSubtasksId : epic.getEpics().get(idEpic).getListOfSubtasks()) {
            subtask.getSubtasks().remove(deleteSubtasksId);
        }
        System.out.println("Удаляем эпик под id= " + idEpic);
        if (epic.getEpics().get(idEpic) != null) {
            epic.getEpics().remove(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void deleteSubtaskById(Integer idSubtask) {
        System.out.println("Удаляем подзадачи под id= " + idSubtask);
        if (subtask.getSubtasks().get(idSubtask) != null) {
            epic.getEpics().get(subtask.getSubtasks().get(idSubtask).getLinkEpic()).getListOfSubtasks().remove(idSubtask); // удаляем из эпика id нашей подзадачи
            subtask.getSubtasks().remove(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void getSubtaskForEpicById(Integer idEpic) {
        System.out.println("Получаем список всех подзадач эпика по id= " + idEpic);
        if (epic.getEpics().get(idEpic) != null) {
            System.out.println("Для эпика с id " + idEpic + " подзадачи " + epic.getEpics().get(idEpic).getListOfSubtasks());
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void determineAndSetCorrectEpicStatus() {
        for (Integer key : epic.getEpics().keySet()) {
            counterSubtasks = 0; // счетчик подзадач
            counterSubtasksStatusNew = 0; // счетчик подзадач со статусом NEW
            counterSubtasksStatusDone = 0; // счетчик подзадач со статусом DONE

            epic.getEpics().get(key).setStatus("IN_PROGRESS"); // по-умолчанию статус IN_PROGRESS
            if (epic.getEpics().get(key).getListOfSubtasks().isEmpty()) {
                epic.getEpics().get(key).setStatus("NEW");
            }
            for (Integer currentSubtask : epic.getEpics().get(key).getListOfSubtasks()) {
                counterSubtasks++;
                if (subtask.getSubtasks().containsKey(currentSubtask)) {
                    if (subtask.getSubtasks().get(currentSubtask).getStatus().equals("NEW")) {
                        counterSubtasksStatusNew++;
                    }
                    if (subtask.getSubtasks().get(currentSubtask).getStatus().equals("DONE")) {
                        counterSubtasksStatusDone++;
                    }
                }
            }
            if (counterSubtasks != 0 && counterSubtasks == counterSubtasksStatusNew) {
                epic.getEpics().get(key).setStatus("NEW");
            }
            if (counterSubtasks != 0 && counterSubtasks == counterSubtasksStatusDone) {
                epic.getEpics().get(key).setStatus("DONE");
            }
        }
    }

    public int generateNewId() {
        return ++id;
    }

}
