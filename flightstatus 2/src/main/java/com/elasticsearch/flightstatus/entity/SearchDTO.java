package com.elasticsearch.flightstatus.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SearchDTO {
    
    @Size(min = 5, message = "Flight no. must be 5 chracter long}")
    @Size(max = 6, message = "Flight no. must not be more than 6 chracter long")
    private String flightNo;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message= "Input must be in YYYY-MM-DD format")
    private String flightDate;

    public SearchDTO(String flightNo, String flightDate){
        this.flightNo = flightNo;
        this.flightDate = flightDate;
    }

    public String getFlightNo() {
        return flightNo;
      }
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
      }
    public String getFlightDate() {
        return flightDate;
      }
    public void setFlightdate(String flightDate) {
        this.flightDate = flightDate;
      }
}
