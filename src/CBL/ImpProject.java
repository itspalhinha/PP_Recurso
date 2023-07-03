/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import java.util.Objects;
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
    private int maximumNumberOfTasks;
    private int maximumNumberOfParticipants;
    private int maximumNumberOfStudents;
    private int maximumNumberOfPartners;
    private int maximumNumberOfFacilitators;
    private Task[] task;
    private Participant[] participants;
    private String[] tags;

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

        Participant removedParticipant = participants[pos];

        for (i = pos; i < numberOfParticipants; i++) {
            participants[i] = participants[i + 1];
        }
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

    @Override
    public void addTask(Task task) throws IllegalNumberOfTasks, TaskAlreadyInProject {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task getTask(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task[] getTasks() {

        return null;

    }

    @Override
    public boolean isCompleted() {
        if (this.numberOfTasks != this.maximumNumberOfTasks) {
            return false;
        }

        for (Task t : task) {
            if (t.getNumberOfSubmissions() < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Project)) {
            return false;
        }
        final ImpProject other = (ImpProject) obj;
        return this.name.equals(this.getName());
    }

}
