package com.budget.app.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="users")
@Component
public class User
{

    // Database Setup
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

    @Column(name="email_address")
    private String emailAddress;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;


    // Connections
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    //@OneToOne(mappedBy = "user")
    private List<Budget> budgets = new ArrayList<Budget>();


    // Constructors
    public User() {}
    public User(int id, String firstName, String lastName, String emailAddress, List<Budget> budgets)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.budgets = budgets;
    }


    // Getters & Setters
    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setFirstName(String firstName){this.firstName = firstName;}

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Budget> getBudgets() {return budgets;}

    public void setBudgets(List<Budget> budgets) {this.budgets = budgets;}
}
