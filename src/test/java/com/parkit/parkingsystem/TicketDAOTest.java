package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketDAOTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static Ticket ticket;
    private final FareCalculatorService fcs = new FareCalculatorService();

    @BeforeAll
   private static void setUp() {
       ticketDAO = new TicketDAO();
       ticketDAO.setDataBaseConfig(dataBaseTestConfig);
       ticket = new Ticket();
   }


    @Test
    public void getTicketTest() {
        ticket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(ticket);
    }

    @Test
    public void checkRegularTest() {
       getTicketTest();
       double regular = ticketDAO.checkRegular(ticket, dataBaseTestConfig);
       assertEquals(1, (int)regular);
    }

    @Test
    public void saveTicketTest() {
       getTicketTest();
       boolean reussite = ticketDAO.saveTicket(ticket);
       assertTrue(reussite);
    }

    @Test
    public void updateTicketTest() {
       getTicketTest();
        LocalDateTime outTime = LocalDateTime.now();
        ticket.setOutTime(outTime);
        double regular = ticketDAO.checkRegular(ticket, dataBaseTestConfig);
        fcs.calculateFare(ticket, regular);
        boolean reussite = ticketDAO.updateTicket(ticket);
        assertTrue(reussite);
    }
    @Test
    public void updateTestTicketTest() {
        getTicketTest();
        LocalDateTime outTime = LocalDateTime.now();
        ticket.setOutTime(outTime);
        double regular = ticketDAO.checkRegular(ticket, dataBaseTestConfig);
        fcs.calculateFare(ticket, regular);
        boolean reussite = ticketDAO.updateTestTicket(ticket);
        assertTrue(reussite);
    }
}
