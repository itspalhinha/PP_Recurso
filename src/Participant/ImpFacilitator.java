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
     * Define a área de especialização do facilitador
     */
    @Override
    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
    
    //metodo JSON
    /*
     * Converte o objeto Participant em uma representação JSON
     * @return Objeto JSON que representa o participante
     */
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
    /*
     * Converte o objeto Participant em uma representação CSV
     * @return Uma string em formato CSV que representao o participante
     */
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
