package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Joe on 7/21/17.
 */
public class TelematicsService {
    // FIELDS
    private static File file;
    private static ObjectMapper mapper;
    private final static String RECORDS_DIRECTORY = "./vehicle-records/";

    // CONSTRUCTORS

    // METHODS
    public static void report(VehicleInfo vehicleInfo) {
        System.out.print("Creating record for vehicle.");
        file = new File(RECORDS_DIRECTORY + vehicleInfo.getVin() + ".json");
        mapper = new ObjectMapper();
        convertToJsonFile(vehicleInfo, file, mapper);

    }

    public static void convertToJsonFile(VehicleInfo vi, File f, ObjectMapper om) {
        try (FileWriter fileWriter = new FileWriter(f)) {
            System.out.print(".");
            String json = om.writeValueAsString(vi);
            System.out.print(".");
            fileWriter.write(json);
            System.out.print(".\n");
            fileWriter.close();
            System.out.println("Vehicle record added");
        } catch (JsonProcessingException ex) {
            System.out.println("\nUnable to convert to JSON");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("\nUnsuccessful write to file");
            ex.printStackTrace();
        }
    }
}
