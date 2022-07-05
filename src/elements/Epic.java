package elements;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> listOfSubtasks; //храним все подзадачи для эпика

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

    public ArrayList<Integer> getListOfSubtasks() {
        return listOfSubtasks;
    }
}
