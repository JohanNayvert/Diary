package com.skypro.diary.task;

import com.skypro.diary.definitionOfTask.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class Service {

    Task.TypesOfTask typesOfTask;
    private final Map<Long, Task> actualTaskBook;
    private static final String personal = "<личная>";
    private static final String working = "<рабочая>";

    public Service() {
        this.actualTaskBook = new HashMap<>();
    }

    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public Task newTask() {
        try {
            try {
                Scanner scanner = scanner();
                System.out.println("Выберите тип задачи: 1." + personal + "; или по дефолту будет выбран тип " + working);
                int type = scanner.nextInt();
                if (type != 1) {
                    typesOfTask = Task.TypesOfTask.WORKING;
                } else {
                    typesOfTask = Task.TypesOfTask.PERSONAL;
                }
                System.out.println("Выберите частоту выполнения задачи: 1. ежедневно; 2. еженедельно; 3. ежемесячно; 4. ежегодично. Либо задача выполнится один раз.");
                int frequency = scanner.nextInt();
                System.out.println("Введите заголовок");
                String title = scanner().nextLine();
                System.out.println("Введите описание");
                String description = scanner().nextLine();
                System.out.println("Введите дату и время дедлайна в виде <dd.MM.yyyy HH:mm>");
                LocalDateTime time = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                try {
                    if (time.isBefore(LocalDateTime.now())) {
                        throw new RuntimeException();
                    }
                } catch (RuntimeException e) {
                    return null;
                }
                System.out.println("Задача была добавлена");
                if (frequency == 4) {
                    return new Yearly(title, description, typesOfTask, time);
                } else if (frequency == 3) {
                    return new Monthly(title, description, typesOfTask, time);
                } else if (frequency == 2) {
                    return new Weekly(title, description, typesOfTask, time);
                } else if (frequency == 1) {
                    return new Daily(title, description, typesOfTask, time);
                } else {
                    return new Once(title, description, typesOfTask, time);
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректно введено значение");
            }
        } catch (DateTimeException e) {
            System.out.println("Некорректно введено время");
        }
        return null;
    }

    public void createNewTask() {
        Task task = newTask();
        if (task != null) {
            actualTaskBook.put(task.getId(), task);
        } else {
            System.out.println("Задача не была добавлена. Данные были введены некорректно");
        }
    }

    public void findTasksForDay() {
        System.out.println("Введите дату в виде <dd.MM.yyyy HH:mm>");
        try {
            try {
                Scanner scanner = scanner();
                LocalDateTime time = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));


                Map<Long, Task> tasksForDay = new HashMap<>();
                for (Map.Entry<Long, Task> entry : actualTaskBook.entrySet()) {
                    if (entry.getValue().getTime().toLocalDate().isAfter(ChronoLocalDate.from(time))) {
                        continue;
                    }
                    if (entry.getValue().getClass().equals(Daily.class)) {
                        for (int i = 0; i < 10000; i++) {
                            if (entry.getValue().getTime().toLocalDate().isEqual(entry.getValue().getTime().toLocalDate().plusDays(i))) {
                                tasksForDay.put(entry.getKey(), entry.getValue());
                            }
                        }
                    } else if (entry.getValue().getClass().equals(Weekly.class)) {
                        for (int i = 0; i < 3000; i++) {
                            if (time.isEqual(ChronoLocalDateTime.from(entry.getValue().getTime().toLocalDate().plusWeeks(i)))) {
                                tasksForDay.put(entry.getKey(), entry.getValue());
                            }
                        }
                    } else if (entry.getValue().getClass().equals(Monthly.class)) {
                        for (int i = 0; i < 1000; i++) {
                            if (time.isEqual(ChronoLocalDateTime.from(entry.getValue().getTime().toLocalDate().plusMonths(i)))) {
                                tasksForDay.put(entry.getKey(), entry.getValue());
                            }
                        }
                    } else if (entry.getValue().getClass().equals(Yearly.class)) {
                        for (int i = 0; i < 200; i++) {
                            if (time.isEqual(ChronoLocalDateTime.from(entry.getValue().getTime().toLocalDate().plusYears(i)))) {
                                tasksForDay.put(entry.getKey(), entry.getValue());
                            }
                        }
                    } else if (entry.getValue().getClass().equals(Once.class)) {
                        if (time.isEqual(ChronoLocalDateTime.from(entry.getValue().getTime().toLocalDate()))) {
                            tasksForDay.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                for (Map.Entry<Long, Task> entry : tasksForDay.entrySet()) System.out.println(entry.getValue().getInfo());
            } catch (InputMismatchException e) {
                System.out.println("Вы ввели некорректное значение даты");
            }
        } catch (DateTimeException e) {
            System.out.println("Вы ввели некорректное значение даты");
        }
    }

    public void showAllActualTasks() {
        for (Map.Entry<Long, Task> pair : actualTaskBook.entrySet()) System.out.println(pair);
    }

    public Task findTask(Long id) {
        return actualTaskBook.get(id);
    }

    public Task taskForDelete() {
        try {
            System.out.println("Введите id задачи, которую хотите удалить");
            return findTask(scanner().nextLong());
        } catch (InputMismatchException e) {
            System.out.println("Вы ввели некорректное значение, попробуйте снова");
        }
        return taskForDelete();
    }

    public void deleteTask(Long id) {
        actualTaskBook.remove(id);
    }

    @Override
    public String toString() {
        return actualTaskBook.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Service taskBook1 = (Service) o;
        return Objects.equals(actualTaskBook, taskBook1.actualTaskBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actualTaskBook);
    }
}