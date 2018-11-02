package cs.ualberta.ca.medlog.entity;

public class MapLocation{
    private double latitude, longitude;

    public MapLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
