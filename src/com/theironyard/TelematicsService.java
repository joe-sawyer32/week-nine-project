package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static ObjectMapper mapper = new ObjectMapper();
    private final static String RECORDS_DIRECTORY = "./vehicle-records/";
    private final static String VIEW_DIRECTORY = "./resources/views/";
    private final static String DASHBOARD = VIEW_DIRECTORY + "dashboard.html";
    private final static String DASHBOARD_TEMPLATE = VIEW_DIRECTORY + "dashboard-template.html";
    private static VehicleInfoAverages averages = new VehicleInfoAverages();
    private static int templateReplace = 1;

    // CONSTRUCTORS

    // METHODS
    public static void report(VehicleInfo vehicleInfo) {
        System.out.print("Creating record for vehicle.");
        File filepath = new File(RECORDS_DIRECTORY + vehicleInfo.getVin() + ".json");
        convertToJsonFile(vehicleInfo, filepath, mapper);
        File records = new File(RECORDS_DIRECTORY);
        updateDashboard(records);
    }

    private static void updateDashboard(File records) {
        List<VehicleInfo> vehicleRecords = grabVehicleRecords(records);
        File template = new File(DASHBOARD_TEMPLATE);
        File dashboard = new File(DASHBOARD);
        try (Scanner scanner = new Scanner(template);
             FileWriter fileWriter = new FileWriter(dashboard)) {
            writeToDashboard(vehicleRecords, scanner, fileWriter);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file to read");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to write to file");
            e.printStackTrace();
        }
    }

    private static void writeToDashboard(List<VehicleInfo> records, Scanner scanner, FileWriter fileWriter) throws IOException {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("#")) {
                switch (templateReplace) {
                    case 1:
                        line = line.replace("#", Integer.toString(averages.getVehicleCount()));
                        break;
                    case 2:
                        line = makeVehicleAveragesLine(line);
                        break;
                    case 3:
                        line = makeVehicleHistoryLines(records, fileWriter, line);
                        break;
                    default:
                        System.out.println("Something went wrong...");
                }
                templateReplace++;
            }
            fileWriter.write(line);
            if (scanner.hasNextLine()) {
                fileWriter.write("\n");
            }
        }
    }

    private static String makeVehicleHistoryLines(List<VehicleInfo> vehicles, FileWriter fw, String line) throws IOException {
        line = line.replaceFirst("#", "%d");
        line = line.replace("#", "%.1f");
        for (int i = 0; i < vehicles.size(); i++) {
            VehicleInfo vi = vehicles.get(i);
            String formattedLine = String.format(line, vi.getVin(), vi.getMiles(),
                    vi.getGasGallonsConsumed(), vi.getMilesAtLastOilChange(), vi.getEngineLiters());
            if (i < vehicles.size() - 1) {
                fw.write(formattedLine);
                fw.write("\n\t</tr>\n\t<tr>\n");
            } else {
                line = formattedLine;
            }
        }
        return line;
    }

    private static String makeVehicleAveragesLine(String line) {
        line = line.replace("#", "%.1f");
        line = String.format(line, averages.getMiles(), averages.getGasGallonsConsumed(),
                averages.getMilesAtLastOilChange(), averages.getEngineLiters());
        return line;
    }

    private static List<VehicleInfo> grabVehicleRecords(File records) {
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

    private static VehicleInfo convertJsonFileToObject(File json) {
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

    private static void updateAveragesObject(VehicleInfoAverages averages, VehicleInfo vi) {
        averages.setMiles((vi.getMiles() + averages.getMiles()) / averages.getVehicleCount());
        averages.setGasGallonsConsumed((vi.getGasGallonsConsumed() + averages.getGasGallonsConsumed()) /
                averages.getVehicleCount());
        averages.setMilesAtLastOilChange((vi.getMilesAtLastOilChange() + averages.getMilesAtLastOilChange()) /
                averages.getVehicleCount());
        averages.setEngineLiters((vi.getEngineLiters() + averages.getEngineLiters()) / averages.getVehicleCount());
        System.out.print(".");
    }

    private static void convertToJsonFile(VehicleInfo vi, File f, ObjectMapper mapper) {
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
