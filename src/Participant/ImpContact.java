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
import org.json.simple.JSONObject;

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
    
    //metodos JSON
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("street", street);
        jsonObject.put("city", city);
        jsonObject.put("state", state);
        jsonObject.put("zipCode", zipCode);
        jsonObject.put("country", country);
        jsonObject.put("phone", phone);

        return jsonObject;
    }

    public static Contact fromJsonObj(JSONObject jsonObject) {

        String street = (String) jsonObject.get("street");
        String city = (String) jsonObject.get("city");
        String state = (String) jsonObject.get("state");
        String zipCode = (String) jsonObject.get("zipCode");
        String country = (String) jsonObject.get("country");
        String phone = (String) jsonObject.get("phone");

        Contact contact = new ImpContact(street, city, state, zipCode, country, phone);

        return contact;
    }

    //metodos CSV
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("street,city,state,zipCode,country,phone\n");

        // Append data
        csvBuilder.append(street).append(",");
        csvBuilder.append(city).append(",");
        csvBuilder.append(state).append(",");
        csvBuilder.append(zipCode).append(",");
        csvBuilder.append(country).append(",");
        csvBuilder.append(phone).append("\n");

        return csvBuilder.toString();
    }
    public static Contact fromCSV(String csvData) {
        String[] fields = csvData.split(",");

        String street = fields[0];
        String city = fields[1];
        String state = fields[2];
        String zipCode = fields[3];
        String country = fields[4];
        String phone = fields[5];

        Contact contact = new ImpContact(street, city, state, zipCode, country, phone);

        return contact;
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
