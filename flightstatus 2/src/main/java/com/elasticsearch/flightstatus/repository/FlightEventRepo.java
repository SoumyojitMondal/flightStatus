package com.elasticsearch.flightstatus.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.elasticsearch.flightstatus.entity.FlightEvent;

@Repository
public interface FlightEventRepo extends ElasticsearchRepository<FlightEvent,Integer>{

    //@Query("{\"bool\": {\"must\": [{\"match\": {\"flightNo\": \"?0\"}},{\"query_string\": {\"query\": \"?1\",\"fields\": [\"eventTime\"]}}]}}")
    @Query("{\"bool\": {\"must\": [{\"match\": {\"flightNo\": \"?0\"}},{\"match_phrase\": {\"eventTime\": \"?1\"}}]}}")
    List<FlightEvent> findFlightStaus(String flightNo, String flightDate);

}
