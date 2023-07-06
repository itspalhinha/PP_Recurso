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


public class ImpFacilitator extends ImpParticipant implements Facilitator{

    /*
     * Área de especialização do Facilitador
     */
    private String areaOfExpertise;
    private int i;
    private String testing;

    /*
     * Este é o construtor para Facilitador
     * 
     * @param areaOfExpertise Área de especialização do Facilitador
     * @param name Nome do facilitador
     * @param email Email do facilitador
     * param contact Contrato do facilitador
     * @param instituition Instituição do facilitador
     */
    public ImpFacilitator(String areaOfExpertise, String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.areaOfExpertise = areaOfExpertise;
    }
        
    /**
     * Obtém a área de especialização do facilitador
     * @return Área de especialização do facilitador
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
