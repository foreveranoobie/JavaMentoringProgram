package com.epam.storozhuk.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ticket implements Entity {

    private Long id;
    private Long eventId;
    private Long userId;
    private Category category;
    private int place;
    private String cinemaName;
    private String cinemaAddress;
    private List<Integer> cinemaPhones;
    private List<Integer> cinemaFacilities;
    private LocalDateTime purchaseDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static TicketBuilder builder() {
        return new Ticket().new TicketBuilder();
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getUserId() {
        return userId;
    }

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

    public List<Integer> getCinemaPhones() {
        return cinemaPhones;
    }

    public List<Integer> getCinemaFacilities() {
        return cinemaFacilities;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public class TicketBuilder {

        public TicketBuilder id(Long id) {
            Ticket.this.id = id;
            return this;
        }

        public TicketBuilder eventId(Long eventId) {
            Ticket.this.eventId = eventId;
            return this;
        }

        public TicketBuilder userId(Long userId) {
            Ticket.this.userId = userId;
            return this;
        }

        public TicketBuilder category(Category category) {
            Ticket.this.category = category;
            return this;
        }

        public TicketBuilder place(int place) {
            Ticket.this.place = place;
            return this;
        }

        public TicketBuilder cinemaName(String cinemaName) {
            Ticket.this.cinemaName = cinemaName;
            return this;
        }

        public TicketBuilder cinemaAddress(String cinemaAddress) {
            Ticket.this.cinemaAddress = cinemaAddress;
            return this;
        }

        public TicketBuilder cinemaPhones(List<Integer> cinemaPhones) {
            Ticket.this.cinemaPhones = new ArrayList<>(cinemaPhones);
            return this;
        }

        public TicketBuilder cinemaFacilities(List<Integer> cinemaFacilities) {
            Ticket.this.cinemaFacilities = new ArrayList<>(cinemaFacilities);
            return this;
        }

        public TicketBuilder purchaseDate(LocalDateTime purchaseDate) {
            Ticket.this.purchaseDate = purchaseDate;
            return this;
        }

        public TicketBuilder createDate(LocalDateTime createDate) {
            Ticket.this.createDate = createDate;
            return this;
        }

        public TicketBuilder updateDate(LocalDateTime updateDate) {
            Ticket.this.updateDate = updateDate;
            return this;
        }

        public Ticket build() {
            return Ticket.this;
        }
    }

    @Override
    public boolean equals(Object compareTo) {
        if (this == compareTo) {
            return true;
        }
        if (compareTo == null || getClass() != compareTo.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) compareTo;
        return place == ticket.place && id.equals(ticket.id) && eventId.equals(ticket.eventId) && userId.equals(
                ticket.userId) && category == ticket.category && cinemaName.equals(ticket.cinemaName)
                && cinemaAddress.equals(ticket.cinemaAddress) && cinemaPhones.equals(ticket.cinemaPhones)
                && cinemaFacilities.equals(ticket.cinemaFacilities) && purchaseDate.equals(ticket.purchaseDate)
                && createDate.equals(ticket.createDate) && updateDate.equals(ticket.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, userId, category, place, cinemaName, cinemaAddress, cinemaPhones,
                cinemaFacilities,
                purchaseDate, createDate, updateDate);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                ", place=" + place +
                ", cinemaName='" + cinemaName + '\'' +
                ", cinemaAddress='" + cinemaAddress + '\'' +
                ", cinemaPhones=" + cinemaPhones +
                ", cinemaFacilities=" + cinemaFacilities +
                ", purchaseDate=" + purchaseDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
