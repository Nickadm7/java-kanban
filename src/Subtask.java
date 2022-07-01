import java.util.HashMap;

public class Subtask extends Task {

    private Integer linkEpic; //номер связанного эпика для подзадачи
    private HashMap<Integer, Subtask> subtasks; //храним все подзадачи

    public Subtask() {
        subtasks = new HashMap<>();
    }

    public Subtask(String name, String description, String status, Integer linkEpic) {
        super(name, description, status);
        this.linkEpic = linkEpic;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public Integer getLinkEpic() {
        return linkEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "linkEpic=" + linkEpic +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
