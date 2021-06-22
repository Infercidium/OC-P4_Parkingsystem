package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        double duration = (double)(Duration.between(ticket.getInTime(), ticket.getOutTime()).getSeconds())/3600;
        /*for(int i = ticket.getOutTime().getYear(); i > ticket.getInTime().getYear(); --i) { // Boucle Année
            LocalDateTime year = LocalDateTime.of(i, 12, 31, 0, 0);
            duration += year.getDayOfYear(); // Ajoute year qui contient le nombre de jours max d'une année (365 ou 366)
        }
        duration += (ticket.getOutTime().getDayOfYear() - ticket.getInTime().getDayOfYear());
        // Ajout du nombre de jour stationné (possiblement négatif si stationné de decembre à janvier mais se soustrait au jour max déjà ajouté)
        duration *= 24; // Jour converti en heure
        duration += (ticket.getOutTime().getHour() - ticket.getInTime().getHour());
        // ajout des heures stationnées (possiblement négatif si stationnné de 9h jour 1 à 7h jour 2 mais se soustrait au jour converti en heure)
        duration += ((double)(ticket.getOutTime().getMinute() - ticket.getInTime().getMinute())/60);
        // Ajout des minutes en virgule (si négatif se soustrait au heure déjà présente)*/

        if(duration >= Fare.FREE_TIME){
            duration -= Fare.FREE_TIME;
        }else{
            duration = 0;
        }
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                System.out.println(ticket.getPrice());
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}