package com.tubz.learningspring.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Guest class.
 */
@Entity
@Table(name = "GUEST")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Guest {
    @Id
    @Column(name = "GUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;
    @NotEmpty
    @Column(name = "FIRST_NAME")
    private String firstName;
    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastName;
    @NotEmpty
    @Email
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @NotEmpty
    @Column(name = "ADDRESS")
    private String address;
    @NotEmpty
    @Column(name = "COUNTRY")
    private String country;
    @NotEmpty
    @Column(name = "STATE")
    private String state;
    @NotEmpty
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}