package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class AirportService {

    AirportRepository theAirportRepository = new AirportRepository();

    public void addAirport(Airport theAirport){
        theAirportRepository.addAirport(theAirport);
    }

    public String getLargestAirportName(){
        return theAirportRepository.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double time = theAirportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);

        return time == Double.MAX_VALUE ? -1 : time;
    }

    public int getNumberOfPeopleOn(Date thedate, String theAirportName){
        return theAirportRepository.getNumberOfPeopleOn(thedate,theAirportName);
    }

    public int calculateFlightFare(Integer flightId){

        int size = theAirportRepository.getPassengerCountByFlight(flightId);

        return 3000 + size * 50;
    }

    public String bookATicket(Integer theFlightId, Integer thePassengerId){
        return theAirportRepository.bookATicket(theFlightId,thePassengerId);
    }

    public String cancelATicket(Integer theFlightId, Integer thePassengerId){
        return theAirportRepository.cancelATicket(theFlightId, thePassengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer thePassengerId){
        return theAirportRepository.countOfBookingsDoneByPassengerAllCombined(thePassengerId);
    }

    public void addFlight(Flight theFlight){
        theAirportRepository.addFlight(theFlight);
    }

    public String getAirportNameFromFlightId(Integer theFlightId){
        return theAirportRepository.getAirportNameFromFlightId(theFlightId);
    }

    public int calculateRevenueOfAFlight(Integer theFlightId){
        int size = theAirportRepository.getPassengerCountByFlight(theFlightId);
        int revenue = 0;

        for(int i = 0; i < size; i++) revenue += 3000 + i * 50;

        return revenue;
    }

    public void addPassenger(Passenger thePassenger){
        theAirportRepository.addPassenger(thePassenger);
    }
}
