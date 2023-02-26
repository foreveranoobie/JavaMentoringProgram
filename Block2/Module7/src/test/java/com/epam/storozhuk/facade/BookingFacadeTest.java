package com.epam.storozhuk.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Category;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.facade.impl.BookingFacadeImpl;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookingFacadeTest {

    private BookingFacade sut;

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @Mock
    private EventService eventService;

    @BeforeEach
    public void init() {
        sut = new BookingFacadeImpl(userService, eventService, ticketService);
    }

    @Test
    public void shouldCreateNewTicket() {
        ArgumentCaptor<Ticket> captor = ArgumentCaptor.forClass(Ticket.class);
        final Long userId = 1L;
        final Long eventId = 2L;
        Ticket expectedTicket = Ticket.builder()
                .id(1L)
                .userId(userId)
                .eventId(eventId)
                .purchaseDate(LocalDateTime.now())
                .place(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();
        TicketDto ticketDto = TicketDto.builder()
                .place(expectedTicket.getPlace())
                .cinemaName(expectedTicket.getCinemaName())
                .cinemaPhones(expectedTicket.getCinemaPhones())
                .cinemaFacilities(expectedTicket.getCinemaFacilities())
                .cinemaAddress(expectedTicket.getCinemaAddress())
                .category(expectedTicket.getCategory())
                .build();
        User user = User.builder()
                .id(userId)
                .username("user")
                .firstname("User")
                .lastname("User")
                .email("user@email.com")
                .age(25)
                .createDate(LocalDateTime.parse("2020-10-10T07:00"))
                .updateDate(LocalDateTime.parse("2020-10-10T07:00"))
                .build();
        Event event = Event.builder()
                .id(eventId)
                .title("Film")
                .date(LocalDateTime.now().plusMonths(1L))
                .createDate(LocalDateTime.parse("2021-10-11T10:10"))
                .updateDate(LocalDateTime.parse("2021-10-11T10:10"))
                .build();
        Mockito.when(eventService.getEventById(event.getId())).thenReturn(event);

        sut.bookTicket(userId, eventId, ticketDto);

        Mockito.verify(ticketService, Mockito.times(1)).bookTicket(captor.capture());
        Ticket realTicket = captor.getValue();
        Assertions.assertAll(() -> assertEquals(expectedTicket.getUserId(), realTicket.getUserId()),
                () -> assertEquals(expectedTicket.getEventId(), realTicket.getEventId()),
                () -> assertEquals(expectedTicket.getCategory(), realTicket.getCategory()),
                () -> assertEquals(expectedTicket.getCreateDate().truncatedTo(ChronoUnit.MINUTES),
                        realTicket.getCreateDate().truncatedTo(ChronoUnit.MINUTES)),
                () -> assertEquals(expectedTicket.getUpdateDate().truncatedTo(ChronoUnit.MINUTES),
                        realTicket.getUpdateDate().truncatedTo(ChronoUnit.MINUTES)),
                () -> assertEquals(expectedTicket.getPurchaseDate().truncatedTo(ChronoUnit.MINUTES),
                        realTicket.getPurchaseDate().truncatedTo(ChronoUnit.MINUTES)),
                () -> assertEquals(expectedTicket.getCinemaAddress(), realTicket.getCinemaAddress()),
                () -> assertEquals(expectedTicket.getCinemaName(), realTicket.getCinemaName()),
                () -> assertEquals(expectedTicket.getCinemaPhones(), realTicket.getCinemaPhones()),
                () -> assertEquals(expectedTicket.getCinemaFacilities(), realTicket.getCinemaFacilities()),
                () -> assertEquals(expectedTicket.getPlace(), realTicket.getPlace()));
    }

    @Test
    public void shouldNotCreateTicketDueToLateDate() {
        Event event = Event.builder()
                .id(1L)
                .title("Film")
                .date(LocalDateTime.parse("2020-10-11T10:10"))
                .createDate(LocalDateTime.parse("2020-10-11T10:10"))
                .updateDate(LocalDateTime.parse("2020-10-11T10:10"))
                .build();

        doReturn(event).when(eventService).getEventById(eq(1L));

        sut.bookTicket(1L, 1L, new TicketDto());

        Mockito.verify(ticketService, Mockito.never()).bookTicket(Mockito.any(Ticket.class));
    }
}
