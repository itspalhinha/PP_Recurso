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

import Exceptions.AlreadyExistsInArray;
import Participant.ImpParticipant;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;
import ma02_resources.project.Project;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ImpProject implements Project {

    /*
     * Nome do projeto
     */
    private String name;
    /*
     * Descrição do projeto
     */
    private String description;
    /*
     * Número de participantes
     */
    private int numberOfParticipants;
    /*
     * Número de estudantes
     */
    private int numberOfStudents;
    /*
     * Número de parceiros
     */
    private int numberOfPartners;
    /*
     * Número de facilitadores
     */
    private int numberOfFacilitators;
    /*
     * Número de tarefas
     */
    private int numberOfTasks;
    /*
     * Número de tags
     */
    private int numberOfTags;
    /*
     * Número máximo de tarefas por projeto
     */
    private int maximumNumberOfTasks;
    /*
     * Número máximo de participantes
     */
    private int maximumNumberOfParticipants;
    /*
     * Número máximo de estudantes
     */
    private int maximumNumberOfStudents;
    /*
     * Número máximo de parceiros
     */
    private int maximumNumberOfPartners;
    /*
     * Número máximo de facilitadores
     */
    private int maximumNumberOfFacilitators;
    /*
     * Array que armazena as tarefas do projeto
     */
    private Task[] tasks;
    /*
     * Array que armazena os participantes do projeto
     */
    private Participant[] participants;
    /*
     * Array que armazena as tags do projeto
     */
    private String[] tags;

    /**
     * Array que armazena as avaliações dos estudantes no projeto
     */
    private Evaluation[] evaluations;
    /*
     * Número de avaliações
     */
    private int numberOfEvaluations;

    /**
     * Método construtor da classe ImpProject, usado para criar um objeto de projeto com base
     * no Template de Projeto. Todas as informações não encontradas no template do projeto são
     * obtidas a partir dos parâmetros deste método.

     * @param name Nome do projeto
     * @param description Descrição do projeto
     * @param maximumNumberOfFacilitators Número máximo de facilitadores permitidos no projeto
     * @param maximumNumberOfStudents Número máximo de estudantes permitidos no projeto
     * @param maximumNumberOfPartners Número máximo de parceiros permitidos no projeto
     * @param maximumNumberOfTasks Número máximo de tarefas permitidas no projeto
     * @param tags Tags associadas ao projeto
     */
    public ImpProject(String name, String description, int maximumNumberOfFacilitators, int maximumNumberOfStudents, int maximumNumberOfPartners, int maximumNumberOfTasks, String[] tags) {

        this.name = name;
        this.description = description;
        this.numberOfFacilitators = this.numberOfEvaluations = this.numberOfStudents = this.numberOfPartners = this.numberOfParticipants = this.numberOfTasks = this.numberOfTags = 0;
        this.maximumNumberOfTasks = maximumNumberOfTasks;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.maximumNumberOfPartners = maximumNumberOfPartners;
        this.maximumNumberOfFacilitators = maximumNumberOfFacilitators;
        this.maximumNumberOfParticipants = this.maximumNumberOfStudents + this.maximumNumberOfPartners + this.maximumNumberOfFacilitators;
        this.tasks = new Task[maximumNumberOfTasks];
        this.participants = new Participant[(int) maximumNumberOfParticipants];
        this.tags = new String[2];
        this.evaluations = new Evaluation[maximumNumberOfStudents];

        for (String tag : tags) {
            this.addTags(tag);
        }

    }

    /**
     * Obtém o número de avaliações
     * @return Número de avaliações
     */
    public int getNumberOfEvaluations() {
        return numberOfEvaluations;
    }

    /**
     * Obtém o nome do projeto
     * @return Nome do projeto
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Obtém a descrição do projeto
     * @return Descrição do projeto
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Obtém o número de participantes do projeto
     * @return Número de participantes do projeto
     */
    @Override
    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /**
     * Obtém o número de estudantes do projeto
     * @return Número de estudantes do projeto
     */
    @Override
    public int getNumberOfStudents() {
        return this.numberOfStudents;
    }

    /**
     * Obtém o número de parceiros do projeto
     * @return Número de parceiros do projeto
     */
    @Override
    public int getNumberOfPartners() {
        return this.numberOfPartners;
    }

    /**
     * Obtém o número de facilitadores do projeto
     * @return Número de facilitadores do projeto
     */
    @Override
    public int getNumberOfFacilitators() {
        return this.numberOfFacilitators;
    }

    /**
     * Obtém o número de tarefas do projeto
     * @return Número de taredas do projeto
     */
    @Override
    public int getNumberOfTasks() {
        return this.numberOfTasks;
    }

    /**
     * Obtém o número máximo de tarefas permitidas no projeto
     * @return Número máximo de tarefas permitidas no projeto
     */
    @Override
    public int getMaximumNumberOfTasks() {
        return this.maximumNumberOfTasks;
    }

    /**
     * Obtém o número máximo de participantes permitidos no projeto
     * @return Número máximo de participantes permitidos no projeto
     */
    @Override
    public long getMaximumNumberOfParticipants() {
        return this.maximumNumberOfParticipants;
    }

    /**
     * Obtém o número máximo de estudantes permitidos no projeto
     * @return Número máximo de estudantes permitidos no projeto
     */
    @Override
    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    /**
     * Obtém o número máximo de parceiros permitidos no projeto
     * @return Número máximo de parceiros permitidos no projeto
     */
    @Override
    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

    /**
     * Obtém o número máximo de facilitadores permitidos no projoeto
     * @return Número máximo de facilitadores permitidos no projeto
     */
    @Override
    public int getMaximumNumberOfFacilitators() {
        return this.maximumNumberOfFacilitators;
    }

    /*
     * Verifica se um participante está presente no projeto
     * @param p Participante a ser verificado
     * @return true se o participante estiver presente, false caso contrário
     */
    private boolean hasParticipant(Participant p) {
        for (int i = 0; i < numberOfParticipants; i++) {
            if (participants[i].equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Essa função inclui um participante no projeto, verificando se o participante já existe
     * e se a lista de participantes já atingiu sua capacidade máxima
     * @param p Participante a ser adicionado
     * @throws IllegalNumberOfParticipantType se a lista estiver em sua capacidade máxima
     * @throws ParticipantAlreadyInProject se o participante já estiver no projeto
     * 
     */
    @Override
    public void addParticipant(Participant p) throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject {
        //check if participant already exists in this Project
        if (hasParticipant(p)) {
            throw new ParticipantAlreadyInProject("Participant already in Project!");
        }

        //check the type of participant to compare to the maximum ammount of participants of that type and incremment on the apropriaete type
        if (p instanceof Student) {
            if (numberOfStudents == maximumNumberOfStudents) {
                throw new IllegalNumberOfParticipantType("Maximum Students for Project already reached!");
            }
            numberOfStudents++;
            // evaluations[numberOfEvaluations++] = new Evaluation((Student)p);

        } else if (p instanceof Partner) {
            if (numberOfPartners == maximumNumberOfPartners) {
                throw new IllegalNumberOfParticipantType("Maximum Partners for Project already reached!");
            }
            numberOfPartners++;
        } else if (p instanceof Facilitator) {
            if (numberOfFacilitators == maximumNumberOfFacilitators) {
                throw new IllegalNumberOfParticipantType("Maximum Facilitators for Project already reached!");
            }
            numberOfFacilitators++;
        }

        participants[numberOfParticipants++] = p;

    }

    /*
     * Esse método verifica a lista de participantes e cria uma lista somente com estudantes
     */
    private Student[] getStudents() {
        Student[] temp = new Student[numberOfStudents];
        int counter = 0;
        for (int i = 0; i < numberOfParticipants; i++) {
            if (participants[i] instanceof Student) {
                temp[counter++] = (Student) participants[i];
            }

        }
        return temp;
    }
    
    /*
     * Essa função verifica uma lista de participantes e cria uma lista somente com facilitadores
     * @return Um array de facilitadores contendo apenas facilitadores
     */
    private Facilitator[] getFacilitator(){
        Facilitator[] temp = new Facilitator[numberOfFacilitators];
        int counter = 0;
        for(int i = 0; i < numberOfParticipants; i++){
            if(participants[i] instanceof Facilitator){
                temp[counter++] = (Facilitator) participants[i];
            }
        }
        return temp;
    }
    
    /*
     * Essa função adiciona uma avaliação heterogênea para um estudante em relação a um 
     * facilitador específico
     * @param student Estudante a ser avaliado
     * @param facilitator Facilitador responsável pela avaliação
     * @param heteroEvaluation Avaliação hererogênia atribuída ao estudante
     * @throws IllegalArgumentException se a nota não for válida ou se o facilitador ou o estudante 
     * não estiverem no projeto
     */
    public void addHeteroEvaluation(Student student, Facilitator facilitator, float heteroEvaluation) throws IllegalArgumentException{
        if(heteroEvaluation < 0f || heteroEvaluation > 20f){
            throw new IllegalArgumentException("Grade not valid");
        }
        if(!hasParticipant(facilitator)){
            throw new IllegalArgumentException("Facilitator doesnt exist in the project");
        }
        if(!hasParticipant(student)){
            throw new IllegalArgumentException("Student doesnt exist in the project");
        }
        
        boolean found = false;
        int i = 0;
        while (!found && i < numberOfEvaluations){
            if(evaluations[i].getStudent().equals(student)){
                evaluations[i].setHeteroevaluation(heteroEvaluation);
                found = true;
            }
            i++;
        }
        if(!found){
            evaluations[numberOfEvaluations++] = new Evaluation(student, heteroEvaluation, facilitator);
        }
    }

    /*
     * Esse método permite atribuir uma nota pelo próprio estudante
     *
     * @param student Estudante a ser avaliado
     * @param selfEvaluation Nota atribuída pelo estudante
     * @throws IllegalArgumentException se a nota não for válida ou se o estudante não estiver
     * no projeto
     */

    public void addSelfEvaluation(Student student, float selfEvaluation) throws IllegalArgumentException{
        if (selfEvaluation < 0f || selfEvaluation > 20f){
            throw new IllegalArgumentException("Grade not valid!");
        }
        
        if (!hasParticipant(student)){
            throw new IllegalArgumentException("Student not in Project!");
        }
        
        
        boolean found = false;
        int i = 0;
        while (!found && i < numberOfEvaluations) {
            if (evaluations[i].getStudent().equals(student)) {
                evaluations[i].setSelfEvaluation(selfEvaluation);
                found = true;
            }
            i++;
        }
        
        if (!found){
            evaluations[numberOfEvaluations++] = new Evaluation(selfEvaluation,student);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Essa função remove um participante do projeto com base no seu email
     * @param string Participante a ser removido
     * @throws IllegalArgumentException se o participante não for encontrado
     */
    @Override
    public Participant removeParticipant(String string) {

        int pos = -1, i = 0;

        //check for the participant index in participants array
        while (pos == -1 && i < numberOfParticipants) {
            if (participants[i].getEmail().equals(string)) {
                pos = i;
            } else {
                i++;
            }
        }
        // if didn't find a participant with the email given, throw IllegalArgumentException
        if (pos == -1) {
            throw new IllegalArgumentException("Participant not found!");
        }

        //participant to be removed
        Participant removedParticipant = participants[pos];

        for (i = pos; i < numberOfParticipants; i++) {
            participants[i] = participants[i + 1];
        }

        //decremment on number of the type of the removedParticipant
        if (removedParticipant instanceof Facilitator) {
            numberOfFacilitators--;
        } else if (removedParticipant instanceof Student) {
            numberOfStudents--;

            //for (int i=0; i<)
        } else if (removedParticipant instanceof Partner) {
            numberOfPartners--;
        }
        participants[--numberOfParticipants] = null;

        return removedParticipant;

    }

    /**
     * Obtém o participante com o email especificado
     * @param string Email do participante a ser obtido
     * @return Participante com o email fornecido
     * @throws IllegalArgumentException se o participante não for encontrado
     *
     */
    @Override
    public Participant getParticipant(String string) {
        for (int i = 0; i < numberOfParticipants; i++) {
            if (participants[i].getEmail().equals(string)) {
                return participants[i];
            }
        }
        throw new IllegalArgumentException("No Participant found!");
    }

    /**
     * Obtém as tags associadas ao projeto
     * @return Um array contendo as tags do projeto
     */
    @Override
    public String[] getTags() {
        return this.tags;
    }

    /**
     * Verifica se o projeto possui determinada tag
     * @param string Tag a ser verificada
     * @return true se a tag estiver presente, false caso contrário
     *
     */
    @Override
    public boolean hasTag(String string) {
        for (String s : this.tags) {
            if (s != null && s.equals(string)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Essa função adiciona uma tag à lista de tags de ela não for nula ou se ainda não
     * estiver presente na lista
     * @param t Tag a ser adicionada
     * @throws IllegalArgumentException se a tag for nula, vazia ou se já existir na lista
     */
    public void addTags(String t) throws IllegalArgumentException {
        if (t == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        if (hasTag(t)) {
            throw new IllegalArgumentException("Tag already in Array");
        }

        if (numberOfTags == tags.length) {
            reallocTags();
        }
        tags[numberOfTags++] = t;

    }

    /*
     * Esse método aumenta o tamanho da lista de tags duplicando o seu comprimento atual,
     * adicionando espaço extra
     */
    private void reallocTags() {
        String[] temp = new String[tags.length * 2];
        int i = 0;
        for (String t : tags) {
            temp[i++] = t;
        }
        tags = temp;
    }

    /*
     * Essa função verifica se uma determinada tarefa existe na lista de tarefas
     * @param task Tarefa a ser verificadsa
     * @return true se existir, false caso contrário
     */
    private boolean hasTask(Task task) {
        for (int i = 0; i < numberOfTasks; i++) {
            if (tasks[i].equals(task)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Essa função adiciona uma tarefa à lista de tarefas, verificando se a tarefa já existe e se
     * o número máximo de tarefas já foi atingido
     * @param task Tarefa a ser adicionada
     * @throws IllegalNumberOfTasks se o número máximo de tarefas for atingido no projeto
     * @throws TaskAlreadyInProject se a tarefa já estiver no projeto
     */
    @Override
    public void addTask(Task task) throws IllegalNumberOfTasks, TaskAlreadyInProject {
        if (hasTask(task)) {
            throw new TaskAlreadyInProject("Task already in Project!");
        }
        if (numberOfTasks == maximumNumberOfTasks) {
            throw new IllegalNumberOfTasks("Maximum Tasks reached in project!");
        }

        tasks[numberOfTasks++] = task;

    }

    /**
     * Obtém a tarefa com o título especificado
     * @param Tarefa com o título fornecido
     * @return Tarefa com o título fornecido
     * @throws IllegalArgumentException se a tarefa não for encontrada
     */
    @Override
    public Task getTask(String title) {

        for (int i = 0; i < numberOfTasks; i++) {

            if (tasks[i].getTitle().equals(title)) {
                return tasks[i];
            }
        }
        throw new IllegalArgumentException("Task not found!");
    }

    /**
     * Obtém todas as tarefas do projeto
     * @return Um array contendo todas as tarefas do projeto
     */
    @Override
    public Task[] getTasks() {
        Task[] temp = new Task[numberOfTasks];

        for (int i = 0; i < numberOfTasks; i++) {
            temp[i] = tasks[i];
        }
        return temp;
    }

    /**
     * Veficia se o projeto está concluído
     * @return true se o projeto estiver concluído, false caso contrário
     */
    @Override
    public boolean isCompleted() {
        if (this.numberOfTasks != this.maximumNumberOfTasks) {
            return false;
        }

        for (int i = 0; i < numberOfTasks; i++) {
            if (tasks[i].getNumberOfSubmissions() < 1) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method is used to export the information about a project to a JSON file.
     * A JSON object is created containing all the project's data and then it is returned.
     * @return JSON object of the project
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();

    /*
     * Verifica se o objeto atual é igual ao objeto fornecido
     * @param obj Objeto a ser comparado
     * @return true se os objetos forem iguais, false caso contrário
     */
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("numberOfFacilitators", numberOfFacilitators);
        jsonObject.put("numberOfStudents", numberOfStudents);
        jsonObject.put("numberOfPartners", numberOfPartners);
        jsonObject.put("numberOfParticipants", numberOfParticipants);
        jsonObject.put("numberOfTasks", numberOfTasks);
        jsonObject.put("numberOfTags", numberOfTags);

        jsonObject.put("maximumNumberOfFacilitators", maximumNumberOfFacilitators);
        jsonObject.put("maximumNumberOfStudents", maximumNumberOfStudents);
        jsonObject.put("maximumNumberOfPartners", maximumNumberOfPartners);
        jsonObject.put("maximumNumberOfParticipants", maximumNumberOfParticipants);
        jsonObject.put("maximumNumberOfTasks", maximumNumberOfTasks);

        JSONArray tasksArray = new JSONArray();
        for (int i = 0; i < numberOfTasks; i++) {
            tasksArray.add(((ImpTask) tasks[i]).toJsonObj());
        }
        jsonObject.put("tasks", tasksArray);

        JSONArray participantsArray = new JSONArray();
        for (int i = 0; i < numberOfParticipants; i++) {
            participantsArray.add(((ImpParticipant) participants[i]).toJsonObj());
        }
        jsonObject.put("participants", participantsArray);

        JSONArray tagsArray = new JSONArray();
        for (int i = 0; i < numberOfTags; i++) {
            tagsArray.add(tags[i]);
        }
        jsonObject.put("tags", tagsArray);

        return jsonObject;
    }

    public static Project fromJsonObj(JSONObject jsonObject) {

        String name = (String) jsonObject.get("name");
        String description = (String) jsonObject.get("description");

        int maximumNumberOfFacilitators = ((Long) jsonObject.get("maximumNumberOfFacilitators")).intValue();
        int maximumNumberOfStudents = ((Long) jsonObject.get("maximumNumberOfStudents")).intValue();
        int maximumNumberOfPartners = ((Long) jsonObject.get("maximumNumberOfPartners")).intValue();
        int maximumNumberOfTasks = ((Long) jsonObject.get("maximumNumberOfTasks")).intValue();

        JSONArray tagsArray = (JSONArray) jsonObject.get("tags");
        String[] tags = new String[tagsArray.size()];
        for (int i = 0; i < tagsArray.size(); i++) {
            tags[i] = (String) tagsArray.get(i);
        }

        ImpProject project = new ImpProject(name, description, maximumNumberOfFacilitators, maximumNumberOfStudents, maximumNumberOfPartners, maximumNumberOfTasks, tags);

        JSONArray tasksArray = (JSONArray) jsonObject.get("tasks");
        for (int i = 0; i < tasksArray.size(); i++) {
            try {
                JSONObject taskJson = (JSONObject) tasksArray.get(i);
                project.addTask(ImpTask.fromJsonObj(taskJson));
            } catch (IllegalNumberOfTasks | TaskAlreadyInProject ex) {

            }
        }

        JSONArray participantsArray = (JSONArray) jsonObject.get("participants");
        for (int i = 0; i < participantsArray.size(); i++) {
            try {
                JSONObject participantJson = (JSONObject) participantsArray.get(i);
                Participant p = ImpParticipant.fromJsonObj(participantJson);
                project.addParticipant(p);
            } catch (IllegalNumberOfParticipantType | ParticipantAlreadyInProject ex) {

            }
        }

        return project;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Project)) {
            return false;
        }
        final Project other = (Project) obj;
        return this.name.equals(other.getName());
    }

}
