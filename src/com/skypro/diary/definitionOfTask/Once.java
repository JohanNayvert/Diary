package com.skypro.diary.definitionOfTask;

import com.skypro.diary.task.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public class Once extends Task {
    private LocalDateTime time;
    private final static String once = "<одноразовая> ";
    private final static String exception = "Время выполнения задачи выбрано некорректно";

    public Once(String title, String description, TypesOfTask typesOfTask, LocalDateTime time) {
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
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Once once = (Once) o;
        return Objects.equals(time, once.time);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), time);
    }
    @Override
    public String toString() {
        return once + getTitle() + " " + getDescription() + " " + time;
    }
}
