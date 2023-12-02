package com.example.kaizentranspo.classees;

/**
 * Bus class represents a bus
 * It contains bus number, destination, and seats
 */
public class Bus {
    private String busNumber;
    private String destination;
    private static final int TOTAL_SEATS = 49;
    private boolean[] seats;


    /**
     * Constructor for BusTicketing.Bus class
     *
     * @param busNumber   bus number
     * @param destination destination
     */
    public Bus(String busNumber, String destination) {
        this.busNumber = busNumber;
        this.destination = destination;

        seats = new boolean[TOTAL_SEATS];
        for (int i = 0; i < TOTAL_SEATS; i++) {
            seats[i] = true;
        }

    }

    /**
     * Getter for bus number
     *
     * @return bus number
     */
    public String getBusNumber() {
        return busNumber;
    }

    /**
     * Getter for destination
     *
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter for status
     *
     * @return total number of seats
     */
    public int getAvailableSeats() {
        int count = 0;
        for (boolean seat : seats) {
            if (seat) {
                count++;
            }
        }
        return count;
    }

    public int getBookedSeats() {
        int count = 0;
        for (boolean seat : seats) {
            if (!seat) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if a seat with a given seat number is available
     *
     * @param seatNumber seat number
     * @return true if seat is available, false otherwise
     */
    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= TOTAL_SEATS) {
            return seats[seatNumber - 1];
        } else {
            // Invalid seat number
            return false;
        }
    }

    /**
     * Book a seat with a given seat number, It can only book a seat if it is available
     *
     * @param seatNumber seat number
     */
    public void bookSeat(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= TOTAL_SEATS && seats[seatNumber - 1] && isSeatAvailable(seatNumber)) {
            seats[seatNumber - 1] = false; // Mark seat as booked
        }
    }

    /**
     * Display all the seats and their status
     */
    public void displaySeatStatus() {
        //print 4 seats per row, and at the last line print 5 seats
        for (int i = 0; i < TOTAL_SEATS; i++) {
            if (i % 4 == 0) {
                System.out.println();
            }
            System.out.printf("%s\t", seats[i] ? " " : "x");
        }
        System.out.println();
    }

    /**
     * Cancel a seat with a given seat number, It can only cancel a seat if it is booked
     *
     * @param seatNumber seat number
     */
    public void cancelSeat(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= TOTAL_SEATS && !seats[seatNumber - 1] && !isSeatAvailable(seatNumber)) {
            seats[seatNumber - 1] = true; // Mark seat as booked
        }
    }
}
