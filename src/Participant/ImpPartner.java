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
     * Define o número VAT do parceiro
     */
    private String vat;
    /*
     * Define o website do parceiro
     */
    private String webSite;

    /*
     * Método construtor para Partner
     *
     * @param vat Número VAT do parceiro
     * @param website Website do parceiro
     * @param name Nome do parceiro
     * @param email Email do parceiro
     * @param email Contato do parceiro
     * @param email Instituição do parceiro
     */
    public ImpPartner(String vat, String webSite, String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.vat = vat;
        this.webSite = webSite;
    }   
    
    
    /**
     * Obtém o número VAT do parceiro
     * @return Número VAT do parceiro
     */
    @Override
    public String getVat() {
        return this.vat;
    }
    
    /**
     * Obtém o website do parceiro
     * @return Website do parceiro
     */
    @Override
    public String getWebsite() {
        return this.webSite;
    }
    
    //metodos JSON
    /*
     * Converte o objeto Participant em uma representação JSON
     * @return Um objeto JSON que representa o participante
     */
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
    /*
     * Converte o objeto Participant em uma representação CSV
     * @return Uma string no formato CSV que representa o participante
     */
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
