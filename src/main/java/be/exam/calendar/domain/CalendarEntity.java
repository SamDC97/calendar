package be.exam.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEntity {

    @Id
    private Long id;
    private Long gpId;
    private Long orderInCalendar;
}
