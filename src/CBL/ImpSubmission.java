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

import java.time.LocalDateTime;
import ma02_resources.participants.Student;
import ma02_resources.project.Submission;

/**
 *
 * @author ROGER
 */
public class ImpSubmission implements Submission{
    
    /*
    * Variable that defines date of submission
    */
    private LocalDateTime date;
    /*
    * Students that submitted a project
    */
    private Student student;
    /*
    * Description of the submission
    */
    private String text;

    

    /*
    * This is one of the constructor methods for Submission. It is used
    * when we want to import form the JSON file a submission that was
    * created previously
    *
    * @param date The submissions date
    * @param student The student that submitted the project
    * @param text The description of the project
    */
    public ImpSubmission(LocalDateTime date, Student student, String text) {
        this.date = LocalDateTime.now();
        this.student = student;
        this.text = text;
    }    
    
    /*
    * This is the constructor method for Submission
    *
    * @param student The student that submitted a project
    * @param text The description of the submit
    */
    public ImpSubmission(Student student, String text) {
        this.date = date;
        this.student = student;
        this.text = text;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Student getStudent() {
        return this.student;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return this.text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Submission sbmsn) {
        if(this == sbmsn){
            return 0;
        }
        return date.compareTo(sbmsn.getDate());
    }
    
}
