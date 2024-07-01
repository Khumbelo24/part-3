package com.mycompany.poe1_2and3;

import javax.swing.JOptionPane;

public class POE1_2AND3 {

    private static String username = "right or wrong username";
    private static String password = "right or wrong password";
    private static String name;
    private static String surname;

    private static int numberOfTasks;
    private static int theTotalHours = 0;
    private static int CounterOfTasks = 0;

    //this will be my arrays so to store task-related data
    private static String[] developer = new String[100]; // for a fixed size to make it a bit more better to deal with
    private static String[] taskNames = new String[100];
    private static String[] taskID = new String[100];
    private static int[] taskDuration = new int[100];
    private static String[] taskStatus = new String[100];

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            // to get my user's choice im using this below
            String choice = JOptionPane.showInputDialog(
                    "Choose an option:\n1. Register\n2. Login\n3. Exit"
            );

            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    if (!loggedIn()) {
                        JOptionPane.showMessageDialog(null, "Please log in first.");
                        continue;
                    }
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unrecognized choice. Please choose from '1', '2', or '3'.");
            }
        }
    }

    public static void register() {
        name = JOptionPane.showInputDialog("Please enter your name");
        surname = JOptionPane.showInputDialog("Please enter your surname");

        do {
            username = JOptionPane.showInputDialog("Enter your username");
            if (!checkUserName(username)) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
            }
        } while (!checkUserName(username));
        JOptionPane.showMessageDialog(null, "Username successfully captured.");

        do {
            password = JOptionPane.showInputDialog("Enter your password");
            if (!checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            }
        } while (!checkPasswordComplexity(password));
        JOptionPane.showMessageDialog(null, "Password successfully captured.");

        JOptionPane.showMessageDialog(null, "Name: " + name + "\nSurname: " + surname + "\nUsername: " + username + "\nPassword: " + password);
        JOptionPane.showMessageDialog(null, "Registration successful");
        JOptionPane.showMessageDialog(null, "NEXT step, LOGGING IN !!");

        loggedIn();
    }

    public static boolean loggedIn() {
        boolean loginUser = false;

        while (!loginUser) {
            String enteredUsername = JOptionPane.showInputDialog("Enter your username:");
            if (enteredUsername == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return false;
            }
            String enteredPassword = JOptionPane.showInputDialog("Enter your password:");
            if (enteredPassword == null) {
                JOptionPane.showMessageDialog(null, "Login cancelled.");
                return false;
            }

            if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                JOptionPane.showMessageDialog(null, "Welcome " + name + ", " + surname + " it is great to see you again.");
                loginUser = true;
                taskOperations(); // After login,the more importtant part comes in
            } else {
                JOptionPane.showMessageDialog(null, "Username or Password Incorrect. Please try again.");
            }
        }

        return true;
    }

    public static void taskOperations() {
        boolean exitTasks = false;

        JOptionPane.showMessageDialog(null, "Welcome to EasyKanbun");

        while (!exitTasks) {
            String choice = JOptionPane.showInputDialog(
                    "Choose an option:\n1. Add Task\n2. Show Report\n3. Tasks with status Done\n4. Task with longest duration\n5. Search task by name\n6. Search task by developer\n7. Delete task by name\n8. Quit"
            );

            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    displayAllTasks();
                    break;
                case "3":
                    displayTasksWithStatus("Done");
                    break;
                case "4":
                    displayTaskWithLongestDuration();
                    break;
                case "5":
                    searchTaskByName();
                    break;
                case "6":
                    searchTaskByDeveloper();
                    break;
                case "7":
                    deleteTaskByName();
                    break;
                case "8":
                    exitTasks = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unrecognized choice. Please choose from '1' to '8'.");
            }
        }
    }

    public static void addTask() {
        int tasksToAdd = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks you wish to enter:"));

        for (int t = 0; t < tasksToAdd; t++) {
            if (CounterOfTasks >= taskNames.length) {
                JOptionPane.showMessageDialog(null, "Task limit reached.");
                return;
            }

            String taskName = JOptionPane.showInputDialog("Enter task name:");
            String taskDescription = JOptionPane.showInputDialog("Enter task description (max 50 characters):");

            if (taskDescription.length() > 50) {
                JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters.");
                t--; // count down to repeat the current task input
                continue; // CounterOfTasks and adding this task but also skipping incrementing 
            }

            String developerFirstName = JOptionPane.showInputDialog("Enter developer's first name:");
            String developerLastName = JOptionPane.showInputDialog("Enter developer's last name:");
            int taskDurationInput = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration in hours:"));

            String generatedTaskID = generateTaskID(taskName, developerLastName);

            String[] statusOptions = {"To Do", "Done", "Doing"};
            int statusChoice = JOptionPane.showOptionDialog(null, "Select task status:", "Task Status",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);

            String taskStatusInput = statusOptions[statusChoice];

            // this is to store my data into arrays
            developer[CounterOfTasks] = developerFirstName + " " + developerLastName;
            taskNames[CounterOfTasks] = taskName;
            taskID[CounterOfTasks] = generatedTaskID.toUpperCase();
            taskDuration[CounterOfTasks] = taskDurationInput;
            taskStatus[CounterOfTasks] = taskStatusInput;

            JOptionPane.showMessageDialog(null, "Task Status: " + taskStatusInput +
                    "\nDeveloper: " + developerFirstName + " " + developerLastName +
                    "\nTask Number: " + CounterOfTasks +
                    "\nTask Name: " + taskName +
                    "\nTask Description: " + taskDescription +
                    "\nTask ID: " + generatedTaskID.toUpperCase() +
                    "\nTask Duration: " + taskDurationInput + " hours");

            theTotalHours += taskDurationInput;
            CounterOfTasks++;
        }
    }

    public static String generateTaskID(String taskName, String developerLastName) {
        return taskName.substring(0, 2).toUpperCase() + ":" + CounterOfTasks + ":" + developerLastName.substring(Math.max(0, developerLastName.length() - 3)).toUpperCase();
    }

    public static void displayAllTasks() {
        StringBuilder report = new StringBuilder("All Tasks Report:\n");

        for (int i = 0; i < CounterOfTasks; i++) {
            report.append("\nTask ").append(i + 1).append(":\n");
            report.append("Task Name: ").append(taskNames[i]).append("\n");
            report.append("Developer: ").append(developer[i]).append("\n");
            report.append("Task ID: ").append(taskID[i]).append("\n");
            report.append("Task Duration: ").append(taskDuration[i]).append(" hours\n");
            report.append("Task Status: ").append(taskStatus[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, report.toString());
    }

    public static void displayTasksWithStatus(String status) {
        StringBuilder report = new StringBuilder("Tasks with Status '" + status + "':\n");

        for (int i = 0; i < CounterOfTasks; i++) {
            if (taskStatus[i].equalsIgnoreCase(status)) {
                report.append("\nTask ").append(i + 1).append(":\n");
                report.append("Task Name: ").append(taskNames[i]).append("\n");
                report.append("Developer: ").append(developer[i]).append("\n");
                report.append("Task Duration: ").append(taskDuration[i]).append(" hours\n");
            }
        }

        JOptionPane.showMessageDialog(null, report.toString());
    }
public static void displayTaskWithLongestDuration() {
        int maxDuration = 0;
        int maxIndex = -1;

        for (int i = 0; i < CounterOfTasks; i++) {
            if (taskDuration[i] > maxDuration) {
                maxDuration = taskDuration[i];
                maxIndex = i;
            }
        }

        if (maxIndex != -1) {
            JOptionPane.showMessageDialog(null, "Task with Longest Duration:\n" +
                    "Task Name: " + taskNames[maxIndex] +
                    "\nDeveloper: " + developer[maxIndex] +
                    "\nTask Duration: " + taskDuration[maxIndex] + " hours");
        } else {
            JOptionPane.showMessageDialog(null, "No tasks found.");
        }
    }

    public static void searchTaskByName() {
        String searchName = JOptionPane.showInputDialog("Enter task name to search:");

        for (int i = 0; i < CounterOfTasks; i++) {
            if (taskNames[i].equalsIgnoreCase(searchName)) {
                JOptionPane.showMessageDialog(null, "Task Found:\n" +
                        "Task Name: " + taskNames[i] +
                        "\nDeveloper: " + developer[i] +
                        "\nTask Status: " + taskStatus[i]);
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public static void searchTaskByDeveloper() {
        String searchDeveloper = JOptionPane.showInputDialog("Enter developer's name to search:");

        for (int i = 0; i < CounterOfTasks; i++) {
            if (developer[i].equalsIgnoreCase(searchDeveloper)) {
                JOptionPane.showMessageDialog(null, "Task Found:\n" +
                        "Task Name: " + taskNames[i] +
                        "\nTask Status: " + taskStatus[i]);
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public static void deleteTaskByName() {
        String deleteName = JOptionPane.showInputDialog("Enter task name to delete:");

        for (int i = 0; i < CounterOfTasks; i++) {
            if (taskNames[i].equalsIgnoreCase(deleteName)) {
                // Shifting elements into arrays so that it deletes the those task
                for (int j = i; j < CounterOfTasks - 1; j++) {
                    developer[j] = developer[j + 1];
                    taskNames[j] = taskNames[j + 1];
                    taskID[j] = taskID[j + 1];
                    taskDuration[j] = taskDuration[j + 1];
                    taskStatus[j] = taskStatus[j + 1];
                }

                // Clearing the last last element
                developer[CounterOfTasks - 1] = null;
                taskNames[CounterOfTasks - 1] = null;
                taskID[CounterOfTasks - 1] = null;
                taskDuration[CounterOfTasks - 1] = 0;
                taskStatus[CounterOfTasks - 1] = null;

                CounterOfTasks--;

                JOptionPane.showMessageDialog(null, "Task '" + deleteName + "' deleted successfully.");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public static boolean checkUserName(String username) {
        return username.length() == 5 && username.contains("_");
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 && !password.equals(password.toLowerCase()) && password.matches(".*\\d.*") && !password.matches("[A-Za-z0-9 ]*");
    }
}