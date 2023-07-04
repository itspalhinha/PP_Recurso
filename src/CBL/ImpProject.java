/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

    private String name;
    private String description;
    private int numberOfParticipants;
    private int numberOfStudents;
    private int numberOfPartners;
    private int numberOfFacilitators;
    private int numberOfTasks;
    private int numberOfTags;
    private int maximumNumberOfTasks;
    private int maximumNumberOfParticipants;
    private int maximumNumberOfStudents;
    private int maximumNumberOfPartners;
    private int maximumNumberOfFacilitators;
    private Task[] tasks;
    private Participant[] participants;
    private String[] tags;

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
        this.numberOfFacilitators = this.numberOfStudents = this.numberOfPartners = this.numberOfParticipants = this.numberOfTasks = this.numberOfTags = 0;
        //The limits variables need to be created based  on the arguments
        this.maximumNumberOfTasks = maximumNumberOfTasks;
        this.maximumNumberOfStudents = maximumNumberOfStudents;
        this.maximumNumberOfPartners = maximumNumberOfPartners;
        this.maximumNumberOfFacilitators = maximumNumberOfFacilitators;
        this.maximumNumberOfParticipants = this.maximumNumberOfStudents + this.maximumNumberOfPartners + this.maximumNumberOfFacilitators;
        this.tasks = new Task[maximumNumberOfTasks];
        this.participants = new Participant[(int) maximumNumberOfParticipants];
        this.tags = new String[2];

        for(String tag : tags) {
            this.addTags(tag);
        }

    }

    

    
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    @Override
    public int getNumberOfStudents() {
        return this.numberOfStudents;
    }

    @Override
    public int getNumberOfPartners() {
        return this.numberOfPartners;
    }

    @Override
    public int getNumberOfFacilitators() {
        return this.numberOfFacilitators;
    }

    @Override
    public int getNumberOfTasks() {
        return this.numberOfTasks;
    }

    @Override
    public int getMaximumNumberOfTasks() {
        return this.maximumNumberOfTasks;
    }

    @Override
    public long getMaximumNumberOfParticipants() {
        return this.maximumNumberOfParticipants;
    }

    @Override
    public int getMaximumNumberOfStudents() {
        return this.maximumNumberOfStudents;
    }

    @Override
    public int getMaximumNumberOfPartners() {
        return this.maximumNumberOfPartners;
    }

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

        //partivipant to be removed
        Participant removedParticipant = participants[pos];

        for (i = pos; i < numberOfParticipants; i++) {
            participants[i] = participants[i + 1];
        }

        //decremment on number of the type of the removedParticipant
        if (removedParticipant instanceof Facilitator) {
            numberOfFacilitators--;
        } else if (removedParticipant instanceof Student) {
            numberOfStudents--;
        } else if (removedParticipant instanceof Partner) {
            numberOfPartners--;
        }
        participants[--numberOfParticipants] = null;

        return removedParticipant;

    }

    @Override
    public Participant getParticipant(String string) {
        for (int i = 0; i < numberOfParticipants; i++) {
            if (participants[i].getEmail().equals(string)) {
                return participants[i];
            }
        }
        throw new IllegalArgumentException("No Participant found!");
    }

    @Override
    public String[] getTags() {
        return this.tags;
    }

    @Override
    public boolean hasTag(String string) {
        for (String s : this.tags) {
            if (s != null && s.equals(string)) {
                return true;
            }
        }
        return false;
    }

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

    private void reallocTags() {
        String[] temp = new String[tags.length * 2];
        int i = 0;
        for (String t : tags) {
            temp[i++] = t;
        }
        tags = temp;
    }

    
     private boolean hasTask(Task task) {
        for (int i=0; i<numberOfTasks; i++) {
            if (tasks[i].equals(task)) {
                return true;
            }
        }
        return false;
    }
     
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

    @Override
    public Task getTask(String title) {

        for (int i = 0; i < numberOfTasks; i++) {

            if (tasks[i].getTitle().equals(title)) {
                return tasks[i];
            }
        }
        throw new IllegalArgumentException("Task not found!");
    }

    @Override
    public Task[] getTasks() {
        Task[] temp = new Task[numberOfTasks];

        for (int i = 0; i < numberOfTasks; i++) {
            temp[i] = tasks[i];
        }
        return temp;
    }

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
