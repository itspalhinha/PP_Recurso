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
import org.json.simple.JSONObject;

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
    
    //metodo JSON
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfParticipant", this.getClass().getSimpleName());
        jsonObject.put("name", super.getName());
        jsonObject.put("email", super.getEmail());
        jsonObject.put("contact", ((ImpContact) super.getContact()).toJsonObj());
        jsonObject.put("instituition", ((ImpInstituition) super.getInstituition()).toJsonObj());
        jsonObject.put("areaOfExpertise", areaOfExpertise);
        return jsonObject;
    }
    
    //metodos CSV
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("typeOfParticipant,name,email,contact,instituition,areaOfExpertise\n");

        // Append data
        csvBuilder.append(this.getClass().getSimpleName()).append(",");
        csvBuilder.append(super.getName()).append(",");
        csvBuilder.append(super.getEmail()).append(",");
        csvBuilder.append(((ImpContact) super.getContact()).toCSV()).append(",");
        csvBuilder.append(((ImpInstituition) super.getInstituition()).toCSV()).append(",");
        csvBuilder.append(areaOfExpertise).append("\n");

        return csvBuilder.toString();
    }

    
}
