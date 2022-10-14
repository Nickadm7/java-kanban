package elements;

import elements.utilenum.Status;

import java.util.ArrayList;

public class Epic extends Task {

    private transient String endTime; //дата и время конца выполнения задачи
    private ArrayList<Integer> listOfSubtasks; //храним все подзадачи для эпика

    public Epic(String name, String description, Status status, String startTime, Integer duration) {
        super(name, description, status, startTime, duration);
    }

    public Epic(String name, String description, Status status, ArrayList<Integer> listOfSubtasks) {
        super(name, description, status);
        this.listOfSubtasks = listOfSubtasks;
    }

    public Epic(String name, String description, Status status, String startTime, Integer duration, ArrayList<Integer> listOfSubtasks) {
        super(name, description, status, startTime, duration);
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
   public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setListOfSubtasks(ArrayList<Integer> listOfSubtasks) {
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
    public String toString() {
        return "elements.Epic{" +
                "listOfSubtasks=" + listOfSubtasks +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", duration(min)='" + getDuration() + '\'' +
                '}';
    }

    public ArrayList<Integer> getListOfSubtasks() {
        return listOfSubtasks;
    }
}