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


public class ImpEdition implements Edition{

    /*
     * Nome da edição 
     */
    private String name;
    /*
     * Status da edição 
     */
    private Status status;
    /*
     * Data de início e término da edição 
     */
    private LocalDate start, end;
    /*
     * Template do projeto definido pelo usuário 
     */
    private String projectTemplate;
    /*
     * List de projetos
     */
    private Project projects[];
    /*
     * Número de projetos atualmente na edição
     */
    private int numberOfprojects;
    /*
     * Template de projeto padrão
     */
    private static final String defaultProjTemp = "src/Files/project_template.json";

    /*
     * Construtor que cria uma instância de ImpEdition com nome, data de início e
     * data de término especificados
     *
     * @param name Nome da edição
     * @param start Data de início
     * @param end Data de término
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
     * Construtor que cria uma instância de ImpEdition com nome, status, data de início
     * e término, e template de projeto especificados
     * 
     * @param name Nome da edição
     * @param status Status da edição
     * @param start Data de início
     * @param end Data de término
     * @param projectTemplate Template de projeto definido pelo usuário
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
     * Obtém o nome da edição
     * @return Nome da edição
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Obtém data de início da edição
     * @return Data de início da edição
     */
    @Override
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * Obtém o template de projeto da edição
     * @return Template de projeto da edição
     */
    @Override
    public String getProjectTemplate() {
        return this.projectTemplate;
    }

    /**
     * Obtém status da edição
     * @return Status da edição
     */
    @Override
    public Status getStatus() {
        return this.status;
    }

    /**
     * Define o status da edição
     * @param status Novo status da edição
     */
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Obtém o número de projetos na edição
     * @return Número de projetos na edição
     */
    @Override
    public int getNumberOfProjects() {
        return this.numberOfprojects;
    }

    /**
     * Obtém a data de término da edição
     * @return Data de término da edição
     */
    @Override
    public LocalDate getEnd() {
        return this.end;
    }

    /*
     * Essa função pesquisa por um projeto na lista
     *
     * @param proj Projeto a ser verificado
     * @return true se o projeto já existe
     * @return false se não encontra o projeto
     */
    private boolean existsProject(Project proj) {
        for (int i = 0; i < this.projects.length; i++) {
            if (projects[i] != null && projects[i].equals(proj)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Essa função adiciona espaço na lista de projetos
     */
    private void realloc() {
        Project[] tempProj = new Project[this.projects.length * 2];
        for (int i = 0; i < this.projects.length; i++) {
            tempProj[i] = this.projects[i];
        }
        this.projects = tempProj;
    }

    /*
     * Essa função adiciona um novo projeto à edição com base no template de projeto fornecido
     *
     * @param name Nome do projeto
     * @param description Descrição do projeto
     * @param tags Tags do projeto
     * @throws IOException lançada se o template de projeto não for encontrado
     * @throws ParseException lançada se o template de projeto não for válido
     */
    @Override
    public void addProject(String name, String description, String[] tags) throws IOException, ParseException {
        if (name == null || description == null || tags == null) {
            throw new IllegalArgumentException("Illegal (param) is null");
        }
        if (numberOfprojects == projects.length) {
            realloc();
            try {
                Reader read = new FileReader(this.projectTemplate);
                JSONParser parser = new JSONParser();
                JSONObject obj;

                obj = (JSONObject) parser.parse(read);

                long number_of_facilitators = (long) obj.get("number_of_facilitators");
                long number_of_students = (long) obj.get("number_of_students");
                long number_of_partners = (long) obj.get("number_of_partners");

                //Create a json array
                JSONArray taskArray = (JSONArray) obj.get("tasks");

                Project newPj = new ImpProject(name, description, (int) number_of_facilitators, (int) number_of_students, (int) number_of_partners, taskArray.size(), tags);

                if (existsProject(newPj)) {
                    throw new IllegalArgumentException("Project already exists in edition");
                }

                for (int i = 0; i < taskArray.size(); i++) {
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
        } else {
            try {
                Reader read = new FileReader(this.projectTemplate);
                JSONParser parser = new JSONParser();
                JSONObject obj;

                obj = (JSONObject) parser.parse(read);

                long number_of_facilitators = (long) obj.get("number_of_facilitators");
                long number_of_students = (long) obj.get("number_of_students");
                long number_of_partners = (long) obj.get("number_of_partners");

                //Create a json array
                JSONArray taskArray = (JSONArray) obj.get("tasks");

                Project newPj = new ImpProject(name, description, (int) number_of_facilitators, (int) number_of_students, (int) number_of_partners, taskArray.size(), tags);

                if (existsProject(newPj)) {
                    throw new IllegalArgumentException("Project already exists in edition");
                }

                for (int i = 0; i < taskArray.size(); i++) {
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
    
    /* Essa função remove um projeto da edição com base em seu nome
=======

    /*Remove um projeto de uma edição encontrando o projeto atraves do seu nome
     * This method removes a project from the edition identifying by the name
>>>>>>> 8801e3c40a307620edba7043871a6c16c231cc20
     * 
     * @param string Nome do projeto a ser removido
     */
    @Override
    public void removeProject(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Null argument!");
        }

        int pos = -1, i = 0;

        while (pos == -1 && i < numberOfprojects) {

            if (projects[i].getName().equals(string)) {
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
    
    /* Obtém um projeto da edição com base no seu nome
     * @param name Nome do projeto a ser obtido
     * @throws IllegalArgumentException lançada se não existir projeto
=======

    /*Tipo um find para encontrar o projeto atraves do nome dado no parametro?
     * This method searches for a project in the Porject's list
     *
     * @param name Project's name
     * @throws IllegalArgumentException if it does not find a project
>>>>>>> 8801e3c40a307620edba7043871a6c16c231cc20
     */
    @Override
    public Project getProject(String name) {
        for (int i = 0; i < numberOfprojects; i++) {
            if (projects[i].getName().equals(name)) {
                return projects[i];
            }
        }
        throw new IllegalArgumentException("Project name is null or the project dont exist");

    }

    /*
     * Essa função lista todos os projetos
     */
    @Override
    public Project[] getProjects() {

        Project[] tempProj = new Project[numberOfprojects];

        for (int i = 0; i < this.numberOfprojects; i++) {
            tempProj[i] = this.projects[i];
        }
        return tempProj;
    }

    /*
     * Obtém todos os projetos com uma tag específica 
     * @param tag Especifíca a tag
     * @return o projeto com a tag correspondente
     */
    @Override
    public Project[] getProjectsByTag(String tag) {
        int counter = 0;
        Project[] tempProjTags = new Project[numberOfprojects];
        for (int i = 0; i < numberOfprojects; i++) {
            if (projects[i].hasTag(tag)) {
                tempProjTags[counter] = projects[i];
                counter++;
            }
        }
        if (counter == 0) {
            return null;
        }
        if (counter != numberOfprojects) {
            Project[] fullArray = new Project[counter];
            for (int i = 0; i < counter; i++) {
                fullArray[i] = tempProjTags[i];
                return fullArray;
            }
        }
        return tempProjTags;
    }

    /* 
     * Obtém todos os projetos com um participante específico
     * @param string Participante específico
     * @return todos os projetos com um participante
     */
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

    /*
     * Verifica se a edição é igual a outro objeto
     * @param obj Objeto a ser comparado
     * @return Retorna true se a edição for igual ao objeto, caso contrário, retorna false
     */

    public static Edition fromJsonObj(JSONObject jsonObject) {

        String name = (String) jsonObject.get("name");
        LocalDate start = LocalDate.parse((String) jsonObject.get("start"));
        LocalDate end = LocalDate.parse((String) jsonObject.get("end"));
        Status status = Status.valueOf(((String) jsonObject.get("status")).toUpperCase());
        String projectTemplate = (String) jsonObject.get("projectTemplate");

        ImpEdition edition = new ImpEdition(name, status, start, end, projectTemplate);

        JSONArray projectsArray = (JSONArray) jsonObject.get("projects");

        for (int i = 0; i < projectsArray.size(); i++) {
            try {

                JSONObject projectJson = (JSONObject) projectsArray.get(i);

                edition.addProjectFormImport(ImpProject.fromJsonObj(projectJson));
            } catch (IllegalArgumentException e) {

            }
        }

        return edition;

    }

    /**
     * This method adds
     *
     * @param p
     */
    private void addProjectFormImport(Project p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (existsProject(p)) {
            throw new IllegalArgumentException("Project already exists");
        }
        if (numberOfprojects == projects.length) {
            realloc();
        }

        projects[numberOfprojects++] = p;
    }

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
