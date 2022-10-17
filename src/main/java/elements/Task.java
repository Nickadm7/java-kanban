package main.java.elements;

import main.java.elements.utilenum.Status;
import main.java.elements.utilenum.TaskType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private Integer id; //уникальный id
    private String name; //название
    private String description; //описание
    private Status status; //статус NEW, IN_PROGRESS, DONE
    private TaskType taskType; //тип задачи TASK, EPIC, SUBTASK
    private String startTime; //дата и время начала выполнения задачи
    private Integer duration; //продолжительность выполнения задачи
    private String endTime; //дата и время конца выполнения задачи

    public Integer getDuration() {
        return duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status, String startTime, Integer duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        calculateEndTime();
    }

    public void calculateEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(this.startTime, formatter);
        LocalDateTime stopTime = startTime.plusMinutes(this.duration);
        String stopTimeOut = stopTime.format(formatter);
        this.setEndTime(stopTimeOut);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        if (id != null) {
            return id;
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime='" + startTime + '\'' +
                ", duration(min)=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(startTime, task.startTime) && Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, startTime, duration);
    }
}