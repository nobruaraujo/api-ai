package com.nobru.api_ai.api.domain.schedule;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(
        name = "schedules",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_schedule_barber_date",
                        columnNames = {"barber_id", "date"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long barberId;

    private LocalDate date;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSlot> timeSlots;

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

        Schedule schedule = Schedule.builder()
                .barberId(barberId)
                .date(date)
                .timeSlots(timeSlots)
                .build();

        timeSlots.forEach(slot -> slot.setSchedule(schedule));

        return schedule;
    }


}
