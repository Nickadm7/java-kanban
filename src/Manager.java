public class Manager {

    private Integer id = 0;
    Task task = new Task();
    Epic epic = new Epic();
    Subtask subtask = new Subtask();
    int counterSubtasks; // счетчик подзадач
    int counterSubtasksStatusNew; // счетчик подзадач со статусом NEW
    int counterSubtasksStatusDone; // счетчик подзадач со статусом DONE

    public void writeNewTask(Task newTask) {
        task.tasks.put(generateNewId(), newTask);
    }

    public void writeNewEpic(Epic newEpic) {
        epic.epics.put(generateNewId(), newEpic);
        determineAndSetCorrectEpicStatus();
    }

    public void writeNewSubtask(Subtask newSubtask) {
        subtask.subtasks.put(generateNewId(), newSubtask);
        determineAndSetCorrectEpicStatus();
    }

    public void printAllTask() {
        if (task.tasks.isEmpty()) {
            System.out.println("Таблица Task пуста!");
        } else {
            System.out.println("Печатаю все Task:");
            for (Integer key : task.tasks.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(task.tasks.get(key));
            }
        }
    }

    public void printAllEpic() {
        if (epic.epics.isEmpty()) {
            System.out.println("Таблица Epic пуста!");
        } else {
            System.out.println("Печатаю все Epic:");
            for (Integer key : epic.epics.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(epic.epics.get(key));
            }
        }
    }

    public void printAllSubtask() {
        if (subtask.subtasks.isEmpty()) {
            System.out.println("Таблица Subtask пуста!");
        } else {
            System.out.println("Печатаю все Subtask:");
            for (Integer key : subtask.subtasks.keySet()) {
                System.out.print("id = " + key + " ");
                System.out.println(subtask.subtasks.get(key));
            }
        }
    }

    public void deleteAllTasks() {
        task.tasks.clear();
        System.out.println("Все задачи успешно удалены!");
        determineAndSetCorrectEpicStatus();
    }

    public void deleteAllEpics() {
        epic.epics.clear();
        System.out.println("Все эпики успешно удалены!");
    }

    public void deleteAllSubtask() {
        subtask.subtasks.clear();
        System.out.println("Все подзадачи успешно удалены!");
        determineAndSetCorrectEpicStatus();
    }

    public void getTaskById(Integer idTask) {
        System.out.println("Ищем задачу под номером " + idTask);
        if (task.tasks.get(idTask) != null) {
            System.out.print("id = " + idTask + " ");
            System.out.println(task.tasks.get(idTask));
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void getEpicById(Integer idEpic) {
        System.out.println("Ищем эпик под номером " + idEpic);
        if (epic.epics.get(idEpic) != null) {
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.epics.get(idEpic));
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void getSubtaskById(Integer idSubtask) {
        System.out.println("Ищем подзадачу под номером " + idSubtask);
        if (subtask.subtasks.get(idSubtask) != null) {
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtask.subtasks.get(idSubtask));
        } else {
            System.out.println("Такой подзадачи нет!");
        }
    }

    public void updateTaskById(Integer idTask, Task newTask) {
        System.out.println("Обновляем задачу под id= " + idTask + " новое значение: ");
        if (task.tasks.get(idTask) != null) {
            task.tasks.put(idTask, newTask);
            System.out.print("id = " + idTask + " ");
            System.out.println(task.tasks.get(idTask));
        } else {
            System.out.println("Такой задачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void updateEpicById(Integer idEpic, Epic newEpic) {
        System.out.println("Обновляем эпик под id= " + idEpic + " новое значение: ");
        if (epic.epics.get(idEpic) != null) {
            epic.epics.put(idEpic, newEpic);
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.epics.get(idEpic));
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void updateSubtaskById(Integer idSubtask, Subtask newSubtask) {
        System.out.println("Обновляем подзадачу под id= " + idSubtask + " новое значение: ");
        if (subtask.subtasks.get(idSubtask) != null) {
            subtask.subtasks.put(idSubtask, newSubtask);
            System.out.print("id = " + idSubtask + " ");
            System.out.println(subtask.subtasks.get(idSubtask));
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void deleteTaskById(Integer idTask) {
        System.out.println("Удаляем задачу под id= " + idTask);
        if (task.tasks.get(idTask) != null) {
            task.tasks.remove(idTask);
        } else {
            System.out.println("Такой задачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void deleteEpicById(Integer idEpic) {
        System.out.println("Удаляем эпик под id= " + idEpic);
        if (epic.epics.get(idEpic) != null) {
            epic.epics.remove(idEpic);
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void deleteSubtaskById(Integer idSubtask) {
        System.out.println("Удаляем подзадачи под id= " + idSubtask);
        if (subtask.subtasks.get(idSubtask) != null) {
            subtask.subtasks.remove(idSubtask);
        } else {
            System.out.println("Такой подзадачи нет!");
        }
        determineAndSetCorrectEpicStatus();
    }

    public void getSubtaskForEpicById(Integer idEpic) {
        System.out.println("Получаем список всех подзадач эпика по id= " + idEpic);
        if (epic.epics.get(idEpic) != null) {
            System.out.println("Для эпика с id " + idEpic + " подзадачи " + epic.epics.get(idEpic).listOfSubtasks);
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void determineAndSetCorrectEpicStatus() {
        for (Integer key : epic.epics.keySet()) {
            counterSubtasks = 0; // счетчик подзадач
            counterSubtasksStatusNew = 0; // счетчик подзадач со статусом NEW
            counterSubtasksStatusDone = 0; // счетчик подзадач со статусом DONE

            epic.epics.get(key).status = "IN_PROGRESS"; // по-умолчанию статус IN_PROGRESS
            if (epic.epics.get(key).listOfSubtasks.isEmpty()) {
                epic.epics.get(key).status = "NEW";
            }
            for (Integer list : epic.epics.get(key).listOfSubtasks) {
                counterSubtasks++;
                if (subtask.subtasks.containsKey(list)) {
                    if (subtask.subtasks.get(list).status.equals("NEW")) {
                        counterSubtasksStatusNew++;
                    }
                    if (subtask.subtasks.get(list).status.equals("DONE")) {
                        counterSubtasksStatusDone++;
                    }
                }
            }
            if (counterSubtasks == counterSubtasksStatusNew && counterSubtasks != 0) {
                epic.epics.get(key).status = "NEW";
            }
            if (counterSubtasks == counterSubtasksStatusDone && counterSubtasks != 0) {
                epic.epics.get(key).status = "DONE";
            }
        }
    }

    public int generateNewId() {
        id++;
        return id;
    }

}
