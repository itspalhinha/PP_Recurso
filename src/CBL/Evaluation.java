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

import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Student;


public class Evaluation {

    /**
     * Variáveis que definem a autoavaliação e a heteroavaliação
     */
    private float selfEvaluation, heteroevaluation;
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
        this.heteroevaluation = heteroevaluation;
        this.facilitator = facilitator;
        this.student = student;
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
        return heteroevaluation;
    }

    /**
     * Define a heteroavaliação do aluno
     */
    public void setHeteroevaluation(float heteroevaluation) {
        this.heteroevaluation = heteroevaluation;
    }

    /**
     * Retorna o aluno sendo avaliado
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Define o aluno sendo avaliado
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Retorna o facilitador que avaliou o aluno
     */
    public Facilitator getFacilitator() {
        return facilitator;
    }

    /**
     * Define o facilitador que avaliou o aluno
     */
    public void setFacilitator(Facilitator facilitator) {
        this.facilitator = facilitator;
    }

}
