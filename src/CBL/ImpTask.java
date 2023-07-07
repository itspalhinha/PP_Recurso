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

import java.time.LocalDate;
import java.util.Objects;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ImpTask implements Task{
    
    /*
    * Define o início da tarefa
    */
    private LocalDate start;
    /*
    * Define o fim da tarefa
    */
    private LocalDate end;
    /*
    * Define a duração da tarefa
    */
    private int duration;
    /*
    * Define o título da tarefa
    */
    private String title;
    /*
    * Define a descrição da tarefa
    */
    private String description;
    /*
    * Array que armazena as submissões
    */
    private Submission[] submission;
    /*
    * Define o número de submissões da tarefa
    */
    private int numberOfSubmissions;
    

    /*
     * Esse é um dos métodos construtores para a tarefa
     *
     * @param title Título da tarefa
     * @param description Descrição da tarefa
     * @param start Data de início da tarefa
     * @param end Data de término da tarefa
     * @para duration Duração da tarefa
     */
    public ImpTask(String title, String description, LocalDate start, LocalDate end, int duration) {

        this.start = start;
        this.end = end;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }
    
    
    /**
     * Obtém a data de início da tarefa
     * @return Data de início da tarefa
     */
    @Override
    public LocalDate getStart() {
        return start;
    }

    /**
     * Obtém data de término da tarefa
     * @return Data de término da tarefa
     */
    @Override
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Obtém a duração da tarefa
     * @return Duração da tarefa
     */
    @Override
    public int getDuration() {
        return this.duration;
    }
    
    /**
     * Obtém o título da tarefa
     * @return Título da tarefa
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Obtém a descrição da tarefa
     * @return Descrição da tarefa
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Obtém o array com as submissões da tarefa
     * @return Array com as submissões da tarefa
     */
    @Override
    public Submission[] getSubmissions() {
        Submission tempSubmission[] = new Submission[numberOfSubmissions];
        for(int i = 0; i < numberOfSubmissions; i++){
            tempSubmission[i] = submission[i];
        }
        return tempSubmission;
    }

    /**
     * Obtém o número de submissões da tarefa
     * @return Número de submissões da tarefa
     */
    @Override
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }
    
    /*
    * Este método verifica se uma submissão existe na lista
    *
    * @param sbmsn SUbmissão a ser verificada
    * @return true se a submissão existir, false caso contrário
    */
    private boolean existsSubmission(Submission sbmsn){
        for(int i = 0; i < this.submission.length; i++){
            if(submission[i] != null && submission[i].equals(sbmsn)){
                return true;
            }
        }
        return false;
    }
    
    /*
    * Esse método adiciona mais espaço na lista de submissões
    */
    private void realloc(){
        Submission[] tempSubm = new Submission[this.submission.length*2];
        for(int i = 0; i < this.submission.length; i++){
            tempSubm[i] = this.submission[i];
        }
        this.submission = tempSubm;
    }

    /** 
     * Esse método adiciona uma submissão na lista de submissões da tarefa
     * @param sbmsn Submissão a ser adicionada
     */
    @Override
    public void addSubmission(Submission sbmsn) {
        if(sbmsn == null){
            throw new IllegalArgumentException("Null value for submission");
        }
        if(numberOfSubmissions == this.submission.length){
            realloc();
            this.submission[numberOfSubmissions] = sbmsn;
            numberOfSubmissions++;
        }else{
            this.submission[numberOfSubmissions] = sbmsn;
            numberOfSubmissions++;
        }
    }

    /**
     * Esse método estende o prazo da tarefa adicionando uma quantidade de dias
     * @param days Quantidade de dias a serem adicionados ao prazo
     */
    @Override
    public void extendDeadline(int days) {
        if(days < 0){
            throw new IllegalArgumentException("Negative Value");
        }
        LocalDate tempDays = end.plusDays(days);
        this.end = tempDays;
    }

    /**
     * Esse método compara a tarefa atual com outra tarefa
     * @return 0 se as tarefas começarem no mesmo dia
     */
    @Override
    public int compareTo(Task task) {
        if(this == task){
            return 0;
        }
        return this.start.compareTo(task.getStart());
    }

    
    //metodos JSON
    /*
     * Converte a tarefa em um objeto JSONObject
     * @return O objeto JSONObject que representa a tarefa
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("start", start.toString());
        jsonObject.put("end", end.toString());
        jsonObject.put("duration", duration);
        jsonObject.put("numberOfSubmissions", numberOfSubmissions);

        JSONArray submissionsArray = new JSONArray();
        for (int i = 0; i < numberOfSubmissions; i++) {
            submissionsArray.add(((ImpSubmission) submission[i]).toJsonObj());
        }
        jsonObject.put("submissions", submissionsArray);

        return jsonObject;
    }

    /*
     * Cria uma tarefa a partir de um objeto JSONObject
     * @param jsonObject Objeto JSONObject contendo os dados da tarefa
     * @return A tarefa criada a partir do objeto JSONObject
     */
    public static Task fromJsonObj(JSONObject jsonObject) {
        String title = (String) jsonObject.get("title");
        String description = (String) jsonObject.get("description");
        LocalDate start = LocalDate.parse((String) jsonObject.get("start"));
        LocalDate end = LocalDate.parse((String) jsonObject.get("end"));
        int duration = ((Long) jsonObject.get("duration")).intValue();

        ImpTask task = new ImpTask(title, description, start, end, duration);

        JSONArray submissionsArray = (JSONArray) jsonObject.get("submissions");

        for (int i = 0; i < submissionsArray.size(); i++) {
            JSONObject submissionJson = (JSONObject) submissionsArray.get(i);
            task.addSubmission(ImpSubmission.fromJsonObj(submissionJson));
        }

        return task;
    }
    
    //metodos CSV
     
    /*
     * Verifica se o objeto atual é igual ao objeto fornecido
     * @param obj Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        final ImpTask other = (ImpTask) obj;
        return this.title.equals(this.getTitle());
    }
    
    
    
}
