import java.util.HashMap;

public class Subtask extends Task {

    protected Integer linkEpic; //номер связанного эпика для подзадачи
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>(); //храним все подзадачи

    public Subtask() {
    }

    public Subtask(String name, String description, String status, Integer linkEpic) {
        super(name, description, status);
        this.linkEpic = linkEpic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "linkEpic=" + linkEpic +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
