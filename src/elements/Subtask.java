package elements;

public class Subtask extends Task {
    private Integer linkEpic; //номер связанного эпика для подзадачи

    public Subtask(String name, String description, Status status, Integer linkEpic) {
        super(name, description, status);
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
                '}';
    }
}
