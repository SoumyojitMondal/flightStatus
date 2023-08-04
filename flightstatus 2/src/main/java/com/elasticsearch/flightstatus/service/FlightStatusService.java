package com.elasticsearch.flightstatus.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elasticsearch.flightstatus.entity.FlightEvent;
import com.elasticsearch.flightstatus.repository.FlightEventRepo;

@Service
public class FlightStatusService {

    @Autowired
    private FlightEventRepo flightEventRepo;

    public Iterable<FlightEvent> getAllFlightEvents() {
        return flightEventRepo.findAll();
    }

    public FlightEvent createFlightEvents(FlightEvent flightEvent) {
        UUID uuid = UUID.randomUUID();
        flightEvent.setId(uuid);
        return flightEventRepo.save(flightEvent);
    }
    public List<FlightEvent> flightStatus(String flightNo, String flightDate) {
        return flightEventRepo.findFlightStaus(flightNo, flightDate);
    }
}
