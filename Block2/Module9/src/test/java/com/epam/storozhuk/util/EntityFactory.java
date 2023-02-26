package com.epam.storozhuk.util;

import com.epam.storozhuk.entity.Category;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Event.EventBuilder;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.Ticket.TicketBuilder;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.entity.User.UserBuilder;
import java.time.LocalDateTime;
import java.util.Arrays;

public class EntityFactory {

    public static UserBuilder createUser() {
        return User.builder()
                .id(1L)
                .username("user")
                .firstname("User")
                .lastname("User")
                .email("user@email.com")
                .age(25)
                .createDate(LocalDateTime.parse("2020-10-10T07:00"))
                .updateDate(LocalDateTime.parse("2020-10-10T07:00"))
                .balance(500);
    }

    public static EventBuilder createEvent() {
        return Event.builder()
                .id(2L)
                .title("Film")
                .date(LocalDateTime.now().plusMonths(1L))
                .createDate(LocalDateTime.parse("2021-10-11T10:10"))
                .updateDate(LocalDateTime.parse("2021-10-11T10:10"))
                .ticketPrice(50);
    }

    public static TicketBuilder createTicket() {
        return Ticket.builder()
                .id(1L)
                .user(createUser().build())
                .event(createEvent().build())
                .place(1)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .cinemaPhones(Arrays.asList("1234", "9876"))
                .cinemaFacilities(Arrays.asList("10", "2"))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART);
    }
}
