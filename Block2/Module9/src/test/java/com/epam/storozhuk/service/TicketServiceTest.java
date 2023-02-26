package com.epam.storozhuk.service;

import static com.epam.storozhuk.util.EntityFactory.createTicket;
import static com.epam.storozhuk.util.EntityFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private TicketServiceImpl sut;

    @Test
    public void shouldBookTicket() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Ticket createTicket = createTicket().build();
        User user = createTicket.getUser();
        Event event = createTicket.getEvent();
        float expectedBookedBalance = user.getBalance() - event.getTicketPrice();

        sut.bookTicket(createTicket);

        verify(ticketDao, times(1)).save(createTicket);
        verify(userService).createUser(userArgumentCaptor.capture());
        assertEquals(expectedBookedBalance, userArgumentCaptor.getValue().getBalance());
    }

    @Test
    public void shouldNotBookTicketForUserWithLowBalance() {
        User user = createUser().balance(45).build();
        Ticket createTicket = createTicket().user(user).build();
        assertThrows(IllegalArgumentException.class, () -> sut.bookTicket(createTicket));
    }
}
