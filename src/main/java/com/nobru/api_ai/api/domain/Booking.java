package com.nobru.api_ai.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_booking_barber_date_time",
                        columnNames = {"barber_id", "date", "time"}
                )
        }
)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barber_id", nullable = false)
    private Long barberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    private LocalDate date;
    private LocalTime time;

    public Booking(Long barberId, LocalDate date, LocalTime time, ServiceType serviceType) {
        this.barberId = barberId;
        this.date = date;
        this.time = time;
        this.serviceType = serviceType;
    }
}