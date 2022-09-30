package elements;

public class Task {
    private Integer id; //уникальный id
    private String name; //название
    private String description; //описание
    private Status status; //статус NEW, IN_PROGRESS, DONE
    private TaskType taskType; //тип задачи TASK, EPIC, SUBTASK

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        if (id != null) {
            return id;
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "elements.Task{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                "TaskType='" + taskType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
