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

import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * Classe que implementa a interface CBLInterface e representa a implementação
 * do CBL (Curso Base de Lógica)
 */
public class ImpCBL implements CBLinterface {

    private Edition[] editions;
    private int numOfEditions;

    /**
     * Construtor que cria uma instância de ImpCBL com um tamanho inicial
     * especificado para o array de edições
     *
     * @param size Tamanho inicial do array de edições
     */
    public ImpCBL(int size) {
        this.editions = new Edition[size];
        this.numOfEditions = 0;
    }

    /**
     * Construtor que cria uma instância de ImpCBL com um tamanho inicial padrão
     * para o array de edições (1)
     */
    public ImpCBL() {

        this.editions = new Edition[1];
        this.numOfEditions = 0;
    }

    /**
     * Função para realocar o tamanho do array destinado a edições, caso esteja
     * cheio
     */
    public void realloc() {
        Edition[] tempEdition = new Edition[this.editions.length * 2];
        for (int i = 0; i < this.numOfEditions; i++) {
            tempEdition[i] = editions[i];
        }
        editions = tempEdition;
    }

    /**
     * *
     * Função para procurar uma edição num array de edições
     *
     * @param edition especificar edição que queremos procurar no array de
     * Edições
     * @return retorna true se existir e false se nao existir
     */
    public boolean existEdition(Edition edition) {
        for (int i = 0; i < this.numOfEditions; i++) {
            if (editions[i] != null && editions[i].equals(edition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Função que adiciona uma nova edição ao CBL
     *
     * @param edition Edição a ser adicionada
     * @throws EditionAlreadyExist Lançada se a edição já existe no CBL
     */
    @Override
    public void addEdition(Edition edition) throws EditionAlreadyExist {
        if (this.numOfEditions == editions.length) {
            realloc();
        }
        if (existEdition(edition)) {
            throw new EditionAlreadyExist("Edition Already exist in the CBL");
        }
        this.editions[numOfEditions] = edition;
        numOfEditions++;
    }

    /**
     * Remove uma edição do CBL com base no nome da edição
     *
     * @param name Nome da edição a ser removida
     * @return Retorna a edição removida
     * @throws EditionDontExist Lançada se a edição não existe no CBL
     */
    @Override
    public Edition removeEdition(String name) throws EditionDontExist {
        Edition removed = null;
        int exist = 0;
        for (int i = 0; i < this.numOfEditions; i++) {
            if (editions[i].getName().equals(name)) {
                removed = this.editions[i];
                exist = i;
            }
        }
        if (exist == 0) {
            throw new EditionDontExist("Edition dont exist in the CBL");
        }
        for (int j = exist; j < numOfEditions; j++) {
            editions[j] = editions[j + 1];
        }
        editions[--numOfEditions] = null;
        return removed;
    }

    /*
     * Obtém uma edição do CBL com base no nome da edição
     * @param name Nome da edição a ser obtida
     * @return Retorna a edição encontrada ou null se não existir
     */
    @Override
    public Edition getEdition(String name) {
        for (int i = 0; i < this.numOfEditions; i++) {
            if (editions[i].getName().equals(name)) {
                return editions[i];
            }
        }
        throw new IllegalArgumentException("Edition not found in CBL");
    }

    @Override
    public void activateEdition(String name) throws IllegalArgumentException {
        int pos = -1;
        Edition edition = new ImpEdition(name, null, null);

        for (int j = 0; j < numOfEditions; j++) {
            if (this.editions[j] != null && this.editions[j].getStatus() == Status.ACTIVE) {
                pos = j;
            }
        }

        boolean cmp = false;
        int i = 0;

        while (!cmp && i < numOfEditions) {
            if (editions[i].equals(edition)) {
                if (pos != -1) {

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
        return this.numOfEditions;
    }

    @Override
    public Edition[] getEditionsByParticipant(Participant p) {
        int counter = 0;

        Edition[] temp = new Edition[numOfEditions];
        boolean hasP = false;
        for (int i = 0; i < numOfEditions; i++) {
            hasP = false;
            for (Project project : editions[i].getProjects()) {
                if (!hasP) {
                    try {
                        Participant participant = project.getParticipant(p.getEmail());
                        if (participant != null) {
                            hasP = true;
                        }

                    } catch (IllegalArgumentException e) {
                    }
                }
            }
            if (hasP) {
                temp[counter++] = editions[i];
            }
        }
        if (counter == 0) {
            throw new NullPointerException("User does not participate in any of the editions");
        }
        //limit the array to just the not null positions
        if (counter != numOfEditions) {
            Edition[] editionsByParticipant = new Edition[counter];

            for (int i = 0; i < counter; i++) {
                editionsByParticipant[i] = temp[i];
            }
            return editionsByParticipant;
        }
        return temp;
    }

    @Override
    public boolean exportJSON(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*
    @Override
public Edition getEdition(String name) {
for (int i = 0; i < this.numOfEditions; i++) {
if (editions[i].getName().equals(name)) {
return editions[i];
}
}
return null;
}
     */
    /**
     * Ativa uma edição do CBL com base no nome da edição
     *
     * @param name Nome da edição a ser ativada
     */
    @Override
    public boolean importDataJSON(String filePath) {
        return false;
    }

    @Override
    public boolean importDataCSV(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*
    @Override
public void activateEdition(String name) {
for (int i = 0; i < this.numOfEditions; i++) {
if (editions[i].getName().equals(name)) {
editions[i].activate();
}
}
}
     */
    @Override
    public boolean exportCSV(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Edition[] getEditions() {
        Edition temp[] = new Edition[numOfEditions];

        for (int i = 0; i < numOfEditions; i++) {
            temp[i] = editions[i];
        }

        return temp;
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

    public void exportToCSV(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);

            // Write header
            writer.write("Edition Name; Status; Start Date; End Date; ProjectTemplate; Number Of Projects;"
                    + " Project Name; Description; Tags; Number of Participants;Number of Facilitators;Number of Students; Number of Partners; Number of Tasks; "
                    + "Task Title; Task Description; Start date; End Date; Number of Submissions; "
                    + "Submission Date; Student Name; Submission Text\n");

            // Write data for each edition, project, task, and submission
            for (int i = 0; i < numOfEditions; i++) {
                Edition edition = editions[i];
                writer.write(edition.getName() + ";");
                writer.write(edition.getStatus().toString() + ";");
                writer.write(edition.getStart() + ";");
                writer.write(edition.getEnd() + ";");
                writer.write(edition.getProjectTemplate() + ";");
                writer.write(edition.getNumberOfProjects() + ";");

                for (int j = 0; j < edition.getNumberOfProjects(); j++) {
                    Project project = edition.getProject(edition.getProjects()[j].getName());

                    writer.write(project.getName() + ";");
                    writer.write(project.getDescription() + ";");
                    String tags = "";
                    for (int a = 0; a < project.getTags().length; a++) {
                        tags += " " + project.getTags()[a];
                    }
                    writer.write(tags + ";");
                    writer.write(project.getNumberOfParticipants() + "/" + project.getMaximumNumberOfParticipants() + ";");
                    writer.write(project.getNumberOfFacilitators() + "/" + project.getMaximumNumberOfFacilitators() + ";");
                    writer.write(project.getNumberOfStudents() + "/" + project.getMaximumNumberOfStudents() + ";");
                    writer.write(project.getNumberOfPartners() + "/" + project.getMaximumNumberOfPartners() + ";");
                    writer.write(project.getNumberOfTasks() + "/" + project.getMaximumNumberOfTasks() + ";");

                    for (int k = 0; k < project.getNumberOfTasks(); k++) {
                        Task task = project.getTask(project.getTasks()[k].getTitle());
                        writer.write(task.getTitle() + ";");
                        writer.write(task.getDescription() + ";");
                        writer.write(task.getStart() + ";");
                        writer.write(task.getEnd() + ";");
                        writer.write(task.getNumberOfSubmissions() + ";");

                        Submission[] submissions = task.getSubmissions();
                        for (int l = 0; l < task.getNumberOfSubmissions(); l++) {

                            writer.write(submissions[l].getDate() + ";");
                            writer.write(submissions[l].getStudent().getEmail() + ";");
                            writer.write(submissions[l].getText());
                            writer.write("\n ;;;;;;;;;;;;;;;;;;;");
                        }
                        writer.write("\n ;;;;;;;;;;;;;;");
                    }
                    writer.write("\n ;;;;;;");
                }
                //proxima linha
                writer.write("\n");
            }

            writer.close();
            System.out.println("CSV file has been exported successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while exporting to CSV: " + e.getMessage());
        }
    }

    @Override
    public Edition[] uncompletedEditions() {
        int counter = 0;
        Edition[] uncompletedEditions = new Edition[numOfEditions];
        boolean hasIncompleteProject = false;

        for (int i = 0; i < numOfEditions; i++) {
            hasIncompleteProject = false;

            if (editions[i].getNumberOfProjects() == 0) {
                hasIncompleteProject = true;
            } else {
                Project[] uncompletedProjects = ((ImpEdition) editions[i]).getUncompletedProjects();
                if (uncompletedProjects != null) {
                    hasIncompleteProject = true;
                }
            }
            //if it found at least 1 uncompleted project, adds the edition to the uncompletedEditions array
            if (hasIncompleteProject) {
                uncompletedEditions[counter++] = editions[i];
            }
        }
        if (counter == 0) {
            throw new NullPointerException("None of the editions are uncompleted!");
        }
        //limit the array to just the not null posicions
        if (counter != numOfEditions) {
            Edition[] trimmedUncompleted = new Edition[counter];

            for (int i = 0; i < counter; i++) {
                trimmedUncompleted[i] = uncompletedEditions[i];
            }
            return trimmedUncompleted;
        }
        return uncompletedEditions;
    }

}
