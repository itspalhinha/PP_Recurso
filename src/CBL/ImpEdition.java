/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
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
    
    
    
    /*Adiciona um projeto numa edição*/
    @Override
    public void addProject(String string, String string1, String[] strings) throws IOException, ParseException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /*Remove um projeto de uma edição encontrando o projeto atraves do seu nome*/
    @Override
    public void removeProject(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /*Tipo um find para encontrar o projeto atraves do nome dado no parametro?*/
    @Override
    public Project getProject(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /*Retorna um array com todos os projetos*/
    @Override
    public Project[] getProjects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /*Retorna todos os projetos com um tag especifico*/
    @Override
    public Project[] getProjectsByTag(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    /*Retorna todos os projetos que tem um respetivo participante*/
    @Override
    public Project[] getProjectsOf(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
