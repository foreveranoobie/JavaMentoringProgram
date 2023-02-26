package com.epam.storozhuk.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "cinema.tickets")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Ticket {

    @Id
    @GeneratedValue(generator = "ticket_seq")
    @SequenceGenerator(name = "ticket_seq", sequenceName = "tickets_id_seq", allocationSize = 1)
    private Long id;
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column
    private int place;
    @Column(name = "cinema_name")
    private String cinemaName;
    @Column(name = "cinema_address")
    private String cinemaAddress;
    @Type(type = "list-array")
    @Column(
            name = "cinema_phones",
            columnDefinition = "varchar[]"
    )
    private List<String> cinemaPhones;
    @Type(type = "list-array")
    @Column(
            name = "cinema_facilities",
            columnDefinition = "varchar[]"
    )
    private List<String> cinemaFacilities;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public static TicketBuilder builder() {
        return new Ticket().new TicketBuilder();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaPhones(List<String> cinemaPhones) {
        this.cinemaPhones = cinemaPhones;
    }

    public Collection<String> getCinemaPhones() {
        return cinemaPhones;
    }

    public void setCinemaFacilities(List<String> cinemaFacilities) {
        this.cinemaFacilities = cinemaFacilities;
    }

    public Collection<String> getCinemaFacilities() {
        return cinemaFacilities;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public class TicketBuilder {

        public TicketBuilder id(Long id) {
            Ticket.this.id = id;
            return this;
        }

        public TicketBuilder event(Event event) {
            Ticket.this.event = event;
            return this;
        }

        public TicketBuilder user(User user) {
            Ticket.this.user = user;
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

        public TicketBuilder cinemaPhones(List<String> cinemaPhones) {
            Ticket.this.cinemaPhones = new LinkedList<>(cinemaPhones);
            return this;
        }

        public TicketBuilder cinemaFacilities(List<String> cinemaFacilities) {
            Ticket.this.cinemaFacilities = new LinkedList<>(cinemaFacilities);
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
        return place == ticket.place && id.equals(ticket.id) && event.equals(ticket.event) && user.equals(
                ticket.user) && category == ticket.category && cinemaName.equals(ticket.cinemaName)
                && cinemaAddress.equals(ticket.cinemaAddress) && cinemaPhones.equals(ticket.cinemaPhones)
                && cinemaFacilities.equals(ticket.cinemaFacilities) && createDate.equals(ticket.createDate)
                && updateDate.equals(ticket.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, user, category, place, cinemaName, cinemaAddress, cinemaPhones,
                cinemaFacilities, createDate, updateDate);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", category=" + category +
                ", place=" + place +
                ", cinemaName='" + cinemaName + '\'' +
                ", cinemaAddress='" + cinemaAddress + '\'' +
                ", cinemaPhones=" + cinemaPhones +
                ", cinemaFacilities=" + cinemaFacilities +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
