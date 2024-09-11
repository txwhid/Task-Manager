package com.example.taskmanager;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TaskManager {
    private static final String FILE_NAME = "tasks.txt";
    private static List<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Task Manager");
        System.out.println("Commands: add <task>, view, delete <task_number>, exit");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            String[] parts = command.split(" ", 2);
            String action = parts[0];

            switch (action) {
                case "add":
                    if (parts.length > 1) {
                        addTask(parts[1]);
                    } else {
                        System.out.println("Please specify a task.");
                    }
                    break;
                case "view":
                    viewTasks();
                    break;
                case "delete":
                    if (parts.length > 1) {
                        deleteTask(parts[1]);
                    } else {
                        System.out.println("Please specify a task number.");
                    }
                    break;
                case "exit":
                    saveTasks();
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static void loadTasks() {
        try {
            if (Files.exists(Paths.get(FILE_NAME))) {
                tasks = Files.readAllLines(Paths.get(FILE_NAME));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveTasks() {
        try {
            Files.write(Paths.get(FILE_NAME), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTask(String task) {
        tasks.add(task);
        System.out.println("Task added.");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    private static void deleteTask(String taskNumberStr) {
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number.");
            } else {
                tasks.remove(taskNumber - 1);
                System.out.println("Task removed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
        }
    }
}
