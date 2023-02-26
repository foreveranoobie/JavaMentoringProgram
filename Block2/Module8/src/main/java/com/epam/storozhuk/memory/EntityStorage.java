package com.epam.storozhuk.memory;

import com.epam.storozhuk.entity.Entity;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Event.EventBuilder;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.entity.User.UserBuilder;
import com.epam.storozhuk.exception.EntityAbsentException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Plays key role as in-memory based storage
 */
public class EntityStorage {

    private final Logger logger = LoggerFactory.getLogger(EntityStorage.class);

    @Value("${path.users}")
    String usersPath;
    @Value("${path.events}")
    String eventsPath;

    private Map<String, Entity> storage;

    public EntityStorage() {
        storage = new HashMap<>();
    }

    /**
     * Retrieves {@link Entity} by key
     *
     * @param key entity's unique key identifier
     * @return {@link Entity}
     */
    public Entity get(String key) {
        logger.info("Returning entity with the following key:{}", key);
        Entity retrievedEntity = storage.get(key);
        if (retrievedEntity == null) {
            throw new EntityAbsentException(String.format("Entity %s is not present", key));
        } else {
            return retrievedEntity;
        }
    }

    /**
     * Puts entity into storage
     *
     * @param key contains entity name
     * @param entity the data to be saved
     * @return saved {@link Entity}
     */
    public Entity save(String key, Entity entity) {
        logger.info("Performing save for entity: {}", entity);
        String maxKey = storage.keySet().stream().filter(storageKey -> storageKey.matches(key + ":[1-9]+[0-9]*"))
                .max((firstKey, secondKey) -> {
                    Long firstNumber = Long.parseLong(firstKey.substring(firstKey.indexOf(":") + 1));
                    Long secondNumber = Long.parseLong(secondKey.substring(secondKey.indexOf(":") + 1));
                    return (int) (firstNumber - secondNumber);
                }).orElse(key.concat(":0"));
        maxKey = maxKey.substring(maxKey.indexOf(":") + 1);
        Long count = Long.parseLong(maxKey) + 1;
        entity.setId(count);
        storage.put(key.concat(":" + count), entity);
        logger.info("Performed save for entity: {}", entity);
        return entity;
    }

    /**
     * Removes entity from storage
     *
     * @param key containing entity type name
     * @param id of entity
     * @return removed {@link Entity}
     */
    public Entity delete(String key, Long id) {
        final String idKey = key.concat(":" + id);
        logger.info("Performing removing of {}", idKey);
        return storage.remove(idKey);
    }

    /**
     * Returns all the storage's values
     *
     * @return {@link Collection} of {@link Entity}
     */
    public Collection<Entity> getAllValues() {
        logger.info("Returning all values from storage");
        return storage.values();
    }

    @PostConstruct
    public void initStorage() throws IOException {
        initUsers();
        initEvents();
    }

    private void initUsers() throws IOException {
        List<String> usersData = new ArrayList<>(Files.readAllLines(Paths.get(usersPath)));
        List<User> users = new LinkedList<>();
        for (String userRow : usersData) {
            UserBuilder userBuilder = User.builder();
            String[] userColumns = userRow.split("\\|");
            for (String userColumn : userColumns) {
                String[] columnData = userColumn.split(" ");
                columnData[1] = columnData[1].replaceAll("\"", "");
                switch (columnData[0]) {
                    case "username":
                        userBuilder.username(columnData[1]);
                        break;
                    case "firstname":
                        userBuilder.firstname(columnData[1]);
                        break;
                    case "lastname":
                        userBuilder.lastname(columnData[1]);
                        break;
                    case "email":
                        userBuilder.email(columnData[1]);
                        break;
                    case "age":
                        userBuilder.age(Integer.parseInt(columnData[1]));
                        break;
                    case "createDate":
                        userBuilder.createDate(LocalDateTime.parse(columnData[1]));
                        userBuilder.updateDate(LocalDateTime.parse(columnData[1]));
                        break;
                    default:
                        break;
                }
            }
            users.add(userBuilder.build());
        }
        for (User user : users) {
            save("user", user);
        }
    }

    private void initEvents() throws IOException {
        List<String> eventsData = new ArrayList<>(Files.readAllLines(Paths.get(eventsPath)));
        List<Event> events = new LinkedList<>();
        for (String eventRow : eventsData) {
            EventBuilder eventBuilder = Event.builder();
            String[] eventColumns = eventRow.split("\\|");
            for (String eventColumn : eventColumns) {
                String[] columnData = eventColumn.split(" ");
                columnData[1] = columnData[1].replaceAll("\"", "");
                switch (columnData[0]) {
                    case "title":
                        eventBuilder.title(columnData[1]);
                        break;
                    case "date":
                        eventBuilder.date(LocalDateTime.parse(columnData[1]));
                        break;
                    case "createDate":
                        eventBuilder.createDate(LocalDateTime.parse(columnData[1]));
                        eventBuilder.updateDate(LocalDateTime.parse(columnData[1]));
                        break;
                    default:
                        break;
                }
            }
            events.add(eventBuilder.build());
        }
        for (Event event : events) {
            save("event", event);
        }
    }
}
