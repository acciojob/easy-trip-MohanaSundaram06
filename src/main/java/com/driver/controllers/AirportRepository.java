package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.*;

public class AirportRepository {

    private HashMap<String, Airport> airportHashMap = new HashMap<>();
    private HashMap<Integer, Passenger> passengerHashMap = new HashMap<>();
    private HashMap<Integer, Flight> flightHashMap = new HashMap<>();
    private HashMap<Integer, HashSet<Integer>> flightPassengerMap = new HashMap<>();

    public void addAirport(Airport theAirport){
        String name = theAirport.getAirportName();
        airportHashMap.put(name,theAirport);
    }

    public String getLargestAirportName(){
        String theAirport = "";
        int count = 0;
        ArrayList<String> list = new ArrayList<>(airportHashMap.keySet());

        for(String name : list){
            if( count < airportHashMap.get(name).getNoOfTerminals()){
                count = airportHashMap.get(name).getNoOfTerminals();
                theAirport = name;
            }
        }
        return theAirport;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double time = Double.MAX_VALUE;

        for(Flight theFlight : flightHashMap.values()){
            if(theFlight.getFromCity() == fromCity && theFlight.getToCity() == toCity){
                time = Math.min(time, theFlight.getDuration());
            }
        }
        return time;
    }

    public int getNumberOfPeopleOn(Date date,String theAirportName){
        int count = 0;
        if(!airportHashMap.containsKey(theAirportName)) return count;

        City theCity = airportHashMap.get(theAirportName).getCity();

        for(int flightId : flightPassengerMap.keySet()){
            Flight theFlight = flightHashMap.get(flightId);

            if(theFlight.getFlightDate().equals(date) && (theFlight.getFromCity().equals(theCity) || theFlight.getToCity().equals(theCity))){
                count += flightPassengerMap.get(flightId).size();
            }
        }
        return count;
    }

    public int getPassengerCountByFlight(int theFlightId){
        int size = 0;

        if(!flightPassengerMap.containsKey(theFlightId)) return size;

        return flightPassengerMap.get(theFlightId).size();
    }

    public String bookATicket(Integer theFlightId, Integer thePassengerId){

        HashSet<Integer> passengerList = flightPassengerMap.getOrDefault(theFlightId,new HashSet<>());

        if(passengerList.size() >= flightHashMap.get(theFlightId).getMaxCapacity()) return "FAILURE";

        if(passengerList.contains(thePassengerId)) return "FAILURE";

        passengerList.add(thePassengerId);

        flightPassengerMap.put(theFlightId,passengerList);

        return "SUCCESS";
    }

    public String cancelATicket(Integer theFlightId, Integer thePassengerId){

        if(!flightPassengerMap.containsKey(theFlightId) ) return "FAILURE";

        if(!flightPassengerMap.get(theFlightId).contains(thePassengerId)) return "FAILURE";

        flightPassengerMap.get(theFlightId).remove(thePassengerId);

        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer thePassengerId){
        int count = 0;

        for(HashSet<Integer> passengerList : flightPassengerMap.values()){
            if(passengerList.contains(thePassengerId)) count++;
        }
        return count;
    }


    public void addFlight(Flight theFlight){
        flightHashMap.put(theFlight.getFlightId(),theFlight);
    }

    public String getAirportNameFromFlightId(Integer theFlightId){

        if(!flightHashMap.containsKey(theFlightId)) return null;

        City theCity = flightHashMap.get(theFlightId).getFromCity();

        for(Airport theAirport : airportHashMap.values()){
            if(theAirport.getCity().equals(theCity)){
                return theAirport.getAirportName();
            }
        }
        return null;
    }

    public void addPassenger(Passenger thePassenger){
        passengerHashMap.put(thePassenger.getPassengerId(), thePassenger);
    }

}
