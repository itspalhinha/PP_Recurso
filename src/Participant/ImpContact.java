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
import org.json.simple.JSONObject;


public class ImpContact implements Contact{
    /*
     * Define a rua
     */
    private String street;
    /*
     * Define a cidade
     */
    private String city;
    /*
     * Define o estado
     */
    private String state;
    /*
     * Define o código postal
     */
    private String zipCode;
    /*
     * Define o país
     */
    private String country;
    /*
     * Define o telefone
     */
    private String phone;

    /*
     * Este é o construtor para Contrato
     *
     * @param street Rua
     * @param city Cidade
     * @param state Estado
     * @param zipCode Código postal
     * @param country País
     * @param phone Telefone
     */
    public ImpContact(String street, String city, String state, String zipCode, String country, String phone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.phone = phone;
    }
    

    /**
     * Obtém a rua do contrato
     * @return Rua do contrato
     */
    @Override
    public String getStreet() {
        return this.street;
    }
    /**
     * Obtém a cidade do contrato
     * @return Cidade do contrato
     */
    @Override
    public String getCity() {
        return this.city;
    }
    /**
     * Obtém o estado do contrato
     * @return Estado do contrato
     */
    @Override
    public String getState() {
        return this.state;
    }
    /**
     * Obtém o código postal do contrato
     * @return Código postal do contrato
     */
    @Override
    public String getZipCode() {
        return this.zipCode;
    }
    /**
     * Obtém o país do contrato
     * @return País do contrato
     */
    @Override
    public String getCountry() {
        return this.country;
    }
    /**
     * Obtém o telefone do contrato
     * @return Telefone do contrato
     */
    @Override
    public String getPhone() {
        return this.phone;
    }
    
    //metodos JSON
    /*
     * Converte o objeto Contact em um JSONObject
     * @return Objeto JSONObject que representa o contato
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("street", street);
        jsonObject.put("city", city);
        jsonObject.put("state", state);
        jsonObject.put("zipCode", zipCode);
        jsonObject.put("country", country);
        jsonObject.put("phone", phone);

        return jsonObject;
    }

    /*
     * Converte um JSONObject em um objeto Contact
     * @param jsonObject Objeto JSONObject contendo os dados do contato
     * @return o objeto Contact criado a partiro do JSONObject
     */
    public static Contact fromJsonObj(JSONObject jsonObject) {

        String street = (String) jsonObject.get("street");
        String city = (String) jsonObject.get("city");
        String state = (String) jsonObject.get("state");
        String zipCode = (String) jsonObject.get("zipCode");
        String country = (String) jsonObject.get("country");
        String phone = (String) jsonObject.get("phone");

        Contact contact = new ImpContact(street, city, state, zipCode, country, phone);

        return contact;
    }

    //metodos CSV
    /*
     * Converte o objeto Contact em uma representação CSV
     * @return uma string no formato CSV que representa o contato
     */
    public String toCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        // Append column headers
        csvBuilder.append("street,city,state,zipCode,country,phone\n");

        // Append data
        csvBuilder.append(street).append(",");
        csvBuilder.append(city).append(",");
        csvBuilder.append(state).append(",");
        csvBuilder.append(zipCode).append(",");
        csvBuilder.append(country).append(",");
        csvBuilder.append(phone).append("\n");

        return csvBuilder.toString();
    }
    
    /*
     * Converte uma representação CSV em um objeto Contact
     * @param csvData String contendo os dados CSV contato
     * @return Objeto Contact criado a partir dos dados CSV
     */
    public static Contact fromCSV(String csvData) {
        String[] fields = csvData.split(",");

        String street = fields[0];
        String city = fields[1];
        String state = fields[2];
        String zipCode = fields[3];
        String country = fields[4];
        String phone = fields[5];

        Contact contact = new ImpContact(street, city, state, zipCode, country, phone);

        return contact;
    }


    /**
     * Esse método verifica se um objeto é igual ao contrato atual
     * @param obj Objeto a ser comparado
     * @return true se o objeto for igual, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof Contact)){
            return false;
        }
        final Contact other = (Contact) obj;
        if (!this.street.equals(other.getStreet())) {
            return false;
        }
        if (!this.city.equals(other.getCity())) {
            return false;
        }
        if (!this.state.equals(other.getState())) {
            return false;
        }
        if (!this.zipCode.equals(other.getZipCode())) {
            return false;
        }
        if (!this.country.equals(other.getCountry())) {
            return false;
        }
        return this.phone.equals(other.getPhone());
    }

    
    
    
    
}
