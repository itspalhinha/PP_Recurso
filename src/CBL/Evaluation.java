/*
 * Nome: Rafael Filipe Silva Medina Coronel
 * Número: 8190348
 * Turma: LSIRCT1
 *
 * Nome: Roger Seiji Hernandez Nakauchi
 * Número: 8210005
 * Turma: LSIRCT1
 */
package CBL;

import Participant.ImpContact;
import Participant.ImpFacilitator;
import Participant.ImpInstituition;
import Participant.ImpParticipant;
import Participant.ImpPartner;
import Participant.ImpStudent;
import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Student;
import org.json.simple.JSONObject;

public class Evaluation {

    /**
     * Variáveis que definem a autoavaliação e a heteroavaliação
     */
    private float selfEvaluation, heteroEvaluation;
    /**
     * Variável que armazena o aluno que está sendo avaliado
     */
    private Student student;
    /**
     * Variável que armazena o facilitador que avaliou o aluno
     */
    private Facilitator facilitator;

    /*
     * Este é um dos construtores do objeto Avaliacao
     */
    public Evaluation(float selfEvaluation, Student student) {
        this.selfEvaluation = selfEvaluation;
        this.student = student;
    }

    /*
     * Este é um dos construtores do objeto Avaliacao
     */
    public Evaluation(Student student) {
        this.student = student;
    }

    /*
     * Este é um dos construtores do objeto Avaliacao
     */
    public Evaluation(Student student, float heteroevaluation, Facilitator facilitator) {
        this.heteroEvaluation = heteroevaluation;
        this.facilitator = facilitator;
        this.student = student;
    }

    public Evaluation(float selfEvaluation, float heteroevaluation, Student student, Facilitator facilitator) {
        this.selfEvaluation = selfEvaluation;
        this.heteroEvaluation = heteroevaluation;
        this.student = student;
        this.facilitator = facilitator;
    }

    /**
     * Retorna a autoavaliação do aluno
     */
    public float getSelfEvaluation() {
        return selfEvaluation;
    }

    /**
     * Define a autoavaliação do aluno
     */
    public void setSelfEvaluation(float selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    /**
     * Retorna a heteroavaliação do aluno
     */
    public float getHeteroevaluation() {
        return heteroEvaluation;
    }

    /**
     * Define a heteroavaliação do aluno
     */
    public void setHeteroevaluation(float heteroevaluation) {
        this.heteroEvaluation = heteroevaluation;
    }

    /**
     * Retorna o aluno sendo avaliado
     *
     * @return o aluno
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Define o aluno sendo avaliado
     *
     * @param student o aluno
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Retorna o facilitador que avaliou o aluno
     *
     * @return facilitador
     */
    public Facilitator getFacilitator() {
        return facilitator;
    }

    /**
     * Define o facilitador que avaliou o aluno
     *
     * @param facilitator
     */
    public void setFacilitator(Facilitator facilitator) {
        this.facilitator = facilitator;
    }

    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();

        // Adicionar valores ao JSON
        try {
            jsonObject.put("selfEvaluation", selfEvaluation);
        } catch (NullPointerException e) {

        }
        try {

            jsonObject.put("heteroEvaluation", heteroEvaluation);

            if (facilitator != null) {
                JSONObject facilitatorJson = ((ImpFacilitator) facilitator).toJsonObj();
                jsonObject.put("facilitator", facilitatorJson);
            }

        } catch (NullPointerException e) {

        }

        JSONObject studentJson = ((ImpStudent) student).toJsonObj();

        jsonObject.put(
                "student", studentJson);

        return jsonObject;
    }

    /*
     * Converte um objeto JSON em um objeto Evaluation
     * @param jsonObject Objeto JSON contendo os dados Evaluation
     * @return Objeto Evaluation
     */
    public static Evaluation fromJsonObj(JSONObject jsonObject) {
        float selfEvaluation, heteroEvaluation;
        Facilitator facilitator;
        Student student;

        try {
            selfEvaluation = (float) jsonObject.get("selfEvaluation");
        } catch (NullPointerException e) {
            selfEvaluation = -1f;
        }
        try {
            heteroEvaluation = (float) jsonObject.get("heteroEvaluation");
            try {
                JSONObject facilitatorJson = (JSONObject) jsonObject.get("facilitator");
                facilitator = (Facilitator) ImpParticipant.fromJsonObj(facilitatorJson);
            } catch (NullPointerException e) {
                facilitator = null;
            }
        } catch (NullPointerException e) {
            heteroEvaluation = -1f;
            facilitator = null;
        }
        try {
            JSONObject studentObj = (JSONObject) jsonObject.get("student");
            student = (Student) ImpParticipant.fromJsonObj(studentObj);
        } catch (NullPointerException e) {
            student = null;
        }

        if (heteroEvaluation == -1f) {
            return new Evaluation(selfEvaluation, student);
        } else if (selfEvaluation == -1f) {
            return new Evaluation(student, heteroEvaluation, facilitator);
        }

        return new Evaluation(selfEvaluation, heteroEvaluation, student, facilitator);

    }

}
