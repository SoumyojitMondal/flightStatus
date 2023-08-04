package com.elasticsearch.flightstatus.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.elasticsearch.flightstatus.entity.FlightEvent;
import com.elasticsearch.flightstatus.entity.SearchDTO;
import com.elasticsearch.flightstatus.service.FlightStatusService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("")
public class FlightStatusController {
    
    @Autowired(required = true)
    private FlightStatusService flightStatusService;
    @GetMapping("/all")
    Iterable<FlightEvent> findAll(){
        return flightStatusService.getAllFlightEvents();
    }
    @PostMapping("/setStatus")
    public FlightEvent insertNewFlightStatus(@Valid @RequestBody  FlightEvent flightEvent){
        log.info("Create flight event invoked with parameters -- flight no: {}, event name: {}, event time: {}", flightEvent.getFlightNo(), flightEvent.getEventName(), flightEvent.getEventTime());
        return flightStatusService.createFlightEvents(flightEvent);
    }
    @GetMapping("/getStatus")
    @ResponseBody
    public List<FlightEvent> getFlightStatus(@Valid @RequestBody SearchDTO searchDTO) {
        log.info("Flight status search invoked with parameters -- flight no: {}, flight date: {}", searchDTO.getFlightNo(), searchDTO.getFlightDate());
        String flightNo = searchDTO.getFlightNo();
        String flightDate = searchDTO.getFlightDate();
        List <FlightEvent> searchResponse =  flightStatusService.flightStatus(flightNo, flightDate);
        log.info("Event count for the searched flight is: {}", searchResponse.size());
        return searchResponse;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
