package org.lab5.lab5_eventsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Event {
    private int id;
    private String description;
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate start_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate end_date;
}
