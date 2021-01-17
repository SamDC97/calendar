package be.exam.calendar.service.mapper;

import be.exam.calendar.domain.CalendarEntity;
import be.exam.calendar.service.dto.Calendar;
import be.exam.calendar.service.dto.GP;
import org.springframework.stereotype.Component;

@Component
public class CalendarMapper {

    public CalendarEntity toEntity(Calendar calendar){
        return new CalendarEntity(calendar.getId(), calendar.getGpId(), calendar.getOrderInCalendar());
    }

    public Calendar toDTO(CalendarEntity calendarEntity){
        return new Calendar(calendarEntity.getId(), calendarEntity.getOrderInCalendar(), calendarEntity.getGpId(), new GP());
    }
}
