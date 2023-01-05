package com.goaltracker.Model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Goal {

    @Expose(serialize = false, deserialize = false)
    public static final DateTimeFormatter customFormat = DateTimeFormatter.ISO_DATE_TIME;

    private final String name;
    private final String start;
    private final String end;

    public Goal(String name, LocalDateTime start) {
        this.name = name;
        this.start = start.format(customFormat);
        this.end = LocalDateTime.MAX.format(customFormat);
    }

    public Goal(String name, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.start = start.format(customFormat);
        this.end = end.format(customFormat);
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
