package cs.ualberta.ca.medlog.entity;


import java.io.Serializable;

/**
 * <h1>
 *     Map Location
 * </h1>
 *
 * <p>
 *     Description: <br>
 *         Class for holding a record map location. It contains the related longitude and latitude.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * @version 1.0
 * @see Photo
 */
public class MapLocation implements Serializable {
    private double latitude, longitude;

    private static final double SEARCHDISTMAP = 0.1; // 10 KM
    /**
     * Creates the map location with the provided longitude and latitude. Makes sure to check that
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
     * Retrieves the longitude of the map location.
     * @return The longitude in double form
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets a new latitude for the map location.
     * @param newLat The new latitude
     */
    public void setLatutude(double newLat) {
        latitude = newLat;
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
     * @param ml The map location to compare against.
     * @return If its less than or equal to the max dist.
     */
    public boolean isNear(MapLocation ml){
        double dist = Math.sqrt(Math.pow(latitude - ml.latitude, 2) + Math.pow(longitude - ml.longitude, 2));
        return dist <= SEARCHDISTMAP;
    }
}
