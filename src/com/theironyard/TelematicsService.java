package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private static VehicleInfoAverages averages = new VehicleInfoAverages();

    // CONSTRUCTORS

    // METHODS
    public static void report(VehicleInfo vehicleInfo) {
        System.out.print("Creating record for vehicle.");
        File filepath = new File(RECORDS_DIRECTORY + vehicleInfo.getVin() + ".json");
        mapper = new ObjectMapper();
        convertToJsonFile(vehicleInfo, filepath, mapper);
        File records = new File(RECORDS_DIRECTORY);
        updateDashboard(averages, records, mapper);
    }

    public static void updateDashboard(VehicleInfoAverages averages, File records, ObjectMapper mapper) {
        List<VehicleInfo> vehicleRecords = grabVehicleRecords(averages, records);
        for (VehicleInfo vi : vehicleRecords) {
            System.out.println("VEHICLE:");
            System.out.println(vi.getVin());
            System.out.println(vi.getMiles());
            System.out.println(vi.getGasGallonsConsumed());
            System.out.println(vi.getMilesAtLastOilChange());
            System.out.println(vi.getEngineLiters());
        }
    }

    private static List<VehicleInfo> grabVehicleRecords(VehicleInfoAverages averages, File records) {
        System.out.print("Grabbing all vehicle records.");
        List<VehicleInfo> vehicleRecords = new ArrayList<>();
        for (File f : records.listFiles()) {
            if (f.getName().endsWith(".json")) {
                System.out.print(".");
                VehicleInfo vehicleRecord = convertJsonFileToObject(f);
                averages.setVehicleCount(averages.getVehicleCount() + 1);
                updateAveragesObject(averages, vehicleRecord);
                vehicleRecords.add(vehicleRecord);
            }
        }
        return vehicleRecords;
    }

    public static VehicleInfo convertJsonFileToObject(File json) {
        VehicleInfo vi = new VehicleInfo();
        try {
            vi = mapper.readValue(json, VehicleInfo.class);
        } catch (JsonProcessingException ex) {
            System.out.println("\nUnable to convert from JSON");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("\nUnsuccessful read from file");
            ex.printStackTrace();
        }
        return vi;
    }

    public static void updateAveragesObject(VehicleInfoAverages averages, VehicleInfo vi) {
        averages.setMiles((vi.getMiles() + averages.getMiles()) / averages.getVehicleCount());
        averages.setGasGallonsConsumed((vi.getGasGallonsConsumed() + averages.getGasGallonsConsumed()) /
                averages.getVehicleCount());
        averages.setMilesAtLastOilChange((vi.getMilesAtLastOilChange() + averages.getMilesAtLastOilChange()) /
                averages.getVehicleCount());
        averages.setEngineLiters((vi.getEngineLiters() + averages.getEngineLiters()) / averages.getVehicleCount());
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
