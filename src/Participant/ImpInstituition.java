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

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.InstituitionType;


public class ImpInstituition implements Instituition {

    /*
     * Variable that defines name
     */
    private String name;
    /*
     * Variable that defines email
     */
    private String email;
    /*
     * Variable that defines website
     */
    private String website;
    /*
     * Variable that defines description
     */
    private String description;
    /*
     * Variable that defines institution type
     */
    private InstituitionType type;
    /*
     * Variable that defines institution's contact
     */
    private Contact contact;

    /*
     * This is the constructor for Institution
     *
     * @param name Institution's name
     * @param email Institution's email
     * @param website Institution's website
     * @param description Institution's description
     * @param contact Institution's contact
     * @param type Institution's type
     */
    public ImpInstituition(String name, String email, String website, String description, Contact contact, InstituitionType type) {
        this.name = name;
        this.email = email;
        this.website = website;
        this.description = description;
        this.contact = contact;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWebsite() {
        return website;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact getContact() {
        return contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InstituitionType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWebsite(String string) {
        this.website = string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(String string) {
        this.description = string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    /**
     * {@inheritDoc}
     */
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
