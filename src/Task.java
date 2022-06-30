import java.util.HashMap;

public class Task {

    protected String name; //название
    protected String description; //описание
    protected String status; //статус NEW, IN_PROGRESS, DONE
    protected HashMap<Integer, Task> tasks = new HashMap<>(); //храним все задачи

    public Task() {
    }

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
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
