package com.skypro.diary.definitionOfTask;

import com.skypro.diary.task.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public class Daily extends Task implements DefinitionOfTask {
    private LocalDateTime time;
    private final static String daily = "<ежедневная> ";
    private final static String exception = "Время выполнения задачи выбрано некорректно";

    public Daily(String title, String description, TypesOfTask typesOfTask, LocalDateTime time) {
        super(title, description, typesOfTask);
        setTime(time);
    }

    public void setTime(LocalDateTime time) {
        if (time.isBefore(LocalDateTime.now())) {
            throw new RuntimeException(exception);
        } else {
            this.time = time;
        }
    }
    public LocalDateTime getNextTime() {
        return time.plusDays(1);
    }
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Daily daily = (Daily) o;
        return Objects.equals(time, daily.time);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time);
    }
    @Override
    public String toString() {
        return daily + getTitle() + " " + getDescription() + " " + time;
    }
}
