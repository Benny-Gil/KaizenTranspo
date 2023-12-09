package com.example.kaizentranspo;

public class BusList {

    private String destination;
    private String time;
    private String busNumber;
    private String price;

    public BusList(String destination, String time, String busNumber, String price) {
        this.destination = destination;
        this.time = time;
        this.busNumber = busNumber;
        this.price = price;
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

    public String getPrice() {
        return price;
    }
}
