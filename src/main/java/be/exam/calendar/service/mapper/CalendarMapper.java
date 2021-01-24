package be.exam.calendar.service.mapper;

import be.exam.calendar.domain.CalendarEntity;
import be.exam.calendar.service.dto.Calendar;
import be.exam.calendar.service.dto.Circuit;
import org.springframework.stereotype.Component;

@Component
public class CalendarMapper {

    public CalendarEntity toEntity(Calendar calendar){
        return new CalendarEntity(calendar.getId(), calendar.getCircuitId(), calendar.getOrderInCalendar(), calendar.getDateOfRace());
    }

    public Calendar toDTO(CalendarEntity calendarEntity){
        return new Calendar(calendarEntity.getId(), calendarEntity.getOrderInCalendar(), calendarEntity.getDateOfRace(), calendarEntity.getCircuitId(), new Circuit());
    }
}
