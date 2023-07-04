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

    public Evaluation(float selfEvaluation, Student student) {
        this.selfEvaluation = selfEvaluation;
        this.student = student;
    }

    public Evaluation(Student student, float heteroevaluation, Facilitator facilitator) {
        this.heteroevaluation = heteroevaluation;
        this.facilitator = facilitator;
        this.student = student;
    }

    public float getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(float selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public float getHeteroevaluation() {
        return heteroevaluation;
    }

    public void setHeteroevaluation(float heteroevaluation) {
        this.heteroevaluation = heteroevaluation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Facilitator getFacilitator() {
        return facilitator;
    }

    public void setFacilitator(Facilitator facilitator) {
        this.facilitator = facilitator;
    }
    
    
}
