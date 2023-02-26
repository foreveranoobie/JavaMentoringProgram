package com.epam.storozhuk.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cinema.users")
public class User {

    @Id
    @GeneratedValue(generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "cinema.users_id_seq", allocationSize = 1)
    private long id;
    @Column
    private String username;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column
    private String email;
    @Column
    private int age;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column
    private float balance;

    public static UserBuilder builder() {
        return new User().new UserBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
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

        public UserBuilder balance(float balance) {
            User.this.balance = balance;
            return this;
        }

        public User build() {
            return User.this;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", balance=" + balance +
                '}';
    }
}
