package com.theironyard;

/**
 * Created by Joe on 7/21/17.
 */

// POJO
public class VehicleInfo {
    // FIELDS
    private int vin;
    private double miles;
    private double gasGallonsConsumed;
    private double milesAtLastOilChange;
    private double engineLiters;

    // CONSTRUCTORS
    // empty
    public VehicleInfo() {}

    // METHODS
    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public double getGasGallonsConsumed() {
        return gasGallonsConsumed;
    }

    public void setGasGallonsConsumed(double gasGallonsConsumed) {
        this.gasGallonsConsumed = gasGallonsConsumed;
    }

    public double getMilesAtLastOilChange() {
        return milesAtLastOilChange;
    }

    public void setMilesAtLastOilChange(double milesAtLastOilChange) {
        this.milesAtLastOilChange = milesAtLastOilChange;
    }

    public double getEngineLiters() {
        return engineLiters;
    }

    public void setEngineLiters(double engineLiters) {
        this.engineLiters = engineLiters;
    }

    public double calculateMilesPerGallon() {
        return this.miles / this.gasGallonsConsumed;
    }
}
