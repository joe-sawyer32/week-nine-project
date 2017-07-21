package com.theironyard;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        VehicleInfo newVehicleInfo = new VehicleInfo();
        getVehicleInfoFromUser(newVehicleInfo);
    }

    public static VehicleInfo getVehicleInfoFromUser(VehicleInfo vi) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLEASE ENTER VEHICLE INFO");
        System.out.println("--------------------------------------\n");
        promptUserForVin(scanner, "VIN Number:");
        promptUserForInfo(scanner, "Mileage:");
        promptUserForInfo(scanner, "Gasoline Used (gallons):");
        promptUserForInfo(scanner, "Mileage at Last Oil Change:");
        promptUserForInfo(scanner, "Engine Size (liters):");
        return vi;
    }

    public static int promptUserForVin(Scanner sc, String message) {
        int userInput = (int) promptUserForInfo(sc, message);
        return userInput;
    }

    public static double promptUserForInfo(Scanner sc, String message) {
        double userInput = 0.0;
        return userInput;
    }
}
