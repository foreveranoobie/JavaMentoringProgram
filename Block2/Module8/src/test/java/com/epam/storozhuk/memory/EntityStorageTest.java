package com.epam.storozhuk.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.memory.EntityStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityStorageTest {

    private EntityStorage sut;

    @BeforeEach
    public void init() {
        sut = new EntityStorage();
    }

    @Test
    public void shouldCorrectlySetIndexOnNewEntity() {
        User user = new User();
        sut.save("user", user);

        assertTrue(user.getId() == 1L);
        User realUser = (User) sut.get("user:1");
        assertEquals(user, realUser);

        Event event = new Event();
        sut.save("event", event);

        assertTrue(event.getId() == 1L);
        Event realEvent = (Event) sut.get("event:1");
        assertEquals(event, realEvent);
    }

    @Test
    public void shouldCorrectlyUpdateIndexAfterEntityRemoval() {
        User user = User.builder()
                .username("firstUser")
                .build();
        sut.save("user", user);

        user = User.builder()
                .username("secondUser")
                .build();
        sut.save("user", user);

        user = User.builder()
                .username("thirdUser")
                .build();
        sut.save("user", user);

        sut.delete("user", 2L);

        assertEquals(user.getId(), ((User) sut.get("user:3")).getId());

        assertEquals(2, sut.getAllValues().size());

        user = User.builder()
                .username("fourthUser")
                .build();
        sut.save("user", user);

        assertEquals(4L, user.getId());
    }
}
