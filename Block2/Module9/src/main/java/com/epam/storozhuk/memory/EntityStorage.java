package com.epam.storozhuk.memory;

import com.epam.storozhuk.entity.Entity;
import com.epam.storozhuk.exception.EntityAbsentException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plays key role as in-memory based storage
 */
public class EntityStorage {

    private final Logger logger = LoggerFactory.getLogger(EntityStorage.class);

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
}
