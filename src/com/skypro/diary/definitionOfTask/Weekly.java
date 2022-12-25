package com.skypro.diary.definitionOfTask;

import com.skypro.diary.task.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public class Weekly extends Task implements DefinitionOfTask{
    private LocalDateTime time;
    private final static String weekly = "<еженедельная> ";
    private final static String exception = "Время выполнения задачи выбрано некорректно";

    public Weekly(String title, String description, TypesOfTask typesOfTask, LocalDateTime time) {
        super(title, description, typesOfTask);
        setTime(time);
    }

    public void setTime(LocalDateTime time){
        if (time.isBefore(LocalDateTime.now())) throw new RuntimeException(exception); else this.time = time;
    }
    @Override
    public LocalDateTime getNextTime() {
        return time.plusWeeks(1);
    }
    public LocalDateTime getTime(){
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Weekly weekly = (Weekly) o;
        return Objects.equals(time, weekly.time);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time);
    }
    public String toString() {
        return weekly + getTitle() + " " + getDescription() + " " + time;
    }
}
