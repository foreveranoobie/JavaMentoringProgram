package com.epam.storozhuk.facade;

import static com.epam.storozhuk.util.EntityFactory.createEvent;
import static com.epam.storozhuk.util.EntityFactory.createTicket;
import static com.epam.storozhuk.util.EntityFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.facade.impl.BookingFacadeImpl;
import com.epam.storozhuk.pdf.PdfGenerator;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookingFacadeTest {
    @Mock
    private UserService userService;
    @Mock
    private TicketService ticketService;
    @Mock
    private EventService eventService;
    @Mock
    private PdfGenerator pdfGenerator;

    @InjectMocks
    private BookingFacadeImpl sut;

    @Test
    public void shouldCreateNewTicket() {
        ArgumentCaptor<Ticket> captor = ArgumentCaptor.forClass(Ticket.class);
        final Long userId = 1L;
        final Long eventId = 2L;
        User user = createUser()
                .id(userId)
                .build();
        Event event = createEvent()
                .id(eventId)
                .build();
        Ticket expectedTicket = createTicket()
                .event(event)
                .user(user)
                .build();

        TicketDto ticketDto = new TicketDto();
        ticketDto.setUserId(userId);
        ticketDto.setEventId(eventId);
        ticketDto.setPlace(expectedTicket.getPlace());
        ticketDto.setCinemaName(expectedTicket.getCinemaName());
        ticketDto.setCinemaPhones((List<String>) expectedTicket.getCinemaPhones());
        ticketDto.setCinemaFacilities((List<String>) expectedTicket.getCinemaFacilities());
        ticketDto.setCinemaAddress(expectedTicket.getCinemaAddress());
        ticketDto.setCategory(expectedTicket.getCategory());

        Mockito.when(eventService.getEventById(eventId)).thenReturn(event);
        Mockito.when(userService.getUserById(userId)).thenReturn(user);

        sut.bookTicket(ticketDto);

        Mockito.verify(ticketService, Mockito.times(1)).bookTicket(captor.capture());
        Ticket realTicket = captor.getValue();
        Assertions.assertAll(() -> assertEquals(expectedTicket.getUser(), realTicket.getUser()),
                () -> assertEquals(expectedTicket.getEvent(), realTicket.getEvent()),
                () -> assertEquals(expectedTicket.getCategory(), realTicket.getCategory()),
                () -> assertEquals(expectedTicket.getCinemaAddress(), realTicket.getCinemaAddress()),
                () -> assertEquals(expectedTicket.getCinemaName(), realTicket.getCinemaName()),
                () -> assertEquals(expectedTicket.getCinemaPhones(), realTicket.getCinemaPhones()),
                () -> assertEquals(expectedTicket.getCinemaFacilities(), realTicket.getCinemaFacilities()),
                () -> assertEquals(expectedTicket.getPlace(), realTicket.getPlace()));
    }

    @Test
    public void shouldNotCreateTicketDueToLateDate() {
        final Long eventId = 1L;
        final Long userId = 2L;
        Event event = Event.builder()
                .id(eventId)
                .title("Film")
                .date(LocalDateTime.parse("2020-10-11T10:10"))
                .createDate(LocalDateTime.parse("2020-10-11T10:10"))
                .updateDate(LocalDateTime.parse("2020-10-11T10:10"))
                .build();

        User user = User.builder()
                .id(userId)
                .createDate(LocalDateTime.now())
                .build();

        doReturn(event).when(eventService).getEventById(eq(eventId));
        doReturn(user).when(userService).getUserById(eq(userId));

        TicketDto ticketDto = new TicketDto();
        ticketDto.setEventId(eventId);
        ticketDto.setUserId(userId);

        Mockito.verify(ticketService, Mockito.never()).bookTicket(Mockito.any(Ticket.class));

        assertThrows(IllegalArgumentException.class, () -> sut.bookTicket(ticketDto));
    }
}
