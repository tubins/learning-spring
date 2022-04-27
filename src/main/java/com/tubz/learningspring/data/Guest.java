package com.tubz.learningspring.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Guest class.
 */
@Entity
@Table(name = "GUEST")
@Getter
@Setter
@ToString
public class Guest {
    @Id
    @Column(name = "GUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "STATE")
    private String state;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}