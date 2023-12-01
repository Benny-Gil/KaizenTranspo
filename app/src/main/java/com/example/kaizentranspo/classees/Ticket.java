package com.example.kaizentranspo.classees;

/**
 * Ticket class represents a bus ticket
 * It contains bus number, destination, time of departure, price, seat number, and ticket number
 */
public class Ticket {
    private String busNumber;
    private String destination;
    private String timeOfDeparture;
    private double price;
    private int seatNumber;
    private int ticketNumber;

    /**
     * Constructor for Ticket class
     * @param busNumber bus number
     * @param destination destination
     * @param timeOfDeparture time of departure
     * @param price price
     * @param seatNumber seat number
     * @param ticketNumber ticket number
     */
    public Ticket(String busNumber, String destination, String timeOfDeparture, double price, int seatNumber, int ticketNumber) {
        this.busNumber = busNumber;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.price = price;
        this.seatNumber = seatNumber;
        this.ticketNumber = ticketNumber;
    }

    /**
     * Getter for bus number
     * @return bus number
     */
    public String getBusNumber() {
        return busNumber;
    }

    /**
     * Getter for destination
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter for time of departure
     * @return time of departure
     */
    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }

    /**
     * Getter for price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for seat number
     * @return seat number
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Getter for ticket number
     * @return ticket number
     */
    public int getTicketNumber() {
        return ticketNumber;
    }

    /**
     * Setter for bus number
     * @param busNumber bus number
     */
    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    /**
     * Setter for destination
     * @param destination destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Setter for time of departure
     * @param timeOfDeparture time of departure
     */
    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }
}
