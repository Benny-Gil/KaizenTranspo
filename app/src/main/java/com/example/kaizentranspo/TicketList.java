package com.example.kaizentranspo;

public class TicketList {
    private String destination;
    private String time;
    private String busNumber;
    private String seatNumber;

    public TicketList(String destination, String time, String busNumber, String seatNumber) {
        this.destination = destination;
        this.time = time;
        this.busNumber = busNumber;
        this.seatNumber = seatNumber;

    }

    public TicketList() {
        this.destination = destination;
        this.time = time;
        this.busNumber = busNumber;
        this.seatNumber = seatNumber;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }


}
