package com.tubz.learningspring.WebServiceController;

import com.tubz.learningspring.business.ReservationService;
import com.tubz.learningspring.business.RoomReservation;
import com.tubz.learningspring.data.Guest;
import com.tubz.learningspring.util.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
