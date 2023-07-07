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
import ma02_resources.participants.Student;
import org.json.simple.JSONObject;


public class ImpStudent extends ImpParticipant implements Student{
    
    /*
     * Variável que aumenta o número de estudantes
     */
    private static int counter = 0;
    /*
     * Variável que define o número de estudantes
     */
    private int number;

    /*
     * Este é um método construtor para Student
     *
     * @param name Nome do estudante
     * @param email Email do estudante
     * @param email Contato do estudante
     * @param email Instituição do estudante
     */
    public ImpStudent( String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.number = ++counter;
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
        jsonObject.put("number", number);

        return jsonObject;
    }
    
    //metodos CSV
    /*
     * Converte o objeto Participant em uma representação CSV
     * @return Uma string no formato CSV que representa o participante
     */
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("typeOfParticipant,name,email,contact,instituition,number\n");

        // Append data
        csvBuilder.append(this.getClass().getSimpleName()).append(",");
        csvBuilder.append(super.getName()).append(",");
        csvBuilder.append(super.getEmail()).append(",");
        csvBuilder.append(((ImpContact) super.getContact()).toCSV()).append(",");
        csvBuilder.append(((ImpInstituition) super.getInstituition()).toCSV()).append(",");
        csvBuilder.append(number).append("\n");

        return csvBuilder.toString();
    }

    /*
     * Obtém o número de estudantes
     * @return Número de estudantes
     */
    @Override
    public int getNumber() {
        return this.number;
    }
    
}
