package com.tubz.learningspring.business;

import com.tubz.learningspring.data.*;
import com.tubz.learningspring.exception.BadRequestException;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for @{@link ReservationService}.
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private GuestRepository guestRepository;

    @Test
    void getRoomReservationsForDate() {
        when(roomRepository.findAll()).thenReturn(List.of(getTestRoom()));
        when(guestRepository.findById(any())).thenReturn(Optional.of(getTestGuest()));
        when(reservationRepository.findReservationByReservationDate(any())).thenReturn(List.of(getTestReservation()));

        Date date = new Date(DateUtil.now().getTime());
        List<RoomReservation> results = reservationService.getRoomReservationsForDate(date);
        assertNotNull(results);
        verify(roomRepository).findAll();
        verify(reservationRepository).findReservationByReservationDate(date);
        verify(guestRepository).findById(getTestGuest().getGuestId());

        assertEquals(1, results.size());
        RoomReservation roomReservation = results.get(0);
        assertNotNull(roomReservation);
        assertEquals(100L, roomReservation.getRoomId());
        assertEquals("Suite room", roomReservation.getRoomName());
        assertEquals("X1", roomReservation.getRoomNumber());
        assertEquals(300L, roomReservation.getGuestId());
        assertEquals("Guest", roomReservation.getFirstName());
        assertEquals("User", roomReservation.getLastName());
        assertEquals(date, roomReservation.getDate());
    }

    @Test
    void getAllGuests() {
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(getTestGuest());
        when(guestRepository.findAll()).thenReturn(guests);
        List<Guest> allGuests = reservationService.getAllGuests();
        assertNotNull(allGuests);
        assertEquals(1, allGuests.size());
        assertEquals(getTestGuest(), allGuests.get(0));
        verify(guestRepository).findAll();
    }

    @Test
    void saveGuest() {
        Guest guestToSave = getTestGuest();
        when(guestRepository.save(any(Guest.class))).thenReturn(guestToSave);
        reservationService.saveGuest(guestToSave);
        verify(guestRepository).save(guestToSave);
    }

    @Test
    void saveGuestShouldThrowExceptionIfGuestIsNull() {
        assertThrows(BadRequestException.class, () -> reservationService.saveGuest(null));
    }

    @Test
    void getAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(getTestRoom()));
        List<Room> allRooms = reservationService.getAllRooms();
        assertNotNull(allRooms);
        assertEquals(1, allRooms.size());
        assertEquals(getTestRoom(), allRooms.get(0));
        verify(roomRepository).findAll();
    }

    private Room getTestRoom() {
        Room room = new Room();
        room.setRoomNumber("X1");
        room.setId(100L);
        room.setBedInfo("King size");
        room.setName("Suite room");
        return room;
    }

    private Reservation getTestReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(200L);
        reservation.setRoomId(100L);
        reservation.setGuestId(300L);
        reservation.setReservationDate(new Date(DateUtil.now().getTime()));
        return reservation;
    }

    private Guest getTestGuest() {
        Guest guest = new Guest();
        guest.setGuestId(300L);
        guest.setFirstName("Guest");
        guest.setLastName("User");
        guest.setAddress("Address line 1");
        guest.setCountry("UK");
        guest.setEmailAddress("guest.user@test.com");
        guest.setPhoneNumber("0123-102030");
        guest.setState("Kent");
        return guest;
    }
}