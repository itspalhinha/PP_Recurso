/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rafas
 */
public class ImpCBL implements CBLinterface{
    
    private Edition[] editions;
    private int numOfEditions;
    
    public ImpCBL(int size){
        this.editions = new Edition[size];
        this.numOfEditions = 0;
    }
    public ImpCBL(){
        this.editions = new Edition[1];
        this.numOfEditions = 0;
    }
    /**
     * Função para realocar o tamanho do array destinado a edições
     */
    public void realloc(){
        Edition[] tempEdition = new Edition[this.editions.length * 2];
        for(int i = 0; i < this.numOfEditions; i++){
            tempEdition[i] = editions[i];
        }
        editions = tempEdition;
    }
    /***
     * Função para procurar uma edição num array de edições
     * @param edition especificar edição que queremos procurar no array de Edições 
     * @return retorna true se existir e false se nao existir
     */
    public boolean existEdition(Edition edition){
        for(int i = 0; i < this.numOfEditions; i++){
            if(editions[i] != null && editions[i].equals(edition)){
                return true;
            }
        }
        return false;
    }
    

    @Override
    public void addEdition(Edition edition) throws EditionAlreadyExist {
        if(this.numOfEditions == editions.length){
            realloc();
        }
        if(existEdition(edition)){
            throw new EditionAlreadyExist("Edition Already exist in the CBL");
        }
        this.editions[numOfEditions] = edition;
        numOfEditions++;
    }

    @Override
    public Edition removeEdition(String name) throws EditionDontExist {
        Edition removed = null;
        int exist = 0;
        for(int i = 0; i < this.numOfEditions; i++){
            if(editions[i].getName().equals(name)){
                removed = this.editions[i];
                exist = i;
            }
        }
        if(exist == 0){
            throw new EditionDontExist("Edition dont exist in the CBL");
        }
        for(int j = exist; j < numOfEditions; j++){
            editions[j] = editions[j+1];
        }
        editions[--numOfEditions] = null;
        return removed;
    }

    @Override
    public Edition getEdition(String name) {
        for(int i = 0; i < this.numOfEditions; i++){
            if(editions[i].getName().equals(name)){
                return editions[i];
	    }
        }
        throw new IllegalArgumentException("Edition not found in CBL");
    }

    @Override
    public void activateEdition(String name) throws IllegalArgumentException{
        int pos = -1;
        Edition edition = new ImpEdition(name, null, null);
        
        for(int j = 0; j < numOfEditions; j++){
            if(this.editions[j] != null && this.editions[j].getStatus() == Status.ACTIVE){
                pos = j;
            }
        }
        
        boolean cmp = false;
        int i = 0;
        
        while (!cmp && i < numOfEditions) {
            if (editions[i].equals(edition)) {
                if (pos != -1) {
                    //closed if end Date not happened yet and cancelled if end date not happened
                    if (editions[pos].getEnd().compareTo(LocalDate.now()) <= 0) {
                        editions[pos].setStatus(Status.CLOSED);
                    } else {
                        editions[pos].setStatus(Status.CANCELED);
                    }
                }
                editions[i].setStatus(Status.ACTIVE);

                cmp = true;
            }
            i++;
        }
        if (!cmp) {
            throw new IllegalArgumentException("No edition found!");
        }
    }

    @Override
    public int getNumberOfEditions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Edition[] getEditionsByParticipant(Participant p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean exportJSON(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean importDataJSON(String filePath) {
        return false;
    }

    @Override
    public boolean importDataCSV(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean exportCSV(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Edition[] getEditions() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Project[] getProjectsOf(Participant participant) {
        int projectCount = 0;

        for (int i = 0; i < numOfEditions; i++) {
            Project[] projects = editions[i].getProjectsOf(participant.getEmail());
            projectCount += projects.length;
        }

        Project[] participantProjects = new Project[projectCount];
        int counter = 0;

        for (int j = 0; j < numOfEditions; j++) {
            Project[] projects = editions[j].getProjectsOf(participant.getEmail());
            for (int i = 0; i < projects.length; i++) {
                participantProjects[counter++] = projects[i];
            }
        }
        return participantProjects;
    }


}
