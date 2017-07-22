package com.theironyard;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        VehicleInfo newVehicleInfo = new VehicleInfo();
        Scanner inputScanner = new Scanner(System.in);
        getVehicleInfoFromUser(newVehicleInfo, inputScanner);
    }

    public static VehicleInfo getVehicleInfoFromUser(VehicleInfo vi, Scanner sc) {
        System.out.println("PLEASE ENTER VEHICLE INFO");
        System.out.println("--------------------------------------");
        vi.setVin(promptUserForVin(sc, "VIN Number:"));
        vi.setMiles(promptUserForInfo(sc, "Mileage:"));
        vi.setGasGallonsConsumed(promptUserForInfo(sc, "Gasoline Used (gallons):"));
        vi.setMilesAtLastOilChange(promptUserForInfo(sc, "Mileage at Last Oil Change:"));
        vi.setEngineLiters(promptUserForInfo(sc, "Engine Size (liters):"));
        return vi;
    }

    public static int promptUserForVin(Scanner sc, String message) {
        int userInput = (int) promptUserForInfo(sc, message);
        return userInput;
    }

    public static double promptUserForInfo(Scanner sc, String message) {
        System.out.println(message);
        double userInput = sc.nextDouble();
        return userInput;
    }
}
