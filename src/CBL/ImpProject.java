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
     * This is the constructor method of Project. It is used to create a project
     * object based on the Project Template. All the information not found in
     * the project template is retrieved from the parameters of this method.
     *
     * @param name Name of the project.
     * @param description Description of the project.
     * @param maximumNumberOfFacilitators Max number of facilitators in a
     * project.
     * @param maximumNumberOfStudents Max number of students in a project.
     * @param maximumNumberOfPartners Max number of partners in a project.
     * @param maximumNumberOfTasks Max number of tasks in a project.
     * @param tags Tags associated with the project.
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
     * This method adds participant to the project checking if the participant
     * to be added already exists and if the participant's list is full
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

    /*
     * This method permits to atribute a note for a student
     *
     * @param student Student to be evaluated
     * @param selfEvaluation Note atributed from the student
     */
    public void addSelfEvaluation(Student student, double selfEvaluation) {

        //check if Student has an evaluation assigned
        boolean found = false;
        Student[] students = getStudents();
        int i=0;
        while (!found && i < students.length) {
            
            
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
     * This method adds a tag to the tag's list only if it is not null or if it
     * does not already exist on the list
     *
     * @param t Tag to be added
     * @throws IllegalArgumentException if the tag is null or empty or already
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
     * This method adds space to the tag list duplicating the current lenght of 
     * the list
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
     * This method verifies if a given task exists in the task list
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
