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

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.TaskAlreadyInProject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
            try{
                Reader read = new FileReader(this.projectTemplate);
                JSONParser parser = new JSONParser();
                JSONObject obj;
                
                obj = (JSONObject) parser.parse(read);
                
                long number_of_facilitators = (long) obj.get("number_of_facilitators");
                long number_of_students = (long) obj.get("number_of_students");
                long number_of_partners = (long) obj.get("number_of_partners");
                
                //Create a json array
                
                JSONArray taskArray = (JSONArray) obj.get("tasks");
                
                Project newPj = new ImpProject(name, description, (int)number_of_facilitators, (int)number_of_students, (int)number_of_partners, taskArray.size(), tags);
                
                if(existsProject(newPj)){
                    throw new IllegalArgumentException("Project already exists in edition");
                }
                
                for(int i = 0; i < taskArray.size(); i++){
                    JSONObject aTask = (JSONObject) taskArray.get(i);
                String title = (String) aTask.get("title");
                String taskDescription = (String) aTask.get("description");
                long start_at = (long) aTask.get("start_at");
                long duration = (long) aTask.get("duration");

                //calculate the start and the  end days
                LocalDate taskStart = this.start.plusDays(start_at);
                LocalDate taskEnd = this.start.plusDays(duration);
                // create task

                try {
                    //add to the new project
                    newPj.addTask(new ImpTask(title, taskDescription, taskStart, taskEnd, (int) duration));
                } catch (IllegalNumberOfTasks | TaskAlreadyInProject ex) {
                    Logger.getLogger(ImpEdition.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                projects[numberOfprojects++] = newPj;
                
            } catch (org.json.simple.parser.ParseException ex) {
                Logger.getLogger(ImpEdition.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try{
                Reader read = new FileReader(this.projectTemplate);
                JSONParser parser = new JSONParser();
                JSONObject obj;
                
                obj = (JSONObject) parser.parse(read);
                
                long number_of_facilitators = (long) obj.get("number_of_facilitators");
                long number_of_students = (long) obj.get("number_of_students");
                long number_of_partners = (long) obj.get("number_of_partners");
                
                //Create a json array
                
                JSONArray taskArray = (JSONArray) obj.get("tasks");
                
                Project newPj = new ImpProject(name, description, (int)number_of_facilitators, (int)number_of_students, (int)number_of_partners, taskArray.size(), tags);
                
                if(existsProject(newPj)){
                    throw new IllegalArgumentException("Project already exists in edition");
                }
                
                for(int i = 0; i < taskArray.size(); i++){
                    JSONObject aTask = (JSONObject) taskArray.get(i);
                String title = (String) aTask.get("title");
                String taskDescription = (String) aTask.get("description");
                long start_at = (long) aTask.get("start_at");
                long duration = (long) aTask.get("duration");

                //calculate the start and the  end days
                LocalDate taskStart = this.start.plusDays(start_at);
                LocalDate taskEnd = this.start.plusDays(duration);
                // create task

                try {
                    //add to the new project
                    newPj.addTask(new ImpTask(title, taskDescription, taskStart, taskEnd, (int) duration));
                } catch (IllegalNumberOfTasks | TaskAlreadyInProject ex) {
                    Logger.getLogger(ImpEdition.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                projects[numberOfprojects++] = newPj;
                
            } catch (org.json.simple.parser.ParseException ex) {
                Logger.getLogger(ImpEdition.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    /*Remove um projeto de uma edição encontrando o projeto atraves do seu nome
     * This method removes a project from the edition
     */
    @Override
    public void removeProject(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Null argument!");
        }
        String[] tags = {"null"};
        Project project = new ImpProject(string, null, 0, 0, 0, 0, tags);

        int pos = -1, i = 0;

        while (pos == -1 && i < numberOfprojects) {

            if (projects[i].equals(project)) {
                pos = i;
            } else {
                i++;
            }
        }
        if (pos == -1) {
            throw new IllegalArgumentException("No project found with that argument!");
        }
        for (i = pos; i < numberOfprojects; i++) {
            projects[i] = projects[i + 1];
        }
        projects[--numberOfprojects] = null;
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
