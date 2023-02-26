package com.epam.storozhuk.entity;

import java.time.LocalDateTime;

public class User implements Entity {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private int age;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static UserBuilder builder() {
        return new User().new UserBuilder();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
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

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public class UserBuilder {

        public UserBuilder id(long id) {
            User.this.id = id;
            return this;
        }

        public UserBuilder age(int age) {
            User.this.age = age;
            return this;
        }

        public UserBuilder username(String username) {
            User.this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            User.this.email = email;
            return this;
        }

        public UserBuilder firstname(String firstname) {
            User.this.firstname = firstname;
            return this;
        }

        public UserBuilder lastname(String lastname) {
            User.this.lastname = lastname;
            return this;
        }

        public UserBuilder createDate(LocalDateTime createDate) {
            User.this.createDate = createDate;
            return this;
        }

        public UserBuilder updateDate(LocalDateTime updateDate) {
            User.this.updateDate = updateDate;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
