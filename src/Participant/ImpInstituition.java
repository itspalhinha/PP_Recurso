/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Participant;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.InstituitionType;

/**
 *
 * @author David Santos
 */
public class ImpInstituition implements Instituition {

    private String name, email, website, description;
    private InstituitionType type;
    private Contact contact;

    public ImpInstituition(String name, String email, String website, String description, Contact contact, InstituitionType type) {
        this.name = name;
        this.email = email;
        this.website = website;
        this.description = description;
        this.contact = contact;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Contact getContact() {
        return contact;
    }

    @Override
    public InstituitionType getType() {
        return type;
    }

    @Override
    public void setWebsite(String string) {
        this.website = string;
    }

    @Override
    public void setDescription(String string) {
        this.description = string;
    }

    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    @Override
    public void setType(InstituitionType it) {
        this.type = it;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Instituition)) {
            return false;
        }
        final Instituition other = (Instituition) obj;
        return this.name.equals(other.getName());
    }

}
