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

/**
 *
 * @author rafas
 */
public abstract class ImpParticipant implements Participant{
    
    /*
     * Variable that defines name
     */
    private String name;
    /*
     * Variable that defines email
     */
    private String email;
    /*
     * Variable that defines contact
     */
    private Contact contact;
    /*
     * Variable that defines an Institution
     */
    private Instituition instituition;

    /*
     * This is the constructor method for Participant
     *
     * @param name Participant's name
     * @param email Participant's email
     * @param contact Participant's contact
     * @param instituition Participant's institution
     */
    public ImpParticipant(String name, String email, Contact contact, Instituition instituition) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.instituition = instituition;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact getContact() {
        return this.contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instituition getInstituition() {
        return this.instituition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstituition(Instituition instn) {
        this.instituition = instn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContact(Contact cntct) {
        this.contact = cntct;
    }

    //metodos JSON
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("typeOfParticiant", this.getClass().getSimpleName());
        jsonObject.put("name", name);
        jsonObject.put("email", email);
        jsonObject.put("contact", ((ImpContact) contact).toJsonObj());
        jsonObject.put("instituition", ((ImpInstituition) instituition).toJsonObj());

        return jsonObject;
    }

    public static Participant fromJsonObj(JSONObject jsonObject) {

        String name = (String) jsonObject.get("name");
        String email = (String) jsonObject.get("email");

        JSONObject contactJson = (JSONObject) jsonObject.get("contact");
        Contact contact = ImpContact.fromJsonObj(contactJson);

        JSONObject instituitionJson = (JSONObject) jsonObject.get("instituition");
        Instituition instituition = ImpInstituition.fromJsonObj(instituitionJson);

        String type = (String) jsonObject.get("typeOfParticipant");

        if (type.equals("StudentImp")) {
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
