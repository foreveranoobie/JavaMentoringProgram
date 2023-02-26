package com.epam.storozhuk.it.facade;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.TestConfig;
import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Category;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.facade.BookingFacade;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingFacadeIT {

    @Autowired
    private BookingFacade bookingFacade;

    @Test
    public void shouldCreateNewTicket() {
        Long userId = 1L;
        Long eventId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket expectedTicket = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(1)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setUserId(userId);
        ticketDto.setEventId(eventId);
        ticketDto.setPlace(expectedTicket.getPlace());
        ticketDto.setCinemaName(expectedTicket.getCinemaName());
        ticketDto.setCinemaPhones(expectedTicket.getCinemaPhones());
        ticketDto.setCinemaFacilities(expectedTicket.getCinemaFacilities());
        ticketDto.setCinemaAddress(expectedTicket.getCinemaAddress());
        ticketDto.setCategory(expectedTicket.getCategory());
        Ticket receivedTicket = bookingFacade.bookTicket(ticketDto);
        assertEquals(expectedTicket, receivedTicket);
    }

    @Test
    public void shouldReturnAllBookedTicketsForUserAndEvent() {
        Long userId = 1L;
        Long eventId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket expectedTicket1 = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(1)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();

        Ticket expectedTicket2 = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(3)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.PREMIUM)
                .build();

        Ticket expectedTicket3 = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(5)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.ULTRA)
                .build();

        TicketDto ticketDto1 = new TicketDto();
        ticketDto1.setUserId(userId);
        ticketDto1.setEventId(eventId);
        ticketDto1.setPlace(expectedTicket1.getPlace());
        ticketDto1.setCinemaName(expectedTicket1.getCinemaName());
        ticketDto1.setCinemaPhones(expectedTicket1.getCinemaPhones());
        ticketDto1.setCinemaFacilities(expectedTicket1.getCinemaFacilities());
        ticketDto1.setCinemaAddress(expectedTicket1.getCinemaAddress());
        ticketDto1.setCategory(expectedTicket1.getCategory());

        TicketDto ticketDto2 = new TicketDto();
        ticketDto2.setUserId(userId);
        ticketDto2.setEventId(eventId);
        ticketDto2.setPlace(expectedTicket2.getPlace());
        ticketDto2.setCinemaName(expectedTicket2.getCinemaName());
        ticketDto2.setCinemaPhones(expectedTicket2.getCinemaPhones());
        ticketDto2.setCinemaFacilities(expectedTicket2.getCinemaFacilities());
        ticketDto2.setCinemaAddress(expectedTicket2.getCinemaAddress());
        ticketDto2.setCategory(expectedTicket2.getCategory());

        TicketDto ticketDto3 = new TicketDto();
        ticketDto3.setUserId(userId);
        ticketDto3.setEventId(eventId);
        ticketDto3.setPlace(expectedTicket3.getPlace());
        ticketDto3.setCinemaName(expectedTicket3.getCinemaName());
        ticketDto3.setCinemaPhones(expectedTicket3.getCinemaPhones());
        ticketDto3.setCinemaFacilities(expectedTicket3.getCinemaFacilities());
        ticketDto3.setCinemaAddress(expectedTicket3.getCinemaAddress());
        ticketDto3.setCategory(expectedTicket3.getCategory());
        bookingFacade.bookTicket(ticketDto1);
        bookingFacade.bookTicket(ticketDto2);
        bookingFacade.bookTicket(ticketDto3);

        List<Ticket> ticketsForUser = bookingFacade.getBookedTicketsForUser(userId, 0, 0);
        assertEquals(3, ticketsForUser.size());

        List<Ticket> ticketsForEvent = bookingFacade.getBookedTicketsForEvent(eventId, 0, 0);
        assertEquals(3, ticketsForEvent.size());
    }

    @Test
    public void shouldReturnPagedBookedTicketsForUserAndEvent() {
        Long userId = 1L;
        Long eventId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket expectedTicket1 = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(1)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();

        Ticket expectedTicket2 = Ticket.builder()
                .id(2L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(3)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.PREMIUM)
                .build();

        Ticket expectedTicket3 = Ticket.builder()
                .id(3L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(5)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.ULTRA)
                .build();

        Ticket expectedTicket4 = Ticket.builder()
                .id(4L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(5)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();

        TicketDto ticketDto1 = new TicketDto();
        ticketDto1.setUserId(userId);
        ticketDto1.setEventId(expectedTicket1.getEventId());
        ticketDto1.setPlace(expectedTicket1.getPlace());
        ticketDto1.setCinemaName(expectedTicket1.getCinemaName());
        ticketDto1.setCinemaPhones(expectedTicket1.getCinemaPhones());
        ticketDto1.setCinemaFacilities(expectedTicket1.getCinemaFacilities());
        ticketDto1.setCinemaAddress(expectedTicket1.getCinemaAddress());
        ticketDto1.setCategory(expectedTicket1.getCategory());

        TicketDto ticketDto2 = new TicketDto();
        ticketDto2.setUserId(userId);
        ticketDto2.setEventId(expectedTicket2.getEventId());
        ticketDto2.setPlace(expectedTicket2.getPlace());
        ticketDto2.setCinemaName(expectedTicket2.getCinemaName());
        ticketDto2.setCinemaPhones(expectedTicket2.getCinemaPhones());
        ticketDto2.setCinemaFacilities(expectedTicket2.getCinemaFacilities());
        ticketDto2.setCinemaAddress(expectedTicket2.getCinemaAddress());
        ticketDto2.setCategory(expectedTicket2.getCategory());

        TicketDto ticketDto3 = new TicketDto();
        ticketDto3.setUserId(userId);
        ticketDto3.setEventId(expectedTicket3.getEventId());
        ticketDto3.setPlace(expectedTicket3.getPlace());
        ticketDto3.setCinemaName(expectedTicket3.getCinemaName());
        ticketDto3.setCinemaPhones(expectedTicket3.getCinemaPhones());
        ticketDto3.setCinemaFacilities(expectedTicket3.getCinemaFacilities());
        ticketDto3.setCinemaAddress(expectedTicket3.getCinemaAddress());
        ticketDto3.setCategory(expectedTicket3.getCategory());

        TicketDto ticketDto4 = new TicketDto();
        ticketDto4.setUserId(userId);
        ticketDto4.setEventId(expectedTicket4.getEventId());
        ticketDto4.setPlace(expectedTicket4.getPlace());
        ticketDto4.setCinemaName(expectedTicket4.getCinemaName());
        ticketDto4.setCinemaPhones(expectedTicket4.getCinemaPhones());
        ticketDto4.setCinemaFacilities(expectedTicket4.getCinemaFacilities());
        ticketDto4.setCinemaAddress(expectedTicket4.getCinemaAddress());
        ticketDto4.setCategory(expectedTicket4.getCategory());
        bookingFacade.bookTicket(ticketDto1);
        bookingFacade.bookTicket(ticketDto2);
        bookingFacade.bookTicket(ticketDto3);
        bookingFacade.bookTicket(ticketDto4);

        List<Ticket> firstPageUserTickets = bookingFacade.getBookedTicketsForUser(userId, 1, 2);
        assertAll(() -> {
            assertEquals(2, firstPageUserTickets.size());
            assertEquals(expectedTicket1, firstPageUserTickets.get(0));
            assertEquals(expectedTicket2, firstPageUserTickets.get(1));
        });

        List<Ticket> secondPageUserTickets = bookingFacade.getBookedTicketsForUser(userId, 2, 2);
        assertAll(() -> {
            assertEquals(2, secondPageUserTickets.size());
            assertEquals(expectedTicket3, secondPageUserTickets.get(0));
            assertEquals(expectedTicket4, secondPageUserTickets.get(1));
        });

        List<Ticket> firstPageEventTickets = bookingFacade.getBookedTicketsForEvent(eventId, 1, 2);
        assertAll(() -> {
            assertEquals(2, firstPageEventTickets.size());
            assertEquals(expectedTicket1, firstPageEventTickets.get(0));
            assertEquals(expectedTicket2, firstPageEventTickets.get(1));
        });

        List<Ticket> secondPageEventTickets = bookingFacade.getBookedTicketsForEvent(eventId, 2, 2);
        assertAll(() -> {
            assertEquals(2, secondPageEventTickets.size());
            assertEquals(expectedTicket3, secondPageEventTickets.get(0));
            assertEquals(expectedTicket4, secondPageEventTickets.get(1));
        });
    }

}