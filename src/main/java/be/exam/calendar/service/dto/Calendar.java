package be.exam.calendar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {

    private Long id;
    private Long orderInCalendar;
    private LocalDate dateOfRace;
    private Long circuitId;
    private Long raceId;

    private Circuit circuit;
}
