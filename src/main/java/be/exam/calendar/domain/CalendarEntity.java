package be.exam.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEntity {

    @Id
    private Long id;
    private Long circuitId;
    private Long orderInCalendar;
    private LocalDate dateOfRace;
    private Long raceId;
}
