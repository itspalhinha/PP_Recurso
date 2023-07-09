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
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Student;
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

    /*
     * Este método ativa a edição com o nome especificado
     * @param name Nome da edição a ser ativada
     * @throws IllegalArgumentException se a edição não for encontrada
     */
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

    /*
     * Obtém o número de edições
     * @return Número de edições
     */
    @Override
    public int getNumberOfEditions() {
        return this.numOfEditions;
    }

    /*
     * Obtém as edições em que o participante especificado está envolvido
     * @param p Participante para o qual se deseja obter as edições
     * @return Um array contendo as edições em que o participante está envolvido
     * @throws NullPointerException se o participante não estiver envolvido em nenhuma das edições
     */
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

    /*
     * Exporta os dados do sistema para um arquivo JSON
     * @param filePath Caminho do arquivo JSON de destino
     * @return true se a exportação for bem sucedida, false caso contrário
     * @throws UnsupportedOperationException se a operação não for suportada
     */
    @Override
    public boolean exportJSON(String filePath) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("numberOfEditions", numOfEditions);

        JSONArray editionsArray = new JSONArray();

        for (int i = 0; i < numOfEditions; i++) {
            try {
                editionsArray.add(((ImpEdition) editions[i]).toJsonObj());
            } catch (NullPointerException e) {
            }
        }

        jsonObject.put("editions", editionsArray);

        try ( FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
            //System.out.println("Exported to JSON file: " + filePath);
        } catch (IOException e) {
            e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    public boolean importDataJSON(String filePath) {
        JSONParser parser = new JSONParser();

        try ( FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray editionsArray = (JSONArray) jsonObject.get("editions");

            for (int i = 0; i < editionsArray.size(); i++) {
                JSONObject editionJson = (JSONObject) editionsArray.get(i);
                try {
                    this.addEdition(ImpEdition.fromJsonObj(editionJson));
                } catch (EditionAlreadyExist ex) {

                }
            }

            return true;

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IO exception");
        } catch (ParseException ex) {
            System.out.println("Parce exception");
        }
        return false;
    }

    /*
     * Importa dados de um arquivo CSV para a classe atual, em que o formato e a estrutura do arquivo
     * CSV devem ser compatíveis com as necessidades da classe
     * @param filePath Caminho do arquivo CSV a ser importado
     * @return True se a importação for bem sucedida, false caso contrário
     * @throws UnsupportedOperationException se a funcionalidade de importação de dados não for suportada
     * @throws IOException se ocorrer um erro durante a leitura ou processamento do arquivo CSV
     */
    @Override
    public boolean importDataCSV(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /*
     * Exporta os dados da classe atual para um arquivo CSV no caminho especificado
     * @param filePath Caminhodo arquivo CSV de destino
     * @return True se a exportação for bem sucedida, false caso contrário
     * @throws UnsupportedOperationException se a funcionalidade de exportação de dados não for suportada
     */
    

    /*
     * Retorna um array contendo todas as edições disponíveis
     * @return Um array de objetos Edition contendo todas as edições disponíveis
     * @throws UnsupportedOperationException se a funcionalidade não for suportada
     */
    @Override
    public Edition[] getEditions() {
        Edition temp[] = new Edition[numOfEditions];

        for (int i = 0; i < numOfEditions; i++) {
            temp[i] = editions[i];
        }

        return temp;
    }

    /*
     * Retorna um array de projetos nos quais o participante especificado está envolvido
     * @param participant Participante para o qual deseja-se obter os projetos
     * @return Um array de objetos Project contendo os projetos nos quais o participante está envolvido
     */
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

    
    
    
    @Override
    public boolean exportToCSV(String filename) {
        try {
            // ... Existing code ...
            FileWriter writer = new FileWriter(filename);

            // Write header
            writer.write("Edition Name; Status; Start Date; End Date; ProjectTemplate; Number Of Projects;"
                    + " Project Name; Description; Tags; Number of Participants;Number of Facilitators;Number of Students; Number of Partners; Number of Tasks; "
                    + "Task Title; Task Description; Start date; End Date; Number of Submissions; "
                    + "Submission Date; Student email; Submission Text;"
                    + "Participant Name; Email; Type; SelfEvaluation; HeteroEvaluation\n");

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

                    for (int m = 0; m < project.getNumberOfParticipants(); m++) {
                        Participant participant = ((ImpProject) project).getParticipants()[m];
                        writer.write(participant.getName() + ";");
                        writer.write(participant.getEmail() + ";");
                        
                        if (participant instanceof Facilitator) {
                            writer.write("Facilitator" + ";");
                        } else if (participant instanceof Student) {
                            writer.write("Student" + ";");
                            try {
                                Evaluation evaluation = ((ImpProject) project).evaluationOf((Student) participant);
                                try {
                                    writer.write( evaluation.getSelfEvaluation()+ ";");
                                } catch (NullPointerException ex){
                                    writer.write(";");
                                }
                                 try {
                                    writer.write( evaluation.getHeteroevaluation()+ ";\n");
                                } catch (NullPointerException ex){
                                    writer.write(";");
                                }
                            } catch (NullPointerException ignored) {

                            }

                        } else {
                            writer.write("Partner" + ";");
                        }
                        writer.write("\n ;;;;;;;;;;;;;;;;;;;;;");
                    }

                    writer.write("\n ;;;;;;");
                }
                //proxima linha
                writer.write("\n");
            }

            writer.close();
            System.out.println("CSV file has been exported successfully.");
            
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while exporting to CSV: " + e.getMessage());
            return false;
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
