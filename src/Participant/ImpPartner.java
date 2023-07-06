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
import org.json.simple.JSONObject;


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
    public ImpPartner(String vat, String webSite, String name, String email, Contact contact, Instituition instituition) {
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
    
    //metodos JSON
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfParticipant", this.getClass().getSimpleName());
        jsonObject.put("name", super.getName());
        jsonObject.put("email", super.getEmail());
        jsonObject.put("contact", ((ImpContact) super.getContact()).toJsonObj());
        jsonObject.put("instituition", ((ImpInstituition) super.getInstituition()).toJsonObj());
        jsonObject.put("vat", vat);
        jsonObject.put("website", webSite);
        return jsonObject;
    }
    
    //metodos CSV
    public String toCSV() {
    StringBuilder csvBuilder = new StringBuilder();

    csvBuilder.append("typeOfParticipant,name,email,contact,instituition,vat,website\n");

    csvBuilder.append(this.getClass().getSimpleName()).append(",");
    csvBuilder.append(super.getName()).append(",");
    csvBuilder.append(super.getEmail()).append(",");
    csvBuilder.append(((ImpContact) super.getContact()).toCSV()).append(",");
    csvBuilder.append(((ImpInstituition) super.getInstituition()).toCSV()).append(",");
    csvBuilder.append(vat).append(",");
    csvBuilder.append(webSite).append("\n");

    return csvBuilder.toString();
}

    
}
