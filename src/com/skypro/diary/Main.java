package com.skypro.diary;

import com.skypro.diary.task.Service;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        startProgram();
    }

    private static void printMenu() {
        System.out.println(
                        "1. Добавить задачу\n"+
                        "2. Удалить задачу\n"+
                        "3. Получить задачу на указанный день\n"+
                        "0. Выход\n"
        );
    }

    public static void startProgram() {
        Service service = new Service();
        printMenu();
        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1:
                        service.createNewTask();
                        break;
                    case 2:
                        service.taskForDelete();
                        break;
                    case 3:
                        service.findTasksForDay();
                        break;
                    case 0:
                        break label;
                }
            } else {
                break;
            }
        }
    }
}
