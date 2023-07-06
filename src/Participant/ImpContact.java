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
