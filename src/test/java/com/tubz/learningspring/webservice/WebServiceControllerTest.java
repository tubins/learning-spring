package com.tubz.learningspring.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubz.learningspring.business.ReservationService;
import com.tubz.learningspring.business.RoomReservation;
import com.tubz.learningspring.data.Guest;
import com.tubz.learningspring.data.Room;
import com.tubz.learningspring.util.DateUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for @{@link WebServiceController}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(WebServiceController.class)
class WebServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private DateUtils dateUtils;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getRoomReservationList() throws Exception {
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoomId(100);
        roomReservation.setRoomNumber("ROOM_100");
        String dateString = "2022-10-10";
        when(reservationService.getRoomReservationsForDate(any())).thenReturn(List.of(roomReservation));

        mockMvc.perform(get("/api/reservations?dateString=" + dateString)).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].roomId", Matchers.is(100)))
                .andExpect(jsonPath("$[0].roomNumber", Matchers.is("ROOM_100")));
    }

    @Test
    void getGuests() throws Exception {
        Guest guest = new Guest();
        guest.setGuestId(200l);
        guest.setLastName("Guest");
        guest.setFirstName("User");
        when(reservationService.getAllGuests()).thenReturn(List.of(guest));

        mockMvc.perform(get("/api/guests")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].guestId", Matchers.is(200)))
                .andExpect(jsonPath("$[0].lastName", Matchers.is("Guest")))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("User")));


    }

    @Test
    void saveGuest() throws Exception {
        String guest = "{\n" +
                "    \"firstName\": \"Test\",\n" +
                "    \"lastName\": \"User\",\n" +
                "    \"emailAddress\": \"test.user@msu.edu\",\n" +
                "    \"address\": \"100 Street\",\n" +
                "    \"country\": \"US\",\n" +
                "    \"state\": \"New-york\",\n" +
                "    \"phoneNumber\": \"0123-101156\" \n" +
                "}";

        mockMvc.perform(post("/api/guests").contentType(MediaType.APPLICATION_JSON).content(guest.getBytes()))
                .andExpect(status().isCreated());
        verify(reservationService).saveGuest(objectMapper.readValue(guest, Guest.class));
    }

    @Test
    void getRooms() throws Exception {
        Room room = new Room();
        room.setId(100l);
        room.setName("Suite Room");
        room.setRoomNumber("ROOM_100");
        when(reservationService.getAllRooms()).thenReturn(List.of(room));

        mockMvc.perform(get("/api/rooms")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(100)))
                .andExpect(jsonPath("$[0].roomNumber", Matchers.is("ROOM_100")))
                .andExpect(jsonPath("$[0].name", Matchers.is("Suite Room")));
    }
}