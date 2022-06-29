
public class Manager {

    private Integer id = 0;
    Task task = new Task();
    Epic epic = new Epic();

    public void writeNewTask(Task newTask){
        System.out.println("Добавлена новая Task");
        task.tasks.put(generateNewId(), newTask);
    }

    public void writeNewEpic(Epic newEpic){
        System.out.println("Добавлена новая Epic");
        epic.epics.put(generateNewId(), newEpic);
    }

    public void printAllTask(){
        if (task.tasks.isEmpty()){
            System.out.println("Таблица Task пуста!");
        } else {
            System.out.println("Печатаю все Task:");
            for (Integer key: task.tasks.keySet()){
                System.out.print("id = " + key + " ");
                System.out.println(task.tasks.get(key));
            }
        }
    }

    public void printAllEpic(){
        if (epic.epics.isEmpty()){
            System.out.println("Таблица Epic пуста!");
        } else {
            System.out.println("Печатаю все Epic:");
            for (Integer key: epic.epics.keySet()){
                System.out.print("id = " + key + " ");
                System.out.println(epic.epics.get(key));
            }
        }
    }

    public void deleteAllTasks(){
        task.tasks.clear();
        System.out.println("Все задачи успешно удалены!");
    }

    public void deleteAllEpics(){
        epic.epics.clear();
        System.out.println("Все Эпики успешно удалены!");
    }

    public void getTaskById(Integer idTask){
        System.out.println("Ищем задачу под номером " + idTask);
        if (task.tasks.get(idTask) != null){
            System.out.print("id = " + idTask + " ");
            System.out.println(task.tasks.get(idTask));
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void getEpicById(Integer idEpic){
        System.out.println("Ищем эпик под номером " + idEpic);
        if (epic.epics.get(idEpic) != null){
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.epics.get(idEpic));
        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void updateTaskById(Integer idTask, Task newTask){
        System.out.println("Обновляем задачу под id= " + idTask + " новое значение: ");
        if (task.tasks.get(idTask) != null){

            task.tasks.put(idTask, newTask);
            System.out.print("id = " + idTask + " ");
            System.out.println(task.tasks.get(idTask));

        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void updateEpicById(Integer idEpic, Epic newEpic){
        System.out.println("Обновляем эпик под id= " + idEpic + " новое значение: ");
        if (epic.epics.get(idEpic) != null){

            epic.epics.put(idEpic, newEpic);
            System.out.print("id = " + idEpic + " ");
            System.out.println(epic.epics.get(idEpic));

        } else {
            System.out.println("Такого эпика нет!");
        }
    }

    public void deleteTaskById(Integer idTask){
        System.out.println("Удаляем задачу под id= " + idTask);
        if (task.tasks.get(idTask) != null){

            task.tasks.remove(idTask);

        } else {
            System.out.println("Такой задачи нет!");
        }
    }
    public int generateNewId() {
        id++;
        return id;
    }

}
