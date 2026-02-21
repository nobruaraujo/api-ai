package com.nobru.api_ai.api.domain.schedule;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(
        name = "schedules",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_schedule_barber_date",
                        columnNames = {"barber_id", "period"}
                )
        }
)
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long barberId;

    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots;

    protected Schedule(){}

    public Schedule(Long id, Long barberId, LocalDate date, List<TimeSlot> timeSlots) {
        this.id = id;
        this.barberId = barberId;
        this.date = date;
        this.timeSlots = timeSlots;
    }

    public Schedule(Long barberId, LocalDate date, List<TimeSlot> timeSlots) {
        this.barberId = barberId;
        this.date = date;
        timeSlots.forEach(slot -> slot.setSchedule(this));
        this.timeSlots = timeSlots;
    }

    public boolean hasAvailableTimeSlot() {
        return timeSlots.stream().anyMatch(TimeSlot::isAvailable);
    }

    public List<LocalTime> getAvailableTimes() {
        return timeSlots.stream()
                .filter(TimeSlot::isAvailable)
                .map(TimeSlot::getStartTime)
                .sorted()
                .toList();
    }

    public static Schedule create(Long barberId, LocalDate date, List<LocalTime> slots) {
        List<TimeSlot> timeSlots = slots.stream()
                .map(time -> new TimeSlot(time, true))
                .toList();

        Schedule schedule = new Schedule(barberId, date, timeSlots);

        timeSlots.forEach(slot -> slot.setSchedule(schedule));

        return schedule;
    }


}
