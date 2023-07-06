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
    * @param start Data de nício da tarefa
    * @param end Data de término da tarefa
    * @param duration Duração da tarefa
    * @param title Título da tarefa
    * @param description Descrição da tarefa
    */
    public ImpTask(LocalDate start, LocalDate end, int duration, String title, String description) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }

    /*
     * Esse é um dos métodos construtores para a tarefa
     *
     * @param title Título da tarefa
     * @param description Descrição da tarefa
     * @param start Data de início da tarefa
     * @param end Data de término da tarefa
     * @para duration Duração da tarefa
     */
    ImpTask(String title, String description, LocalDate start, LocalDate end, int duration) {
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

    /*
     * Verifica se o objeto atual é igual ao objeto fornecido
     * @param obj Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
    @Override
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
