package be.exam.calendar.service;

import be.exam.calendar.domain.repository.CalendarRepository;
import be.exam.calendar.service.dto.Calendar;
import be.exam.calendar.service.dto.Circuit;
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
import java.util.concurrent.ThreadLocalRandom;
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
    private String gpIdURL;

    public void addCalendar(Calendar calendar){
        calendarRepository.save(calendarMapper.toEntity(calendar));
    }

    public List<Calendar> generateCalendar(){
        
        try{
            List<Circuit> circuitList = getListOfCircuits();

            // TODO: add dates to Calendar or find a way to save them to GP.
            List<Long> listOfDates = generateRandomDates(Long.valueOf(circuitList.size()));

            List<Calendar> calendarList = new ArrayList<>();
            Collections.shuffle(circuitList);

            for (int i = 0; i < circuitList.size(); i++){
                Long index = Long.valueOf(i);

                Calendar calendar = new Calendar(index, index + 1, listOfDates.get(i),circuitList.get(i).getId(), circuitList.get(i));
                calendarRepository.save(calendarMapper.toEntity(calendar));
                calendarList.add(calendar);
            }

            return calendarList;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Circuit> getListOfCircuits() throws URISyntaxException {
        URI gpURI = new URI(allCircuitsURL);
        List<Circuit> circuitList = new ArrayList<>();
        ResponseEntity<List<Circuit>> circuitListEntity = restTemplate.exchange(allCircuitsURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Circuit>>() {}, Collections.emptyMap());
        if (circuitListEntity.getStatusCode() == HttpStatus.OK) {
            circuitList= circuitListEntity.getBody();
        }
        return circuitList;
    }

    public List<Calendar> getCalendar(){
        List<Calendar> calendarList = StreamSupport.stream(calendarRepository.findAll().spliterator(), false)
                .map(c -> calendarMapper.toDTO(c))
                .collect(Collectors.toList());
        for (Calendar calendar : calendarList){
            calendar.setCircuit(getRESTCircuit(calendar.getGpId()));
        }
        return calendarList;
    }

    private Circuit getRESTCircuit(Long id){
        try{
            URI circuitURI = new URI(gpIdURL + id);
            return restTemplate.getForObject(circuitURI, Circuit.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generates a random day and than calculates the rest of the schedule
    private List<Long> generateRandomDates(Long numberOfDatesNeeded){
        List<Long> listOfDates = new ArrayList<>();

        LocalDate startDate = LocalDate.of(2021, 1, 1); //start date
        Long start = startDate.toEpochDay();
        LocalDate endDate = LocalDate.of(2021, 3, 1); //end date
        Long end = endDate.toEpochDay();

        Long randomDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        System.out.println(LocalDate.ofEpochDay(randomDay)); // random date between the range

        for (int i = 0; i < numberOfDatesNeeded; i++){
            randomDay = randomDay + 14;
            listOfDates.add(randomDay);
        }

        return listOfDates;
    }

}
