package edu.upenn.cit594.util;

public class StateData {
    private final String state;
    private final double latitude;
    private final double longitude;
    public StateData(String state, double latitude, double longitude)  {
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getState() {return state;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
}
