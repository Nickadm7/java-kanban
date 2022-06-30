import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    protected ArrayList<Integer> listOfSubtasks; //храним все подзадачи для эпика
    protected HashMap<Integer, Epic> epics = new HashMap<>(); //храним все epic

    public Epic() {
    }

    public Epic(String name, String description, String status, ArrayList<Integer> listOfSubtasks) {
        super(name, description, status);
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "listOfSubtasks=" + listOfSubtasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
