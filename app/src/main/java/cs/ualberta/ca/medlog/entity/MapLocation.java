package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

/**
 * <p>
 *     Description: <br>
 *         Class representing a record's map location in the application. It contains the related
 *         longitude and latitude for the location as well as getters and setters for them.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 *
 * @author Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * @version 1.0
 */
public class MapLocation implements Serializable {
    private double latitude, longitude;

    private static final double SEARCH_DIST_MAP = 0.1; // APPROX 10 KM

    /**
     * Constructs the map location with the provided longitude and latitude. Makes sure to check that
     * the provided longitude and latitude are valid
     * @param latitude The latitude of the location in double form
     * @param longitude The longitude of the location in double form
     * @throws IllegalArgumentException If latitude or longitude are outside relevant bounds
     */
    public MapLocation(double latitude, double longitude){
        if(latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180){
            throw new IllegalArgumentException("Invalid longitude [-90,90] / latitude [-180,180]. Provided values are " + longitude + " longitude and " + latitude + " latitude");
        }

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Retrieves the latitude of the map location.
     * @return The latitude in double form
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets a new latitude for the map location.
     * @param newLat The new latitude
     */
    public void setLatitude(double newLat) {
        latitude = newLat;
    }

    /**
     * Retrieves the longitude of the map location.
     * @return The longitude in double form
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets a new longitude for the map location.
     * @param newLon The new longitude
     */
    public void setLongitude(double newLon) {
        longitude = newLon;
    }

    /**
     * Checks if two map locations are equal.
     * @param obj The other map location object
     * @return True if they are equal, false if not
     */
    public boolean equals(Object obj){
        if(obj instanceof MapLocation){
            MapLocation ml = (MapLocation) obj;
            return longitude == ml.longitude && latitude == ml.latitude;
        }
        return false;
    }

    /**
     * Returns if a map location is near to another location.
     * @param otherLocation The map location to compare against.
     * @return If its less than or equal to the max dist.
     */
    public boolean isNear(MapLocation otherLocation){
        double dist = Math.sqrt(Math.pow(latitude - otherLocation.latitude, 2) + Math.pow(longitude - otherLocation.longitude, 2));
        return dist <= SEARCH_DIST_MAP;
    }
}
