package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;

/**
 * Calculate the price
 */
public class FareCalculatorService {

    private final TicketDAO ticketDAO = new TicketDAO();

    /**
     * Set a price using the date of entry, exit and whether the user is regulated
     * @param ticket Ticket containing vehicle entry and exit information
     */
    public void calculateFare(Ticket ticket) {
        if ( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            if (ticket.getOutTime() != null) {
                throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
            }else{
                throw new IllegalArgumentException("Out time provided is null");
            }
        }
        double duration = (double)(Duration.between(ticket.getInTime(), ticket.getOutTime()).getSeconds())/3600;

        if(duration >= Fare.FREE_TIME){
            duration -= Fare.FREE_TIME;
        }else{
            duration = 0;
        }
        //Endroit d'affichage de la récurence
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(Math.round(((duration * Fare.CAR_RATE_PER_HOUR)*ticketDAO.checkRegular(ticket))*100.0)/100.0);
                break;
            }
            case BIKE: {
                ticket.setPrice(Math.round(((duration * Fare.BIKE_RATE_PER_HOUR)*ticketDAO.checkRegular(ticket))*100.0)/100.0);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}