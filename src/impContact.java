
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
    
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;

    @Override
    public String getStreet() {
        return this.street;
    }
    @Override
    public String getCity() {
        return this.city;
    }
    @Override
    public String getState() {
        return this.state;
    }
    @Override
    public String getZipCode() {
        return this.zipCode;
    }
    @Override
    public String getCountry() {
        return this.country;
    }
    @Override
    public String getPhone() {
        return this.phone;
    }


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
