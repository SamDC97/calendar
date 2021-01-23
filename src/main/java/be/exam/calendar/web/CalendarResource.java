package be.exam.calendar.web;

import be.exam.calendar.service.CalendarService;
import be.exam.calendar.service.dto.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CalendarResource {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/generate-calendar")
    public ResponseEntity<List<Calendar>> generateCalendar(){
        return new ResponseEntity<>(calendarService.generateCalendar(), HttpStatus.OK);
    }

    @GetMapping("/calendars")
    public ResponseEntity<List<Calendar>> getCalendar(){
        return new ResponseEntity<>(calendarService.getCalendar(), HttpStatus.OK);
    }
}
