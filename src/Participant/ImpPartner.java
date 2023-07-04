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
import ma02_resources.participants.Partner;


public class ImpPartner extends ImpParticipant implements Partner{
    
    /*
     * Variable that defines VAT number
     */
    private String vat;
    /*
     * Variable that defines Partiner's website
     */
    private String webSite;

    /*
     * This is the constructor method for Partner
     *
     * @param vat Partners's VAT number
     * @param website Partner's website
     * @param name Partner's name
     * @param email Partner's email
     * @param email Partner's contact
     * @param email Partner's institution
     */
    public ImpPartner(String name, String email, Contact contact, Instituition instituition, String vat, String webSite) {
        super(name, email, contact, instituition);
        this.vat = vat;
        this.webSite = webSite;
    }   
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getVat() {
        return this.vat;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getWebsite() {
        return this.webSite;
        }
}
