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


public class ImpSubmission implements Submission{
    
    /*
    * Data da submissão do projeto
    */
    private LocalDateTime date;
    /*
    * Estudante que fez a submissão do projeto
    */
    private Student student;
    /*
    * Descrição da submissão
    */
    private String text;
    

    /*
    * Este é um dos métodos construtores para a classe Submission, e é usado quando queremos importar
    * de um arquivo JSON uma submissão que foi criada anteriormente
    *
    * @param date Data da submissão
    * @param student Estudante que realizou a submissão
    * @param text Descrição da submissão
    */
    public ImpSubmission(LocalDateTime date, Student student, String text) {
        this.date = LocalDateTime.now();
        this.student = student;
        this.text = text;
    }    

    /*
    * Este é um dos métodos construtores para a classe Submission
    * @param student Estudante que efetuou a submissão
    * @param text Descrição da submissão
    */
    public ImpSubmission(Student student, String text) {
        this.date = LocalDateTime.now();
        this.student = student;
        this.text = text;
    }
    
    /**
     * Obtém a data de submissão
     * @return Data de submissão
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Obtém o estudante que efetuou a submissão
     * @return Estudante que efetuou a submissão
     */
    @Override
    public Student getStudent() {
        return this.student;
    }

    /**
     * Obtém a descrição da submissão
     * @return Descrição da submissão
     */
    @Override
    public String getText() {
        return this.text;
    }

    /**
     * Este método compara a data da submissão atual com a data de outra submissão.
     * @return 0 se as datas forem iguais
     */
    @Override
    public int compareTo(Submission sbmsn) {
        if(this == sbmsn){
            return 0;
        }
        return date.compareTo(sbmsn.getDate());
    }
    
    /**
     * Este método converte a submissão em um objeto JSONObject
     * @return Um objeto JSON com as informações da submissão
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", text);
        jsonObject.put("date", date.toString());
        jsonObject.put("student", ((ImpStudent) student).toJsonObj());

        return jsonObject;
    }

    /**
     * Este método converte um objeto JSONObject em um objeto ImpSubmission
     * @param jsonObject Objeto JSON contendo a informação a ser recuperada
     * @return Uma submissão criada baseada na informação recuperada
     */
    public static ImpSubmission fromJsonObj(JSONObject jsonObject) {
        String text = (String) jsonObject.get("text");
        LocalDateTime date = LocalDateTime.parse((String) jsonObject.get("date"));
        Student student = (Student) ImpParticipant.fromJsonObj((JSONObject) jsonObject.get("student"));

        return new ImpSubmission(date, student, text);
    }
    
    
}
