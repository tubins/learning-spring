package com.tubz.learningspring.business;

import com.tubz.learningspring.data.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service  class for room reservation.
 */
@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;


    /**
     * Constructor.
     *
     * @param roomRepository        room repo.
     * @param reservationRepository reservation repo.
     * @param guestRepository       guest repo.
     */
    public ReservationService(RoomRepository roomRepository, ReservationRepository reservationRepository, GuestRepository guestRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
    }

    /**
     * Gets all room reservation for a given date.
     *
     * @param date given date.
     * @return returns all the room reservations.
     */
    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort((o1, o2) -> {
            if (o1.getRoomName().equals(o2.getRoomName())) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            }
            return o1.getRoomName().compareTo(o2.getRoomName());
        });
        return roomReservations;
    }

    /**
     * Gets all the guests details.
     *
     * @return guests details.
     */
    public List<Guest> getAllGuests() {
        List<Guest> allGuests = this.guestRepository.findAll();
        allGuests.sort((o1, o2) -> {
            if (o1.getLastName().equals(o2.getLastName())) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
            return o1.getLastName().compareTo(o2.getLastName());
        });
        return allGuests;
    }

    /**
     * Save guest details.
     *
     * @param guestDetails guest details.
     */
    public void saveGuest(final Guest guestDetails) {
        if (guestDetails == null) {
            throw new RuntimeException("Guest cannot be null.");
        }
        this.guestRepository.save(guestDetails);
    }

    /**
     * Gets all the rooms details.
     *
     * @return all rooms.
     */
    public List<Room> getAllRooms() {
        return (List<Room>) this.roomRepository.findAll();
    }
}
