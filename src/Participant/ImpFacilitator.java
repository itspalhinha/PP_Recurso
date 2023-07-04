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
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafas
 */
public class ImpFacilitator extends ImpParticipant implements Facilitator{

    /*
     * Area of Facilitator's expertise
     */
    private String areaOfExpertise;
    private int i;
    private String testing;

    /*
     * This is the constructor method for Facilitator
     * 
     * @param areaOfExpertise Area of Facilitator's expertise
     * @param name Facilitator's name
     * @param email Facilitator's email
     * param contact Facilitator's contact
     * @param instituition Facilitator's institution
     */
    public ImpFacilitator(String areaOfExpertise, String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.areaOfExpertise = areaOfExpertise;
    }
        
    /**
     * {@inheritDoc}
     */
    @Override
    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
    
}
