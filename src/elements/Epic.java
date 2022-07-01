package elements;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private ArrayList<Integer> listOfSubtasks; //храним все подзадачи для эпика
    private HashMap<Integer, Epic> epics; //храним все epic

    public Epic() {
        epics = new HashMap<>();
    }

    public Epic(String name, String description, String status, ArrayList<Integer> listOfSubtasks) {
        super(name, description, status);
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
    public String toString() {
        return "elements.Epic{" +
                "listOfSubtasks=" + listOfSubtasks +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public ArrayList<Integer> getListOfSubtasks() {
        return listOfSubtasks;
    }
}
