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

import Participant.ImpParticipant;
import Participant.ImpStudent;
import java.time.LocalDateTime;
import ma02_resources.participants.Student;
import ma02_resources.project.Submission;
import org.json.simple.JSONObject;

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
    
    /**
     * This method is used to export to a JSON file, information of a submission.
     * @return A Json Object with the submission's information
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", text);
        jsonObject.put("date", date.toString());
        jsonObject.put("student", ((ImpStudent) student).toJsonObj());

        return jsonObject;
    }

    /**
     * This method is used to import information about a submission from a JSON
     * file.
     * @param jsonObject The JSON Object containing the information to be retrived
     * @return A submission creted based on the information retrived
     */
    public static ImpSubmission fromJsonObj(JSONObject jsonObject) {
        String text = (String) jsonObject.get("text");
        LocalDateTime date = LocalDateTime.parse((String) jsonObject.get("date"));
        Student student = (Student) ImpParticipant.fromJsonObj((JSONObject) jsonObject.get("student"));

        return new ImpSubmission(date, student, text);
    }
    
    
}
