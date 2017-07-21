package com.theironyard;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Joe on 7/21/17.
 */
public class MainTest {
    @Test
    public void getVehicleInfoFromUserTest() throws Exception {

    }

    @Test
    public void promptUserForVinTest(String prompt) throws Exception {

    }

    @Test
    public void promptUserForInfoTest(String prompt) throws Exception {

        assertEquals("A double should be returned", 0.0, Main.promptUserForInfo(), 1E-9 );
    }
}