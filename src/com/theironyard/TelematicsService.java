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
    private final static String VIEW_DIRECTORY = "./resources/views/";
    private final static String DASHBOARD = VIEW_DIRECTORY + "dashboard.html";
    private final static String DASHBOARD_TEMPLATE = VIEW_DIRECTORY + "dashboard-template.html";
    private static VehicleInfo totals;
    private static int recordCount;

    // CONSTRUCTORS

    // METHODS
    public static void report(VehicleInfo vehicleInfo) {
        System.out.print("Creating record for vehicle.");
        File filepath = new File(RECORDS_DIRECTORY + vehicleInfo.getVin() + ".json");
        mapper = new ObjectMapper();
        convertToJsonFile(vehicleInfo, filepath, mapper);
        File records = new File(RECORDS_DIRECTORY);
        totals = new VehicleInfo();
        updateDashboard(totals, records);
    }

    public static void updateDashboard(VehicleInfo totals, File records) {
        System.out.print("Grabbing all vehicle records.");
        for (File f : records.listFiles()) {
            if (f.getName().endsWith(".json")) {
                System.out.print(".");
                recordCount++;
                VehicleInfo vehicleRecord = covertJsonFileToObject(totals, f);
                if (vehicleRecord != null) {
                    try (FileWriter fileWriter = new FileWriter(DASHBOARD)) {
                        fileWriter.write(vehicleRecord.getVin());
                    } catch (IOException ex) {
                        System.out.println("Unable to write to file");
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static VehicleInfo covertJsonFileToObject(VehicleInfo totals, File json) {
        VehicleInfo vi = new VehicleInfo();
            try {
                vi = mapper.readValue(json, VehicleInfo.class);
                updateTotalsObject(totals, vi);
            } catch (JsonProcessingException ex) {
                System.out.println("\nUnable to convert from JSON");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("\nUnsuccessful read from file");
                ex.printStackTrace();
            }
        return vi;
    }

    public static void updateTotalsObject(VehicleInfo totals, VehicleInfo vi) {
        totals.setMiles(vi.getMiles() + totals.getMiles());
        totals.setGasGallonsConsumed(vi.getGasGallonsConsumed() + totals.getGasGallonsConsumed());
        totals.setMilesAtLastOilChange(vi.getMilesAtLastOilChange() + totals.getMilesAtLastOilChange());
        totals.setEngineLiters(vi.getEngineLiters() + totals.getEngineLiters());
        System.out.print(".");
    }

    public static void convertToJsonFile(VehicleInfo vi, File f, ObjectMapper mapper) {
        try (FileWriter fileWriter = new FileWriter(f)) {
            System.out.print(".");
            String json = mapper.writeValueAsString(vi);
            System.out.print(".");
            fileWriter.write(json);
            System.out.print(".\n");
            System.out.printf("Vehicle record added for vin %s.\n", vi.getVin());
        } catch (JsonProcessingException ex) {
            System.out.println("\nUnable to convert to JSON");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("\nUnsuccessful write to file");
            ex.printStackTrace();
        }
    }
}
