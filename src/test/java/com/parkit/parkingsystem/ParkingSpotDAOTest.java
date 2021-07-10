package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingSpotDAOTest {

        private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
        private static ParkingSpotDAO parkingSpotDAO;
        private static Ticket ticket;
        private static DataBasePrepareService dataBasePrepareService;
        private FareCalculatorService fcs = new FareCalculatorService();

        @BeforeAll
        private static void setUp() {
            parkingSpotDAO = new ParkingSpotDAO();
            parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
            ticket = new Ticket();
            FareCalculatorService fcs = new FareCalculatorService();
        }

        @Test
        public void getNextAvailableSlotTest() {
            ParkingType parkingType = ParkingType.CAR;
            int libre = parkingSpotDAO.getNextAvailableSlot(parkingType);
            assertEquals(2, libre);
        }

        @Test
        public void updateParkingTest() {
            ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
            boolean prise = parkingSpotDAO.updateParking(parkingSpot);
            assertTrue(prise);
        }
}
