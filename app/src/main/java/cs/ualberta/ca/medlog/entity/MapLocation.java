/*
 * Class file for the MapLocation entity
 *
 * Authors: Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * Contact: roskewic@ualberta.ca, chomyc1@ualberta.ca, ttamre@ualberta.ca
 * Created: October 30, 2018
 */

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

    public void setLatutude(double newLat) {
        latitude = newLat;
    }

    public void setLongitude(double newLon) {
        longitude = newLon;
    }

    public boolean equals(Object obj){
        if(obj instanceof MapLocation){
            MapLocation ml = (MapLocation) obj;
            return longitude == ml.longitude && latitude == ml.latitude;
        }
        return false;
    }
}
