package backend.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private EventTag tag;
    private ArrayList<Pulse> pulses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventTag getTag() {
        return tag;
    }

    public void setTag(EventTag tag) {
        this.tag = tag;
    }

    public ArrayList<Pulse> getPulses() {
        return pulses;
    }

    public void setPulses(ArrayList<Pulse> pulses) {
        this.pulses = pulses;
    }
}
