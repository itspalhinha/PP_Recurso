/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Participant;

import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Partner;

/**
 *
 * @author ROGER
 */
public class ImpPartner extends ImpParticipant implements Partner{
    
    private String vat;
    private String webSite;

    public ImpPartner(String name, String email, Contact contact, Instituition instituition, String vat, String webSite) {
        super(name, email, contact, instituition);
        this.vat = vat;
        this.webSite = webSite;
    }   
    

    @Override
    public String getVat() {
        return this.vat;
    }
    

    @Override
    public String getWebsite() {
        return this.webSite;
    }
    
}
