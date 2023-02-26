package com.epam.storozhuk.entity;

import java.time.LocalDateTime;

public class Event implements Entity {

    private Long id;
    private String title;
    private LocalDateTime date;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static EventBuilder builder() {
        return new Event().new EventBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
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

        public Event build() {
            return Event.this;
        }
    }
}
