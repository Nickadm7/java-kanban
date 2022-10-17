package main.java.elements;

import main.java.elements.utilenum.Status;

import java.util.List;

public class Epic extends Task {

    private transient String endTime; //дата и время конца выполнения задачи
    private List<Integer> listOfSubtasks; //храним все подзадачи для эпика

    public Epic(String name, String description, Status status, String startTime, Integer duration) {
        super(name, description, status, startTime, duration);
    }

    public Epic(String name, String description, Status status, List<Integer> listOfSubtasks) {
        super(name, description, status);
        this.listOfSubtasks = listOfSubtasks;
    }

    public Epic(String name, String description, Status status, String startTime, Integer duration, List<Integer> listOfSubtasks) {
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

    public void setListOfSubtasks(List<Integer> listOfSubtasks) {
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
    public String toString() {
        return "main.java.elements.Epic{" +
                "listOfSubtasks=" + listOfSubtasks +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", duration(min)='" + getDuration() + '\'' +
                '}';
    }

    public List<Integer> getListOfSubtasks() {
        return listOfSubtasks;
    }
}