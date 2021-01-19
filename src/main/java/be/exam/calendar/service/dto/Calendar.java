package be.exam.calendar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {

    private Long id;
    private Long orderInCalendar;
    private Long dateOfRace;
    private Long gpId;

    private Circuit circuit;
}
