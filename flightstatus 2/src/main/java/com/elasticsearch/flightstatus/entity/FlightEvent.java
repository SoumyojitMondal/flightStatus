package com.elasticsearch.flightstatus.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(indexName = "events")
@Mapping(dateDetection = Mapping.Detection.FALSE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightEvent {  
  @Id
    private UUID id;
    @Size(min = 5, message = "Flight no. must be 5 chracter long}")
    @Size(max = 6, message = "Flight no. must not be more than 6 chracter long")
    private String flightNo;
    @NotBlank(message = "Event Name must not be blank")
    private String eventName;
    @Pattern(regexp = "^([0-9]{4})-([0-1][0-9])-([0-3][0-9])\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$", message= "Input must be in YYYY-MM-DD HH:MM:SS format")
    private String eventTime;

    public FlightEvent(String flightNo, String eventName, String eventTime){

      this.id = UUID.randomUUID();
      this.flightNo = flightNo;
      this.eventName = eventName;
      this.eventTime = eventTime;
    }
    public UUID getId() {
        return id;
      }
    public void setId(UUID id) {
        this.id = id;
      }
    public String getFlightNo() {
        return flightNo;
      }
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
      }
    public String getEventName() {
        return eventName;
      }
    public void setEventName(String eventName) {
        this.eventName = eventName;
      }
    public String getEventTime() {
        return eventTime;
      }
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
      }

}
