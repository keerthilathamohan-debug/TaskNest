package service;

import model.FreeLancer;

public class Task implements TaskDisplay {

    public int taskId;
    public String title;
    public String description;
    public double budget;

    private String status;
    private FreeLancer assignedFreelancer;

    private final FreeLancer[] applicants = new FreeLancer[100];
    public int applicantCount = 0;

    public Task(int taskId, String title, String description, double budget) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.status = "Open";
        this.assignedFreelancer = null;
    }

    @Override
    public void displayTask() {
        System.out.println("Task ID : " + taskId);
        System.out.println("Title : " + title);
        System.out.println("Description : " + description);
        System.out.println("Budget : " + budget);
        System.out.println("Status : " + status);

        if (assignedFreelancer != null) {
            System.out.println("Assigned Freelancer : " +
                    assignedFreelancer.getName());
        } else {
            System.out.println("Assigned Freelancer : Not Assigned");
        }

        System.out.println("-----------------------------------");
    }

    public FreeLancer[] getApplicants() {
        return applicants;
    }

    public FreeLancer getAssignedFreelancer() {
        return assignedFreelancer;
    }

    public void setAssignedFreelancer(FreeLancer assignedFreelancer) {
        this.assignedFreelancer = assignedFreelancer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
