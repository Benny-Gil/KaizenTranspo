package com.example.kaizentranspo.classees;

/**
 * User class represents a user
 * It contains username, password, tickets booked
 */
public class User {
    private String username;
    private String password;
    private Ticket[] ticketsBooked;

    /**
     * Constructor for User class
     * @param username username
     * @param password password
     * @param ticketsBooked tickets booked
     */
    public User(String username, String password, Ticket[] ticketsBooked) {
        this.username = username;
        this.password = password;
        this.ticketsBooked = ticketsBooked;
    }

    /**
     * Getter for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for tickets booked
     * @return tickets booked
     */
    public Ticket[] getTicketsBooked() {
        return ticketsBooked;
    }
}
