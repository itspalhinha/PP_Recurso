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


import java.util.Objects;
import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;
import org.json.simple.JSONObject;


public abstract class ImpParticipant implements Participant{
    
    /*
     * Define o nome do participante
     */
    private String name;
    /*
     * Define o email do participante
     */
    private String email;
    /*
     * Define o contato do participante
     */
    private Contact contact;
    /*
     * Define uma instituição
     */
    private Instituition instituition;

    /*
     * Método construtor para a classe Participant
     *
     * @param name Nome do participante
     * @param email Email do participante
     * @param contact Contato do participante
     * @param instituition Instituição do participante
     */
    public ImpParticipant(String name, String email, Contact contact, Instituition instituition) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.instituition = instituition;
    }
    
    /**
     * Obtém o nome do participante
     * @return Nome do participante
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Obtém o email do participante
     * @return Email do participante
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * Obtém o contato do participante
     * @return Contato do participante
     */
    @Override
    public Contact getContact() {
        return this.contact;
    }

    /**
     * Obtém a instituição do participante
     * @return Instituição do participante
     */
    @Override
    public Instituition getInstituition() {
        return this.instituition;
    }

    /**
     * Define a instituição do participante
     */
    @Override
    public void setInstituition(Instituition instn) {
        this.instituition = instn;
    }

    /**
     * Define o contato do participante
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    //metodos JSON
    /*
     * Converte o objeto Participant em uma representação JSON
     * @return Objeto JSON que representa o participante
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfParticiant", this.getClass());
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("contact", ((ImpContact) contact).toJsonObj());
        jsonObject.put("instituition", ((ImpInstituition) instituition).toJsonObj());

        return jsonObject;
    }

    /*
     * Converte um objeto JSON em um objeto Participant
     * @param jsonObject Objeto JSON contendo os dados do participante
     * @return Objeto Participant
     */
    public static Participant fromJsonObj(JSONObject jsonObject) {

        String name = (String) jsonObject.get("name");
        String email = (String) jsonObject.get("email");

        JSONObject contactJson = (JSONObject) jsonObject.get("contact");
        Contact contact = ImpContact.fromJsonObj(contactJson);

        JSONObject instituitionJson = (JSONObject) jsonObject.get("instituition");
        Instituition instituition = ImpInstituition.fromJsonObj(instituitionJson);

        String type = (String) jsonObject.get("typeOfParticipant");


        if (type.equals("ImpStudent")) {
            return new ImpStudent(name, email, contact, instituition);

        } else if (type.equals("FacilitatorImp")) {
            String areaOfExpertise = (String) jsonObject.get("areaOfExpertise");
            return new ImpFacilitator(areaOfExpertise, name, email, contact, instituition);

        } else if (type.equals("PartnerImp")) {
            String vat = (String) jsonObject.get("vat");
            String website = (String) jsonObject.get("website");
            return new ImpPartner(vat, website, name, email, contact, instituition);

        } else {
            return new ImpParticipant(name, email, contact, instituition) {};
        }
    }
    
    //metodos CSV
    /*
     * Converte o objeto Participant em uma representação CSV
     * @return Uma string no formato CSV que representa o participante
     */
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("typeOfParticipant,name,email,contact,instituition\n");

        // Append data
        csvBuilder.append(this.getClass().getSimpleName()).append(",");
        csvBuilder.append(name).append(",");
        csvBuilder.append(email).append(",");
        csvBuilder.append(((ImpContact) contact).toCSV()).append(",");
        csvBuilder.append(((ImpInstituition) instituition).toCSV()).append("\n");

        return csvBuilder.toString();
    }
    
    /*
     * Converte uma string CSV em um objeto Participant
     * @param csvData A string CSV contendo os dados do participante
     * @return Objeto Participant
     */
    public static Participant fromCSV(String csvData) {
        String[] fields = csvData.split(",");

        String type = fields[0];
        String name = fields[1];
        String email = fields[2];
        String contactData = fields[3];
        String instituitionData = fields[4];

        Contact contact = ImpContact.fromCSV(contactData);
        Instituition instituition = ImpInstituition.fromCSV(instituitionData);

        if (type.equals("StudentImp")) {
            return new ImpStudent(name, email, contact, instituition);
        } else if (type.equals("FacilitatorImp")) {
            String areaOfExpertise = fields[5];
            return new ImpFacilitator(areaOfExpertise, name, email, contact, instituition);
        } else if (type.equals("PartnerImp")) {
            String vat = fields[5];
            String website = fields[6];
            return new ImpPartner(vat, website, name, email, contact, instituition);
        } else {
            return new ImpParticipant(name, email, contact, instituition) {};
        }
    }


    /*
     * Verifica se o objeto atual é igual ao objeto fornecido
     * @param obj Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Participant)) {
            return false;
        }
        final Participant other = (Participant) obj;
        return this.email.equals(other.getEmail());
    }
    
    
    
}
