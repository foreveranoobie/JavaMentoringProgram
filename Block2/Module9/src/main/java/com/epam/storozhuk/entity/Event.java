package com.epam.storozhuk.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cinema.events")
public class Event {

    @Id
    @GeneratedValue(generator = "events_seq")
    @SequenceGenerator(name = "events_seq", sequenceName = "events_id_seq", allocationSize = 1)
    private Long id;
    @Column
    private String title;
    @Column
    private LocalDateTime date;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "ticket_price")
    private float ticketPrice;

    public static EventBuilder builder() {
        return new Event().new EventBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public class EventBuilder {

        public EventBuilder id(long id) {
            Event.this.id = id;
            return this;
        }

        public EventBuilder title(String title) {
            Event.this.title = title;
            return this;
        }

        public EventBuilder date(LocalDateTime date) {
            Event.this.date = date;
            return this;
        }

        public EventBuilder createDate(LocalDateTime createDate) {
            Event.this.createDate = createDate;
            return this;
        }

        public EventBuilder updateDate(LocalDateTime updateDate) {
            Event.this.updateDate = updateDate;
            return this;
        }

        public EventBuilder ticketPrice(float ticketPrice) {
            Event.this.ticketPrice = ticketPrice;
            return this;
        }

        public Event build() {
            return Event.this;
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
