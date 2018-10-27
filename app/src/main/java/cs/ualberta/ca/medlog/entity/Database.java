package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;

public class Database {

    public static void SavePatient(Patient patient){

    }

    public static Patient LoadPatient(){
        return null;
    }

    public static void SaveProvider(CareProvider provider){

    }

    public static CareProvider LoadProvider(){
        return null;
    }

    public static ArrayList<Problem> SearchPatient(Patient patient, String keywords, MapLocation map, BodyLocation bod){
        return null;
    }

    public static ArrayList<Problem> SearchProvider(CareProvider provider, String keyword, MapLocation map, BodyLocation body){
        return null;
    }
}
