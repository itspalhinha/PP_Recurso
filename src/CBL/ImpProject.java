/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import ma02_resources.participants.Participant;
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
public class ImpProject implements Project{
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

    @Override
    public void addParticipant(Participant p) throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Participant removeParticipant(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Participant getParticipant(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getTags() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasTag(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return this.task;
    }

    @Override
    public boolean isCompleted() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
