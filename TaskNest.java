package main;

import model.Client;
import model.FreeLancer;
import service.Task;
import exception.DuplicateApplication;
import java.util.Scanner;

public class TaskNest {
    static Task[] tasks = new Task[100];
    static int taskCount = 0;

    static Client[] clients = new Client[100];
    static int clientCount = 0;

    static int taskIdCount = 1;
    static int freelancerIdCount = 1;
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("=========TaskNest========== ");
            System.out.println("1.Client");
            System.out.println("2.Freelancer");
            System.out.println("3.Exit");
            System.out.println("Enter Your Choice:");
            choice = s.nextInt();
            s.nextLine();
            switch (choice) {
                case 1:
                    clientMenu();
                    break;
                case 2:
                    freelancerMenu();
                    break;
                case 3:
                    System.out.println("Thank You For using TaskNest!");
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 3);
    }

    static void clientMenu() {
        System.out.println("Enter Client ID:");
        int clientId = s.nextInt();
        s.nextLine();
        System.out.println("Enter Client Name:");
        String clientName = s.nextLine();
        Client client = new Client(clientId, clientName, clientName + "@mail.com");
        if (clientCount >= clients.length) {
            System.out.println("Client limit reached.");
            return;
        }
        clients[clientCount] = client;
        clientCount++;
        client.displayDetails();
        int choice;
        do {
            System.out.println("========Client Menu========");
            System.out.println("1.Create Task");
            System.out.println("2.View Task");
            System.out.println("3.Update Task");
            System.out.println("4.View Applicants");
            System.out.println("5.Assign Freelancer");
            System.out.println("6.Sort By Budget");
            System.out.println("7.Back");
            System.out.println("Enter Your Choice");
            choice = s.nextInt();
            s.nextLine();
            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    viewTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    viewApplicants();
                    break;
                case 5:
                    assignFreelancer();
                    break;
                case 6:
                    sortTasksByBudget();
                    System.out.println("Tasks sorted by budget.");
                    viewTask();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 7);
    }

    static void freelancerMenu() {
        System.out.println("Enter Freelancer Name : ");
        String freelancerName = s.nextLine();
        int choice;
        do {
            System.out.println("======Freelancer Menu=======");
            System.out.println("1.View Task");
            System.out.println("2.Search Task");
            System.out.println("3.Apply for Task");
            System.out.println("4.View Applied Task");
            System.out.println("5.Back");
            System.out.println("Enter Your Choice : ");
            choice = s.nextInt();
            s.nextLine();
            switch (choice) {
                case 1:
                    viewTask();
                    break;
                case 2:
                    searchTask();
                    break;
                case 3:
                    applyForTask(freelancerName);
                    break;
                case 4:
                    viewAppliedTask(freelancerName);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } while (choice != 5);
    }

    static void createTask() {
        System.out.println("Enter Task Title : ");
        String title = s.nextLine();
        System.out.println("Enter Task Description : ");
        String description = s.nextLine();
        System.out.println("Enter Budget : ");
        double budget = s.nextDouble();
        s.nextLine();
        tasks[taskCount++] = new Task(taskIdCount++, title, description, budget);
        System.out.println("Task created successfully");
    }

    static void viewTask() {
        if (taskCount == 0) {
            System.out.println("No task Available.");
            return;
        }
        for (int i = 0; i < taskCount; i++) {
            tasks[i].displayTask();
        }
    }

    static Task findTaskById(int id) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].taskId == id) {
                return tasks[i];
            }
        }
        return null;
    }

    static void updateTask() {
        System.out.println("Enter Task ID to Update : ");
        int id = s.nextInt();
        s.nextLine();
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("Task not Found");
            return;
        }
        try {
            if (task.getStatus().equalsIgnoreCase("Closed")) {
                throw new Exception("Closed Task cannot be edited.");
            }
            System.out.println("Enter new Title : ");
            task.title = s.nextLine();
            System.out.println("Enter new Description : ");
            task.description = s.nextLine();
            System.out.println("Enter new Budget : ");
            task.budget = s.nextDouble();
            s.nextLine();
            System.out.println("Task Updated Successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void searchTask() {
        System.out.println("Enter Keyword to Search : ");
        String keyword = s.nextLine();
        boolean found = false;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].title.toLowerCase().contains(keyword.toLowerCase())) {
                tasks[i].displayTask();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No Matching Task Found.");
        }
    }

    static void applyForTask(String freelancerName) {
        System.out.println("Enter Task ID : ");
        int id = s.nextInt();
        s.nextLine();
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        try {
            if (!task.getStatus().equalsIgnoreCase("Open")) {
                throw new Exception("Cannot apply. Task is closed.");
            }
            if (task.applicantCount >= task.getApplicants().length) {
                throw new Exception("Maximum applicants reached.");
            }
            for (int i = 0; i < task.applicantCount; i++) {
                if (task.getApplicants()[i].getName().equalsIgnoreCase(freelancerName)) {
                    throw new DuplicateApplication("You have already applied for this Task.");
                }
            }
            System.out.println("Enter Skills : ");
            String skills = s.nextLine();
            FreeLancer freelancer =
                    new FreeLancer(freelancerIdCount++, freelancerName, freelancerName + "@gmail.com", "I have experience in " + skills);
            freelancer.setSkills(skills);
            task.getApplicants()[task.applicantCount] = freelancer;
            task.applicantCount++;
            System.out.println("Applied successfully.");
        } catch (DuplicateApplication e){
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void sortTasksByBudget() {
        for (int i = 0; i < taskCount - 1; i++) {
            for (int j = 0; j < taskCount - i - 1; j++) {
                if (tasks[j].budget > tasks[j + 1].budget) {
                    Task temp = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = temp;
                }
            }
        }
    }

    static void assignFreelancer() {
        System.out.println("Enter Task ID : ");
        int id = s.nextInt();
        s.nextLine();
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        try {
            if (task.applicantCount == 0) {
                throw new Exception("Cannot assign. No applicants available.");
            }
            if (task.getAssignedFreelancer() != null) {
                throw new Exception("Freelancer already assigned.");
            }
            System.out.println("Applicants:");
            for (int i = 0; i < task.applicantCount; i++) {
                System.out.println(task.getApplicants()[i].getName());
            }
            System.out.println("Enter Freelancer Name to Assign : ");
            String freelancerName = s.nextLine();
            FreeLancer selectedFreelancer = null;
            for (int i = 0; i < task.applicantCount; i++) {
                if (task.getApplicants()[i].getName()
                        .equalsIgnoreCase(freelancerName)) {
                    selectedFreelancer = task.getApplicants()[i];
                    break;
                }
            }
            if (selectedFreelancer == null) {
                throw new Exception("This freelancer has not applied.");
            }
            task.setAssignedFreelancer(selectedFreelancer);
            task.setStatus("Closed");
            System.out.println("Freelancer assigned successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void viewApplicants() {
        System.out.println("Enter Task ID : ");
        int id = s.nextInt();
        s.nextLine();
        Task task = findTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        if (task.applicantCount == 0) {
            System.out.println("No applicants available.");
            return;
        }
        System.out.println("Applicants for " + task.title + ":");
        for (int i = 0; i < task.applicantCount; i++) {
            System.out.println(task.getApplicants()[i]);
        }
    }

    static void viewAppliedTask(String freelancerName) {
        boolean found = false;
        for (int i = 0; i < taskCount; i++) {
            Task task = tasks[i];
            for (int j = 0; j < task.applicantCount; j++) {
                if (!task.getApplicants()[j].getName().equalsIgnoreCase(freelancerName)) {
                    continue;
                }
                task.displayTask();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No applied tasks found.");
        }
    }
}