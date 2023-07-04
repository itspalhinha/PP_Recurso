/*
 * Nome: Rafael Filipe Silva Medina Coronel
 * Número: 8190348
 * Turma: LSIRCT1
 *
 * Nome: Roger Seiji Hernandez Nakauchi
 * Número: 8210005
 * Turma: LSIRCT1
 */
package Participant;


import java.util.Objects;
import ma02_resources.participants.Contact;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafas
 */
public class ImpContact implements Contact{
    /*
     * Variable that defines street
     */
    private String street;
    /*
     * Variable that defines city
     */
    private String city;
    /*
     * Variable that defines state
     */
    private String state;
    /*
     * Variable that defines zip code
     */
    private String zipCode;
    /*
     * Variable that defines country
     */
    private String country;
    /*
     * Variable that defines phone
     */
    private String phone;

    /*
     * This is the constructor for Contract
     *
     * @param street Street address
     * @param city City
     * @param state State
     * @param zipCode Zip Code
     * @param country Country
     * @param phone Phone
     */
    public ImpContact(String street, String city, String state, String zipCode, String country, String phone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.phone = phone;
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStreet() {
        return this.street;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getCity() {
        return this.city;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getState() {
        return this.state;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getZipCode() {
        return this.zipCode;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getCountry() {
        return this.country;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getPhone() {
        return this.phone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof Contact)){
            return false;
        }
        final Contact other = (Contact) obj;
        if (!this.street.equals(other.getStreet())) {
            return false;
        }
        if (!this.city.equals(other.getCity())) {
            return false;
        }
        if (!this.state.equals(other.getState())) {
            return false;
        }
        if (!this.zipCode.equals(other.getZipCode())) {
            return false;
        }
        if (!this.country.equals(other.getCountry())) {
            return false;
        }
        return this.phone.equals(other.getPhone());
    }
    
    
    
}
