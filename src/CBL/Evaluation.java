/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Student;

/**
 *
 * @author David Santos
 */
public class Evaluation {

    /**
     * Variable that defines the self evaluation and the heteroevaluation
     */
    private float selfEvaluation, heteroevaluation;
    /**
     * Variable that stores the Student being evaluated
     */
    private Student student;
    /**
     * Variable that stores the Facilitator that evaluated the Student
     */
    private Facilitator facilitator;

    
    /*
     * This is one of the constructor method for Evaluation
     */
    public Evaluation(float selfEvaluation, Student student) {
        this.selfEvaluation = selfEvaluation;
        this.student = student;
    }

    /*
     * This is one of the constructor method for Evaluation
     */
    public Evaluation(Student student) {
        this.student = student;
    }

    /*
     * This is one of the constructor method for Evaluation
     */
    public Evaluation(Student student, float heteroevaluation, Facilitator facilitator) {
        this.heteroevaluation = heteroevaluation;
        this.facilitator = facilitator;
        this.student = student;
    }

    /**
     * {@inheritDoc}
     */
    public float getSelfEvaluation() {
        return selfEvaluation;
    }

    /**
     * {@inheritDoc}
     */
    public void setSelfEvaluation(float selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    /**
     * {@inheritDoc}
     */
    public float getHeteroevaluation() {
        return heteroevaluation;
    }

    /**
     * {@inheritDoc}
     */
    public void setHeteroevaluation(float heteroevaluation) {
        this.heteroevaluation = heteroevaluation;
    }

    /**
     * {@inheritDoc}
     */
    public Student getStudent() {
        return student;
    }

    /**
     * {@inheritDoc}
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * {@inheritDoc}
     */
    public Facilitator getFacilitator() {
        return facilitator;
    }

    /**
     * {@inheritDoc}
     */
    public void setFacilitator(Facilitator facilitator) {
        this.facilitator = facilitator;
    }

}
