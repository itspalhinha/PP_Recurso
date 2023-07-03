/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import java.time.LocalDateTime;
import ma02_resources.participants.Student;
import ma02_resources.project.Submission;

/**
 *
 * @author ROGER
 */
public class ImpSubmission implements Submission{
    
    private LocalDateTime date;
    private Student student;
    private String text;

    public ImpSubmission(LocalDateTime date, Student student, String text) {
        this.date = date;
        this.student = student;
        this.text = text;
    }
    

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public Student getStudent() {
        return this.student;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public int compareTo(Submission sbmsn) {
        if(this == sbmsn){
            return 0;
        }
        return this.date.compareTo(sbmsn.getDate());
    }
    
}
