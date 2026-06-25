package model;

import service.TaskDisplay;

public class FreeLancer extends User {
    private String skills;

    public FreeLancer(int userId, String name, String email, String skills) {
        super(userId, name, email);
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public void displayDetails() {
        System.out.println("Freelancer Id  : " + getUserId());
        System.out.println("Name           : " + getName());
        System.out.println("Email          : " + getEmail());
        System.out.println("Skills         : " + getSkills());
    }

    @Override
    public String toString() {
        return "ID: " + getUserId() + ", Name: " + getName() + ", Email: " + getEmail() + ", Skills: " + getSkills();
    }
}