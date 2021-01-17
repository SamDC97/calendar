package be.exam.calendar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {

    private Long id;
    private Long orderInCalendar;
    private Long gpId;

    private GP gp;
}
