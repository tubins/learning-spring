package com.tubz.learningspring.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Class represents a room.
 */
@Entity
@Table(name = "ROOM")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Column(name = "BED_INFO")
    private String bedInfo;
}
