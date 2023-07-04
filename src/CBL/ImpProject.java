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

/**
 *
 * @author ROGER
 */
public class ImpProject implements Project {

    /*
     * Variable that defines Project's name 
     */
    private String name;
    /*
     * Variable that defines Project's description
     */
    private String description;
    /*
     * Variable that defines number of participants
     */
    private int numberOfParticipants;
    /*
     * Variable that defines number of students
     */
    private int numberOfStudents;
    /*
     * Variable that defines number of partners
     */
    private int numberOfPartners;
    /*
     * Variable that defines number of facilitators
     */
    private int numberOfFacilitators;
    /*
     * Variable that defines number of tasks
     */
    private int numberOfTasks;
    /*
     * Variable that defines number of tags
     */
    private int numberOfTags;
    /*
     * Variable that defines maximum number of tasks
     */
    private int maximumNumberOfTasks;
    /*
     * Variable that defines maximum number of participants
     */
    private int maximumNumberOfParticipants;
    /*
     * Variable that defines maximum number of students
     */
    private int maximumNumberOfStudents;
    /*
     * Variable that defines maximum number of partners
     */
    private int maximumNumberOfPartners;
    /*
     * Variable that defines maximum number of facilitators
     */
    private int maximumNumberOfFacilitators;
    /*
     * Array variable that stores tasks
     */
    private Task[] tasks;
    /*
     * Array variable that stores participants
     */
    private Participant[] participants;
    /*
     * Array variable that stores tags
     */
    private String[] tags;

    /**
     * Array variable that stores the evaluation of the Students in project
     */
    private Evaluation[] evaluations;
    private int numberOfEvaluations;

    /**
     * This is the constructor function for the Project class. It is used to
     * create a project object based on the Project Template. Any information 
     * not found in the project template is retrieved from the parameters of 
     * this function.

     * @param name: The name of the project.
     * @param description: The description of the project.
     * @param maximumNumberOfFacilitators: The maximum number of facilitators allowed in the project.
     * @param maximumNumberOfStudents: The maximum number of students allowed in the project.
     * @param maximumNumberOfPartners: The maximum number of partners allowed in the project.
     * @param maximumNumberOfTasks: The maximum number of tasks allowed in the project.
     * @param tags: The tags associated with the project.
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
     * Getter method for number of evaluations
     *
     * @return
     */
    public int getNumberOfEvaluations() {
        return numberOfEvaluations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfStudents() {
        return this.numberOfStudents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfPartners() {
        return this.numberOfPartners;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfFacilitators() {
        return this.numberOfFacilitators;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfTasks() {
        return this.numberOfTasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumNumberOfTasks() {
        return this.maximumNumberOfTasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getMaximumNumberOfParticipants() {
        return this.maximumNumberOfParticipants;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumNumberOfFacilitators() {
        return this.maximumNumberOfFacilitators;
    }

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
     *
     * This function includes a participant in the project by verifying if the
     * participant already exists and if the participant's list has reached its maximum capacity.
     *
     * @throws IllegalNumberOfParticipantType if list is full
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
     * This method checks the list of Participants and creates a list with
     * only Students
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
     * This method permits to atribute a note for a student
     *
     * @param student Student to be evaluated
     * @param selfEvaluation Note atributed from the student
     * @throws IllegalArgumentException if grade is not valid and if Student
     * is not in the Project
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
     * This method remove participant from the list by the name
     *
     * @param string Participant's name to be removed
     * @throws IllegalArgumentException if the participant is not found
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     *
     */
    @Override
    public String[] getTags() {
        return this.tags;
    }

    /**
     * {@inheritDoc}
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
     * This method appends a tag to the list of tags solely if it is non-null
     * or if it is not already present in the list.
     *
     * @param t Tag to be added
     * @throws IllegalArgumentException if the tag is null, empty or already
     * exist in array
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
     * This method extends the tag list by duplicating its current length, 
     * adding extra space.
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
     * This method checks whether a specified task exists in the list of tasks
     * 
     * @param task Task to be verified
     * @return true if exists
     * @return false if does not exist
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
     * This method adds task to the Task list checking if task already exists
     * and the maximum number of tasks
     *
     * @param task Task to be added
     * @throws IllegalNumberOfTasks if the maximum number of tasks reached in
     * project
     * @throws TaskAlreadyInProject if the task is already in the project
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
