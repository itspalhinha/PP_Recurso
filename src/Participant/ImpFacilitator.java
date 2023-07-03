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

    private String areaOfExpertise;
    private int i;

    public ImpFacilitator(String areaOfExpertise, String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.areaOfExpertise = areaOfExpertise;
    }
        
    @Override
    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    @Override
    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
    
}
