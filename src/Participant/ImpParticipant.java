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
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;

/**
 *
 * @author rafas
 */
public abstract class ImpParticipant implements Participant{
    
    /*
     * Variable that defines name
     */
    private String name;
    /*
     * Variable that defines email
     */
    private String email;
    /*
     * Variable that defines contact
     */
    private Contact contact;
    /*
     * Variable that defines an Institution
     */
    private Instituition instituition;

    /*
     * This is the constructor method for Participant
     *
     * @param name Participant's name
     * @param email Participant's email
     * @param contact Participant's contact
     * @param instituition Participant's institution
     */
    public ImpParticipant(String name, String email, Contact contact, Instituition instituition) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.instituition = instituition;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact getContact() {
        return this.contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instituition getInstituition() {
        return this.instituition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstituition(Instituition instn) {
        this.instituition = instn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Participant)) {
            return false;
        }
        final Participant other = (Participant) obj;
        return this.email.equals(other.getEmail());
    }
    
    
    
}
