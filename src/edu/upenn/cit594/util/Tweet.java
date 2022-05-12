package edu.upenn.cit594.util;

public class Tweet {
//    private String coordinates;
    private final String tweet;
    private double latitude;
    private double longitude;
    public Tweet(double latitude, double longitude, String tweet) {
        this.latitude  = latitude;
        this.longitude = longitude;
        this.tweet = tweet;
    }
    public String getTweet() {
        return this.tweet;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return  this.longitude;
    }
}
