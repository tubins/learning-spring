package com.tubz.learningspring.web;

import com.tubz.learningspring.business.ReservationService;
import com.tubz.learningspring.data.Guest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Guest controller.
 */
@Controller
@RequestMapping("/guests")
public class GuestController {
    private final ReservationService reservationService;

    public GuestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Gets all the guests details.
     *
     * @param model model.
     * @return view with guest details.
     */
    @GetMapping
    public String getAllGuests(final Model model) {
        List<Guest> allGuests = this.reservationService.getAllGuests();
        model.addAttribute("allGuests", allGuests);
        return "guests";
    }
}
