package be.exam.calendar.service.mapper;

import be.exam.calendar.domain.CalendarEntity;
import be.exam.calendar.service.dto.Calendar;
import be.exam.calendar.service.dto.Circuit;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CalendarMapper {

    public CalendarEntity toEntity(Calendar calendar){
        return new CalendarEntity(calendar.getId(), calendar.getGpId(), calendar.getOrderInCalendar(), 0L);
    }

    public Calendar toDTO(CalendarEntity calendarEntity){
        return new Calendar(calendarEntity.getId(), calendarEntity.getOrderInCalendar(), 0L, calendarEntity.getCircuitId(), new Circuit());
    }
}
