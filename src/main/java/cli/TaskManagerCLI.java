package cli;

import cli.helper.TaskListPrinter;
import exception.NoSuchTaskException;
import service.TaskService;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.*;

public class TaskManagerCLI {

    private final Scanner scanner;
    private final TaskService taskService;
    private final TaskListPrinter taskListPrinter;

    public TaskManagerCLI(TaskService taskService) {
        this.scanner = new Scanner(in);
        this.taskService = taskService;
        this.taskListPrinter = new TaskListPrinter();
    }

    public void run() {
        printHelpMenu();

        String command;
        while (!(command = scanner.next().toLowerCase()).equals("exit")) {
            switch (command) {
                case "add" -> add();
                case "update" -> update();
                case "delete" -> delete();
                case "mark-todo" -> markTodo();
                case "mark-done" -> markDone();
                case "mark-in-progress" -> markInProgress();
                case "list" -> list();
                case "list-todo" -> listTodo();
                case "list-in-progress" -> listInProgress();
                case "list-done" -> listDone();
                case "help" -> printHelpMenu();
                default -> out.println("Unknown command. Type 'help' to see the list of commands or 'exit' to exit.");
            }
        }

        exit();
    }

    private void add() {
        String description = scanner.nextLine().trim();
        int id = taskService.addTask(description);
        out.printf("Task added successfully (ID: %d)\n", id);
    }

    private void update() {
        try {
            int id = scanner.nextInt();
            String description = scanner.nextLine().trim();
            taskService.updateTask(id, description);
            out.println("Task updated successfully");
        } catch (NoSuchTaskException e) {
            out.println(e.getMessage());
        }
    }

    private void delete() {
        try {
            int id = scanner.nextInt();
            taskService.deleteTask(id);
            out.println("Task deleted successfully");
            scanner.nextLine();
        } catch (NoSuchTaskException e) {
            out.println(e.getMessage());
        }
    }

    private void markTodo() {
        try {
            int id = scanner.nextInt();
            taskService.markTaskAsTodo(id);
            out.println("Task marked as Todo successfully");
            scanner.nextLine();
        } catch (NoSuchTaskException e) {
            out.println(e.getMessage());
        }
    }

    private void markInProgress() {
        try {
            int id = scanner.nextInt();
            taskService.markTaskAsInProgress(id);
            out.println("Task marked as In-Progress successfully");
            scanner.nextLine();
        } catch (NoSuchTaskException e) {
            out.println(e.getMessage());
        }
    }

    private void markDone() {
        try {
            int id = scanner.nextInt();
            taskService.markTaskAsDone(id);
            out.println("Task marked as Done successfully");
            scanner.nextLine();
        } catch (NoSuchTaskException e) {
            out.println(e.getMessage());
        }
    }

    private void list() {
        taskListPrinter.printTaskList(taskService.getAllTasks());
        scanner.nextLine();
    }

    private void listTodo() {
        taskListPrinter.printTaskList(taskService.getTodoTasks());
        scanner.nextLine();
    }

    private void listInProgress() {
        taskListPrinter.printTaskList(taskService.getInProgressTasks());
        scanner.nextLine();
    }

    private void listDone() {
        taskListPrinter.printTaskList(taskService.getDoneTasks());
        scanner.nextLine();
    }

    private void printHelpMenu() {
        String help = """
                - add [description] : Add a new task
                - update [id] [description] : Update a task
                - delete [id] : Delete a task
                - mark-todo [id] : Mark a task as Todo
                - mark-in-progress [id] : Mark a task as In-Progress
                - mark-done [id] : Mark a task as Done
                - list : List all tasks
                - list-todo : List all Todo tasks
                - list-in-progress : List all In-Progress tasks
                - list-done : List all Done tasks
                - exit : Exit the program
                """;
        out.println(help);
    }

    private void exit() {
        try {
            out.println("Saving the tasks...");
            taskService.saveTasks();
            out.println("Done! Good luck Have Fun! (❁´◡`❁)");
        } catch (IOException e) {
            out.println("Something went wrong and couldn't save the tasks! ¯\\_(ツ)_/¯");
        }
    }
}
