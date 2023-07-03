/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Objects;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;

/**
 *
 * @author rafas
 */
public class ImpEdition implements Edition{
    
    private String name;
    private Status status;
    private LocalDate start, end;
    private String projectTemplate;
    private Project projects[];
    private int numberOfprojects;
    private static final String defaultProjTemp = "src/Files/project_template.json";

    public ImpEdition(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
        
        this.status = Status.INACTIVE;
        this.projects = new Project[5];
        this.projectTemplate = defaultProjTemp;
        this.numberOfprojects = 0;
    }
    
    

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public LocalDate getStart() {
        return this.start;
    }
    @Override
    public String getProjectTemplate() {
        return this.projectTemplate;
    }
    @Override
    public Status getStatus() {
        return this.status;
    }
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public int getNumberOfProjects() {
        return this.numberOfprojects;
    }
    @Override
    public LocalDate getEnd() {
        return this.end;
    }
    
    private boolean existsProject(Project proj){
        for(int i = 0; i < this.projects.length; i++){
            if(projects[i] != null && projects[i].equals(proj)){
                return true;
            }
        }
        return false;
    }
    private void realloc(){
        Project[] tempProj = new Project[this.projects.length*2];
        for(int i = 0; i < this.projects.length; i++){
            tempProj[i] = this.projects[i];
        }
        this.projects = tempProj;
    }
    
    /*Adiciona um projeto numa edição*/
    @Override
    public void addProject(String name, String description, String[] tags) throws IOException, ParseException {
        if(name == null || description == null || tags == null){
            throw new IllegalArgumentException("Illegal (param) is null");
        }
        if(numberOfprojects == projects.length){
            realloc();
            /*try{
            
            }catch(IOException ex){
                throw new IOException("Project template not found");  
            }catch(ParseException ex){
                throw new ParseException("Project template is not valid");
            }*/
        }else{
            /*try{
            
            }catch(IOException ex){
                throw new IOException("Project template not found");  
            }catch(ParseException ex){
                throw new ParseException("Project template is not valid");
            }*/
        }
        
        
    }
    
    /*Remove um projeto de uma edição encontrando o projeto atraves do seu nome*/
    @Override
    public void removeProject(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    /*Tipo um find para encontrar o projeto atraves do nome dado no parametro?*/
    @Override
    public Project getProject(String name) {
        for(int i = 0; i < numberOfprojects; i++){
            if(projects[i].getName().equals(name)){
                return projects[i];
            }
        }
        throw new IllegalArgumentException("Project name is null or the project dont exist");
        
    }
    /*Cria um array com todos os projetos*/
    @Override
    public Project[] getProjects() {
        int counter = 0;
        Project[] tempProj = new Project[numberOfprojects];
        
        for (int i = 0; i < this.numberOfprojects; i++){
            tempProj[counter] = this.projects[i];
            counter++;
        }
        return tempProj;
    }
    /*Retorna todos os projetos com um tag especifico*/
    @Override
    public Project[] getProjectsByTag(String tag) {
        int counter = 0;
        Project[] tempProjTags = new Project[numberOfprojects];
        for(int i = 0; i < numberOfprojects; i++){
            if(projects[i].hasTag(tag)){
                tempProjTags[counter] = projects[i];
                counter++;
            }
        }
        if(counter == 0){
            return null;
        }
        if(counter != numberOfprojects){
            Project[] fullArray = new Project[counter];
            for(int i = 0; i < counter; i++){
                fullArray[i] = tempProjTags[i];
                return fullArray;
            }
        }
        return tempProjTags;
    }
    
    /*Retorna todos os projetos que tem um respetivo participante*/
    @Override
    public Project[] getProjectsOf(String string) {
        Project[] temp = new Project[this.numberOfprojects];

        int counter = 0;
        for (int i = 0; i < numberOfprojects; i++) {
            try {
                projects[i].getParticipant(string);
                //if it didnt throw an exception, the project will be added to the array that will be later returned
                temp[counter++] = projects[i];
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (counter != numberOfprojects) {
            Project[] trimmedTemp = new Project[counter];

            for (int i = 0; i < counter; i++) {
                trimmedTemp[i] = temp[i];
            }
            return trimmedTemp;
        }
        return temp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Edition)) {
            return false;
        }
        final ImpEdition other = (ImpEdition) obj;
        return this.name.equals(this.getName());
    }

    
    

}
