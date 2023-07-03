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
    
    /*
     * Variable that defines Edition's name 
     */
    private String name;
    /*
     * Variable that defines Edition's status
     */
    private Status status;
    /*
     * Variable that defines Edition's start and end date 
     */
    private LocalDate start, end;
    /*
     * Project template defined by the user 
     */
    private String projectTemplate;
    /*
     * List of projects
     */
    private Project projects[];
    /*
     * Variable that defines how many Editions are in the list
     */
    private int numberOfprojects;
    /*
     * Default structure of project when one is not chosen
     */
    private static final String defaultProjTemp = "src/Files/project_template.json";

    /*
     * This is the constructor method for Edition
     *
     * @param name Edition's name
     * @param start Edition's start date
     * @param end Edition's end dtae
     */
    public ImpEdition(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
        
        this.status = Status.INACTIVE;
        this.projects = new Project[5];
        this.projectTemplate = defaultProjTemp;
        this.numberOfprojects = 0;
    }

    /*
     * This is the constructor method for Edition
     * 
     * @param name Edition's name
     * @param status Edition's status
     * @param start Edition's start date
     * @param end Edition's end date
     * @param projectTemplate Project template defined by the user
     */
    public ImpEdition(String name, Status status, LocalDate start, LocalDate end, String projectTemplate) {
        this.name = name;
        this.status = status;
        this.start = start;
        this.end = end;
        this.projectTemplate = projectTemplate;
        
        projects = new Project[5];
        numberOfprojects = 0;
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
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectTemplate() {
        return this.projectTemplate;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Status getStatus() {
        return this.status;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfProjects() {
        return this.numberOfprojects;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getEnd() {
        return this.end;
    }
    
    /*
     * This method searches for a project int the list
     *
     * @param proj Project to be searched
     * @return true if it finds a project
     * @return false if it does not find a project
     */
    private boolean existsProject(Project proj){
        for(int i = 0; i < this.projects.length; i++){
            if(projects[i] != null && projects[i].equals(proj)){
                return true;
            }
        }
        return false;
    }
    
    /*
     * This method adds space to the Projects list
     */
    private void realloc(){
        Project[] tempProj = new Project[this.projects.length*2];
        for(int i = 0; i < this.projects.length; i++){
            tempProj[i] = this.projects[i];
        }
        this.projects = tempProj;
    }
    
    /*
     * This method adds a project to be edition. The project is created from the
     * template
     *
     * @param name The Project's name
     * @param description The Project's description
     * @param tags The Project's tags
     * @throws IOException if the project template is not found
     * @throws ParseException if the project template is not valid
     */
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
    
    /*Remove um projeto de uma edição encontrando o projeto atraves do seu nome
     * This method removes a project from the edition
     */
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
        Participant participant;

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
