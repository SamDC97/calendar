package be.exam.calendar.web;

import be.exam.calendar.service.CalendarService;
import be.exam.calendar.service.dto.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class CalendarResource {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/generate-calendar/{numberOfRaces}")
    public ResponseEntity<List<Calendar>> generateCalendar(@PathVariable int numberOfRaces){
        log.info("Call received to generate {} races", numberOfRaces);
        return new ResponseEntity<>(calendarService.generateCalendar(numberOfRaces), HttpStatus.OK);
    }

    @GetMapping("/calendars")
    public ResponseEntity<List<Calendar>> getCalendar(){
        log.info("Request received.");
        return new ResponseEntity<>(calendarService.getCalendar(), HttpStatus.OK);
    }
}
