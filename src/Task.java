import java.util.HashMap;

public class Task {

    private String name; //название
    private String description; //описание
    private String status; //статус NEW, IN_PROGRESS, DONE
    private HashMap<Integer, Task> tasks; //храним все задачи

    public Task() {
        tasks = new HashMap<>();
    }

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
