package elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Subtask extends Task {
    private Integer linkEpic; //номер связанного эпика для подзадачи
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.dd.MMТHH:mm");

    public Subtask(String name, String description, Status status, Integer linkEpic) {
        super(name, description, status);
        this.linkEpic = linkEpic;
    }

    public Subtask(String name, String description, Status status, Integer duration, String startTime, Integer linkEpic) {
        super(name, description, status, duration, startTime);
        this.linkEpic = linkEpic;
    }

    public Integer getLinkEpic() {
        return linkEpic;
    }

    @Override
    public String toString() {
        return "elements.Subtask{" +
                "linkEpic=" + linkEpic +
                ", id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", TaskType='" + getTaskType() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime() + '\'' +
                ", duration(min)='" + getDuration() + '\'' +
                ", endTime='" + getEndTime() + '\'' +
                '}';
    }
}
