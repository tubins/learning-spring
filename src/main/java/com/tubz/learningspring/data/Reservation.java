package com.tubz.learningspring.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 * Reservation class.
 */
@Entity
@Table(name = "RESERVATION")
@Getter
@Setter
@ToString
public class Reservation {
    @Id
    @Column(name = "RESERVATION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reservationId;
    @Column(name = "ROOM_ID")
    private long roomId;
    @Column(name = "GUEST_ID")
    private long guestId;
    @Column(name = "RES_DATE")
    private Date reservationDate;
}