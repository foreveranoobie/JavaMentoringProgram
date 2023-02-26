package com.epam.storozhuk.dto;

import com.epam.storozhuk.entity.Category;
import java.util.ArrayList;
import java.util.List;

public class TicketDto {

    private Category category;
    private int place;
    private String cinemaName;
    private String cinemaAddress;
    private List<Integer> cinemaPhones;
    private List<Integer> cinemaFacilities;

    public static TicketDtoBuilder builder() {
        return new TicketDto().new TicketDtoBuilder();
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

    public class TicketDtoBuilder {

        public TicketDto build() {
            return TicketDto.this;
        }

        public TicketDtoBuilder category(Category category) {
            TicketDto.this.category = category;
            return this;
        }

        public TicketDtoBuilder place(int place) {
            TicketDto.this.place = place;
            return this;
        }

        public TicketDtoBuilder cinemaName(String cinemaName) {
            TicketDto.this.cinemaName = cinemaName;
            return this;
        }

        public TicketDtoBuilder cinemaAddress(String cinemaAddress) {
            TicketDto.this.cinemaAddress = cinemaAddress;
            return this;
        }

        public TicketDtoBuilder cinemaPhones(List<Integer> cinemaPhones) {
            TicketDto.this.cinemaPhones = new ArrayList<>(cinemaPhones);
            return this;
        }

        public TicketDtoBuilder cinemaFacilities(List<Integer> cinemaFacilities) {
            TicketDto.this.cinemaFacilities = new ArrayList<>(cinemaFacilities);
            return this;
        }
    }
}
