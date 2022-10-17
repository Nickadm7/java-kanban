package main.java.elements;

import main.java.elements.utilenum.Status;

public class Subtask extends Task {
    private Integer linkEpic; //номер связанного эпика для подзадачи

    public Subtask(String name, String description, Status status, Integer linkEpic) {
        super(name, description, status);
        this.linkEpic = linkEpic;
    }

    public Subtask(String name, String description, Status status, String startTime, Integer duration, Integer linkEpic) {
        super(name, description, status, startTime, duration);
        this.linkEpic = linkEpic;
    }


    public Integer getLinkEpic() {
        return linkEpic;
    }

    @Override
    public String toString() {
        return "main.java.elements.Subtask{" +
                "linkEpic=" + linkEpic +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", TaskType='" + getTaskType() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", duration(min)='" + getDuration() + '\'' +
                '}';
    }
}