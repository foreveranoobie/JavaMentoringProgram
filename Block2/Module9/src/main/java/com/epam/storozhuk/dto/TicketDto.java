package com.epam.storozhuk.dto;

import com.epam.storozhuk.entity.Category;
import java.util.List;

public class TicketDto {

    private Long userId;
    private Long eventId;
    private Category category;
    private int place;
    private String cinemaName;
    private String cinemaAddress;
    private List<String> cinemaPhones;
    private List<String> cinemaFacilities;

    public Category getCategory() {
        return category;
    }

    public int getPlace() {
        return place;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public List<String> getCinemaPhones() {
        return cinemaPhones;
    }

    public List<String> getCinemaFacilities() {
        return cinemaFacilities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public void setCinemaPhones(List<String> cinemaPhones) {
        this.cinemaPhones = cinemaPhones;
    }

    public void setCinemaFacilities(List<String> cinemaFacilities) {
        this.cinemaFacilities = cinemaFacilities;
    }
}
