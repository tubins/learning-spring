package com.tubz.learningspring.webservice;

import com.tubz.learningspring.business.ReservationService;
import com.tubz.learningspring.business.RoomReservation;
import com.tubz.learningspring.data.Guest;
import com.tubz.learningspring.data.Room;
import com.tubz.learningspring.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Web service API controller.
 */
@RestController
@RequestMapping("/api")
public class WebServiceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    /**
     * Constructor.
     *
     * @param dateUtils          date utils.
     * @param reservationService reservation service.
     */
    public WebServiceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    /**
     * Api to get all the room reservations.
     *
     * @param dateString date string to fetch all the reservation for a given date.
     * @return all the reservations.
     */
    @GetMapping("/reservations")
    public List<RoomReservation> getRoomReservationList(@RequestParam(required = false) final String dateString) {
        Date date = this.dateUtils.createDateFromDateString(dateString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    /**
     * Api to get all the guests details.
     *
     * @return all guests details
     */
    @GetMapping("/guests")
    public List<Guest> getGuests() {
        return reservationService.getAllGuests();
    }

    /**
     * Api to add new guest details.
     *
     * @param guestDetails guest details to add.
     */
    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveGuest(@Valid @RequestBody final Guest guestDetails) {
        this.reservationService.saveGuest(guestDetails);
    }

    /**
     * Api to get all the rooms details.
     *
     * @return all rooms details
     */
    @GetMapping("/rooms")
    public List<Room> getRooms() {
        return reservationService.getAllRooms();
    }
}
