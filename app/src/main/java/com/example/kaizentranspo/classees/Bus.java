package com.example.kaizentranspo.classees;

/**
 * Bus class represents a bus
 * It contains bus number, destination, status, seats occupied, and seats available
 */
public class Bus {
    private String busNumber;
    private String destination;
    private String status;
    private int seatsOccupied;
    private int seatsAvailable;

    /**
     * Constructor for Bus class
     * @param busNumber bus number
     * @param destination destination
     * @param status status
     * @param seatsOccupied seats occupied
     * @param seatsAvailable seats available
     */
    public Bus(String busNumber, String destination, String status, int seatsOccupied, int seatsAvailable) {
        this.busNumber = busNumber;
        this.destination = destination;
        this.status = status;
        this.seatsOccupied = seatsOccupied;
        this.seatsAvailable = seatsAvailable;
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
     * Getter for status
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter for seats occupied
     * @return seats occupied
     */
    public int getSeatsOccupied() {
        return seatsOccupied;
    }

    /**
     * Getter for seats available
     * @return seats available
     */
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
}
