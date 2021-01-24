package be.exam.calendar.service;

import be.exam.calendar.domain.repository.CalendarRepository;
import be.exam.calendar.service.dto.Calendar;
import be.exam.calendar.service.dto.Circuit;
import be.exam.calendar.service.dto.Race;
import be.exam.calendar.service.mapper.CalendarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private CalendarMapper calendarMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.allCircuits}")
    private String allCircuitsURL;
    @Value("${url.circuitId}")
    private String circuitIdURL;
    @Value("${url.generateRace}")
    private String generateRaceURL;

    public List<Calendar> generateCalendar(int numberOfRaces){
        
        try{
            List<Circuit> listOfUniqueCircuits = getListOfCircuits();
            List<Circuit> circuitList = new ArrayList<>();
            List<Calendar> calendarList = new ArrayList<>();
            for (int i = 1; i <= numberOfRaces; i++) {

                List<LocalDate> listOfDates = generateRandomDates(numberOfRaces);
                Long circuitId = getCircuitId(i, listOfUniqueCircuits);
                Calendar calendar = new Calendar((long)i, (long)i, listOfDates.get(i-1), circuitId, generateRace(),getCircuit(circuitId, listOfUniqueCircuits));
                calendarRepository.save(calendarMapper.toEntity(calendar));
                calendarList.add(calendar);
            }

            return calendarList;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Calendar> getCalendar(){
        List<Calendar> calendarList = StreamSupport.stream(calendarRepository.findAll().spliterator(), false)
                .map(c -> calendarMapper.toDTO(c))
                .collect(Collectors.toList());
        for (Calendar calendar : calendarList){
            calendar.setCircuit(getRESTCircuit(calendar.getCircuitId()));
        }
        return calendarList;
    }

    private Long generateRace() {
        List<Race> raceList = new ArrayList<>();
        ResponseEntity<List<Race>> raceListEntity = restTemplate.exchange(generateRaceURL + 1, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Race>>() {}, Collections.emptyMap());
        if (raceListEntity.getStatusCode() == HttpStatus.OK) {
            raceList= raceListEntity.getBody();
        }
        return raceList.get(0).getId();

        //try{
       //     URI raceURI = new URI(generateRaceURL + 1);
        //    Race[] races = restTemplate.getForObject(raceURI, Race[].class);
       //
       //     return races;
       // } catch (URISyntaxException e) {
       //     e.printStackTrace();
        //    return null;
       // }
    }

    private Circuit getCircuit(Long circuitId, List<Circuit> circuitList) {
        for (Circuit circuit : circuitList){
            if (circuit.getId() == circuitId){
                return circuit;
            }
        }
        return null;
    }

    private Long getCircuitId(int index, List<Circuit> listOfUniqueCircuits) {
        try{
            return listOfUniqueCircuits.get(index).getId();
        } catch (IndexOutOfBoundsException e){
            int newIndex = index - listOfUniqueCircuits.size();
            return getCircuitId(newIndex, listOfUniqueCircuits);
        }
    }

    private List<Circuit> getListOfCircuits() throws URISyntaxException {
        List<Circuit> circuitList = new ArrayList<>();
        ResponseEntity<List<Circuit>> circuitListEntity = restTemplate.exchange(allCircuitsURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Circuit>>() {}, Collections.emptyMap());
        if (circuitListEntity.getStatusCode() == HttpStatus.OK) {
            circuitList= circuitListEntity.getBody();
        }
        return circuitList;
    }

    private Circuit getRESTCircuit(Long id){
        try{
            URI circuitURI = new URI(circuitIdURL + id);
            return restTemplate.getForObject(circuitURI, Circuit.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<LocalDate> generateRandomDates(int numberOfDatesNeeded){
        List<LocalDate> listOfDates = new ArrayList<>();
        for (int i = 0; i < numberOfDatesNeeded; i++) {
            listOfDates.add(LocalDate.now().plusWeeks(i));
        }
        return listOfDates;
    }
}
