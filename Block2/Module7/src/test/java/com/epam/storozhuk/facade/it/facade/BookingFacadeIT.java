package com.epam.storozhuk.facade.it.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Category;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.facade.BookingFacade;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ApplicationContext.xml")
@ExtendWith(SpringExtension.class)
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
        TicketDto ticketDto = TicketDto.builder()
                .place(expectedTicket.getPlace())
                .cinemaName(expectedTicket.getCinemaName())
                .cinemaPhones(expectedTicket.getCinemaPhones())
                .cinemaFacilities(expectedTicket.getCinemaFacilities())
                .cinemaAddress(expectedTicket.getCinemaAddress())
                .category(expectedTicket.getCategory())
                .build();
        Ticket receivedTicket = bookingFacade.bookTicket(1L, 1L, ticketDto);
        assertEquals(expectedTicket, receivedTicket);
    }
}
