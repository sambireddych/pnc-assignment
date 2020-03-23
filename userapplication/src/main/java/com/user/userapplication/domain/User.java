package com.user.userapplication.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private long id;

    @JsonIgnore
    @Column(name = "UniqueIdentification")
    private String uniqueIdentification;

    @Column(name = "FirstName",length = 50)
    private String firstName;

    @Column(name = "LastName",length = 20)
    private String lastName;

    @Column(name = "PhoneNumber")
    private String phoneNo;

/*    @Column(name = "Password")
    private String password;*/

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUniqueIdentification() {
        return uniqueIdentification;
    }

    public void setUniqueIdentification(String uniqueIdentification) {
        this.uniqueIdentification = uniqueIdentification;
    }

}
