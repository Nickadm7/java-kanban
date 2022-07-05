package elements;

public class Subtask extends Task {
    private Integer linkEpic; //номер связанного эпика для подзадачи

    public Subtask(String name, String description, String status, Integer linkEpic) {
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
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
