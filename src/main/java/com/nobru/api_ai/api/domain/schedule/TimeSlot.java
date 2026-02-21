package com.nobru.api_ai.api.domain.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "time_slots")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private LocalTime startTime;

    @Getter
    private boolean available;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    protected TimeSlot() {}

    public TimeSlot(Long id, LocalTime startTime, boolean available, Schedule schedule) {
        this.id = id;
        this.startTime = startTime;
        this.available = available;
    }

    public TimeSlot(LocalTime startTime, boolean available) {
        this.startTime = startTime;
        this.available = available;
    }

    public void book() {
        this.available = false;
    }
}
