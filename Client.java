package model;

public class Client extends User{
    public Client(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public void displayDetails() {
        System.out.println("Client Id : " + getUserId());
        System.out.println("Name      : " + getName());
        System.out.println("Email     : " + getEmail());
    }

    @Override
    public String toString() {
        return "Client ID: " + getUserId() + ", Name: " + getName() + ", Email: " + getEmail();}
}