package com.newrofactory.model;

import java.time.LocalDate;

public class Intern {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate arrival;
    private LocalDate formationOver;
    private long promotionId;

   
    public Intern(long id, String firstName, String lastName, LocalDate arrival, LocalDate formationOver, long promotionId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.arrival = arrival;
        this.formationOver = formationOver;
        this.promotionId = promotionId;
    }

   
    public Intern(String firstName, String lastName, LocalDate arrival, LocalDate formationOver, long promotionId) {
        this(0, firstName, lastName, arrival, formationOver, promotionId);
    }

   
    public long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getArrival() { return arrival; }
    public LocalDate getFormationOver() { return formationOver; }
    public long getPromotionId() { return promotionId; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + firstName + " " + lastName +
               " | Arrivée: " + arrival +
               " | Fin: " + (formationOver != null ? formationOver : "En cours") +
               " | Promo ID: " + promotionId;
    }
}