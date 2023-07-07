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
import ma02_resources.participants.InstituitionType;
import org.json.simple.JSONObject;


public class ImpInstituition implements Instituition {

    /*
     * Nome da instituição
     */
    private String name;
    /*
     * Email da instituição
     */
    private String email;
    /*
     * Website da instituição
     */
    private String website;
    /*
     * Descrição da instituição
     */
    private String description;
    /*
     * Define o tipo da instituição
     */
    private InstituitionType type;
    /*
     * Define o contato da instituição
     */
    private Contact contact;

    /*
     * Método construtor para Instituição
     *
     * @param name Nome da instituição
     * @param email Email da instituição
     * @param website Website da instituição
     * @param description Descrição da instituição
     * @param contact Contato da instituição
     * @param type Tipo da instituição
     */
    public ImpInstituition(String name, String email, String website, String description, Contact contact, InstituitionType type) {
        this.name = name;
        this.email = email;
        this.website = website;
        this.description = description;
        this.contact = contact;
        this.type = type;
    }

    /**
     * Obtém o nome da instituição
     * @return Nome da instituição
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Obtém o email da instituição
     * @return Email da instituição
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Obtém o website da instituição
     * @return Website da instituição
     */
    @Override
    public String getWebsite() {
        return website;
    }

    /**
     * Obtém a descrição da instituição
     * @return Descrição da instituição
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Obtém o contato da instituição
     * @return Contato da instituição
     */
    @Override
    public Contact getContact() {
        return contact;
    }

    /**
     * Obtém o tipo da instituição
     * @return Tipo da instituição
     */
    @Override
    public InstituitionType getType() {
        return type;
    }

    /**
     * Obtém o website da instituição
     * @return Website da instituição
     */
    @Override
    public void setWebsite(String string) {
        this.website = string;
    }

    /**
     * Define a descrição da instituição
     */
    @Override
    public void setDescription(String string) {
        this.description = string;
    }

    /**
     * Define o contato da instituição
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    /**
     * Define o tipo da instituição
     */
    @Override
    public void setType(InstituitionType it) {
        this.type = it;
    }

    //metodos JSON
    /*
     * Converte o objeto Instituition em uma representação JSON
     * @return Um objeto JSON que represetna a instituição
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("website", website);
        jsonObject.put("description", description);
        jsonObject.put("contact", ((ImpContact) contact).toJsonObj());
        
        //Error if its InstituitionType.UNIVERSITY, the toString given is "universitary", it causes problems when importing
        if (this.type == InstituitionType.UNIVERSITY){
            jsonObject.put("type", "University");
        } else {
            jsonObject.put("type", type.toString());
        }

        return jsonObject;
    }

    /*
     * Converte um objeto JSON em um objeto Instituition
     * @param jsonObject Objeto JSON contendo os dados da instituição
     * @return Objeto Instituition
     */
    public static Instituition fromJsonObj(JSONObject jsonObject) {

        String name = (String) jsonObject.get("name");
        String email = (String) jsonObject.get("email");
        String website = (String) jsonObject.get("website");
        String description = (String) jsonObject.get("description");
        
        JSONObject contactJson = (JSONObject) jsonObject.get("contact");
        Contact contact = ImpContact.fromJsonObj(contactJson);
        
        InstituitionType type = InstituitionType.valueOf(((String) jsonObject.get("type")).toUpperCase());
        
        Instituition instituition = new ImpInstituition(name, email, website, description, contact, type);
        
        return instituition;
    }
    
    //metodos CSV
    /*
     * Converte o objeto Instituition em uma representação CSV
     * @return Uma string no formato CSV que representa a instituição
     */
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("name,email,website,description,contact,type\n");

        // Append data
        csvBuilder.append(name).append(",");
        csvBuilder.append(email).append(",");
        csvBuilder.append(website).append(",");
        csvBuilder.append(description).append(",");
        csvBuilder.append(((ImpContact) contact).toCSV()).append(",");

        if (type == InstituitionType.UNIVERSITY) {
            csvBuilder.append("University").append(",");
        } else {
            csvBuilder.append(type.toString()).append(",");
        }

        return csvBuilder.toString();
    }
    
    /*
     * Converte uma string CSV em um objeto Instituition
     * @param csvData String CSV contendo os dados da instituição
     * @return Objeto Instituition
     */
    public static Instituition fromCSV(String csvData) {
        String[] fields = csvData.split(",");

        String name = fields[0];
        String email = fields[1];
        String website = fields[2];
        String description = fields[3];
        String contactData = fields[4];
        String typeData = fields[5];

        Contact contact = ImpContact.fromCSV(contactData);

        InstituitionType type;
        if (typeData.equalsIgnoreCase("University")) {
            type = InstituitionType.UNIVERSITY;
        } else {
            type = InstituitionType.valueOf(typeData.toUpperCase());
        }

        return new ImpInstituition(name, email, website, description, contact, type);
    }


    
    /*
     * Verifica se um objeto é igual a esta instituição
     * @param obj Objeto a ser comparado
     * @return true se os objetos são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Instituition)) {
            return false;
        }
        final Instituition other = (Instituition) obj;
        return this.name.equals(other.getName());
    }

    

}
