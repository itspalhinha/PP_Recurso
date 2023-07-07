/*
 * Nome: Rafael Filipe Silva Medina Coronel
 * Número: 8190348
 * Turma: LSIRCT1
 *
 * Nome: Roger Seiji Hernandez Nakauchi
 * Número: 8210005
 * Turma: LSIRCT1
 */
package Managers;

import Exceptions.AlreadyExistsInArray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import ma02_resources.participants.Facilitator;

import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Participant.ImpParticipant;

public class ParticipantsManager {

    /**
     * Array que armazena participantes
     */
    private static Participant[] participantsList;
    /**
     * Contador de participantes
     */
    private static int participantsCounter;

    
    /**
     * Método construtor para ParticipantsManager
     */
    public ParticipantsManager() {
        participantsCounter = 0;
        participantsList = new Participant[10];
    }

    /**
     * Obtém o número de participantes existentes
     * @return Número de participantes existentes
     */
    public int getParticipantsCounter() {
        return participantsCounter;
    }

    /**
     * Adiciona espaço à lista de participantes caso esteja cheia
     * 
     */
    private void realloc() {
        Participant[] temp = new Participant[participantsList.length * 2];
        int i = 0;
        for (Participant p : participantsList) {
            temp[i++] = p;
        }
        participantsList = temp;
    }

    /**
     * Este método verifica se um participante já existe na lista 
     * @param p Participante a ser verificado
     * @return true se existir, false caso contrário
     */
    public boolean hasParticipant(Participant p) {
        for (Participant participant : participantsList) {
            if (participant != null && participant.equals(p)) {
                return true;
            }
        }
        return false;
    }

//    public int findParticipant(Participant p) {
//        int i=0;
//        for (Participant participant : participantsList) {
//            if (participant.equals(p)) {
//                return i;
//            }
//            i++;
//        }
//        return -1;
//    }
    
    /**
     * Essa função adiciona um participante à lista, verificando se o participante já existe e
     * se o array está cheio 
     * @param p Participante a ser adicionado
     * @throws AlreadyExistsInArray se o participante já existir
     */
    public void addParticipant(Participant p) throws AlreadyExistsInArray {

        if (hasParticipant(p)) {
            throw new AlreadyExistsInArray("Participant with same email already exists!");
        }
        if (participantsCounter == participantsList.length) {
            realloc();
        }
        participantsList[participantsCounter++] = p;
    }

    /**
     * Essa função remove um participante da lista
     * @param string Nome do participante a ser removido
     * @return O participante removido
     */
    public Participant removeParticipant(String string) {
        Participant deleted = new ImpParticipant(null, string, null, null) {};
        int pos = -1, i = 0;
        while (pos == -1 && i < participantsCounter) {

            if (participantsList[i].equals(deleted)) {
                pos = i;
                deleted = participantsList[i];
            } else {
                i++;
            }
        }
        if (pos == -1) {
            throw new IllegalArgumentException("No Participant found!");
        }
        for (i = pos; i < participantsCounter; i++) {
            participantsList[i] = participantsList[i + 1];
        }
        participantsList[--participantsCounter] = null;
        return deleted;
    }

    /**
     * Obtém um participante da lista pelo nome
     * @param string Nome do participante a ser pesquisado
     * @return O participante encontrado
     * @throws IllegalArgumentException se o participante não for encontrado
     */
    public Participant getParticipant(String string) throws IllegalArgumentException {
        Participant p = new ImpParticipant(null, string, null, null) {};

        for (int i = 0; i < participantsCounter; i++) {

            if (participantsList[i].equals(p)) {
                p = participantsList[i];
                return p;
            }
        }
        throw new IllegalArgumentException("No Participant found!");

    }

//    public Participant[] getParticipants() {
//        int counter = 0;
//        Participant temp[] = new Participant[participantsCounter];
//
//        for (int i = 0; i < participantsCounter; i++) {
//            temp[counter++] = participantsList[i];
//        }
//        if (counter == participantsCounter) {
//            return temp;
//        }
//
//        Participant trimmedTemp[] = new Participant[counter];
//        for (int i = 0; i < counter; i++) {
//            trimmedTemp[i] = temp[i];
//        }
//        return trimmedTemp;
//    }
    
    
    /*
     * Obtém o número de facilitadores
     * @return Número de facilitadores
     */
    public int getNumberOfFacilitators() {
        int numberOfFacilitators = 0;
        for (int i = 0; i < participantsCounter; i++) {
            if (participantsList[i] instanceof Facilitator) {
                numberOfFacilitators++;
            }
        }
        return numberOfFacilitators;
    }

    /*
     * Obtém o número de estudantes
     * @return Número de estudantes
     */
    public int getNumberOfStudents() {
        int numberOfStudents = 0;
        for (int i = 0; i < participantsCounter; i++) {
            if (participantsList[i] instanceof Student) {
                numberOfStudents++;
            }
        }
        return numberOfStudents;
    }

    /*
     * Obtém o número de parceiros
     * @return Número de parceiros
     */
    public int getNumberOfPartners() {
        int numberOfPartners = 0;
        for (int i = 0; i < participantsCounter; i++) {
            if (participantsList[i] instanceof Partner) {
                numberOfPartners++;
            }
        }
        return numberOfPartners;
    }

    /**
     * Obtém os dados de todos os participantes, ordenados por facilitadores, estudantes e parceiros 
     * @return Çista com os dados
     */
    public Participant[] getParticipants() {
        int counter = 0;

        Participant[] temp = new Participant[participantsCounter];

        for (int i = 0; i < participantsCounter; i++) {
            if (participantsList != null) {
                temp[counter++] = participantsList[i];
            }
        }

        Participant trimmedTemp[] = new Participant[counter];
        int j = 0;
        counter = 0;

        while (j < getNumberOfFacilitators() && counter < trimmedTemp.length) {
            for (int i = 0; i < trimmedTemp.length; i++) {
                if (temp[i] instanceof Facilitator) {
                    trimmedTemp[counter++] = temp[i];
                    j++;
                }
            }
        }

        j = 0;
        while (j < getNumberOfStudents() && counter < trimmedTemp.length) {
            for (int i = 0; i < trimmedTemp.length; i++) {
                if (temp[i] instanceof Student) {
                    trimmedTemp[counter++] = temp[i];
                    j++;
                }
            }
        }

        j = 0;
        while (j < getNumberOfPartners() && counter < trimmedTemp.length) {
            for (int i = 0; i < trimmedTemp.length; i++) {
                if (temp[i] instanceof Partner) {
                    trimmedTemp[counter++] = temp[i];
                    j++;
                }
            }
        }

        return trimmedTemp;
    }

    /**
     * Obtém todos os estudantes
     * @return Um array com todos os estudantes
     * @throws NullPointerException se não forem encontrados estudantes
     */
    public Student[] getStudents() {
        int counter = 0;

        if (getNumberOfStudents() > 0) {
            Student[] temp = new Student[getNumberOfStudents()];
            for (int i = 0; i < participantsCounter; i++) {
                if (participantsList[i] instanceof Student) {
                    temp[counter++] = (Student) participantsList[i];
                }
            }
            return temp;
        }
        return null;
    }

    /**
     * Exporta os dados dos participantes para um arquivo JSON
     * @param filePath O caminho do arquivo de destino
     * @return true se a exportação for bem sucedida, false caso contrário
     */
    public boolean export(String filePath) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("participantsCounter", participantsCounter);

        JSONArray participantsArray = new JSONArray();
        for (int i = 0; i < participantsCounter; i++) {
            try {
                participantsArray.add(((ImpParticipant) participantsList[i]).toJsonObj());
            } catch (NullPointerException e) {
            }
        }
        jsonObject.put("participants", participantsArray);

        try ( FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {

            return false;
        }
        return true;
    }

    /**
     * Essa função importa dados de um arquivo JSON
     * 
     * @param filePath O caminho do arquivo JSON de origem
     * @return true se a importação for bem sucedida, false caso contrário
     */
    public boolean importData(String filePath) {
        JSONParser parser = new JSONParser();

        try ( FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray participantsArray = (JSONArray) jsonObject.get("participants");
            for (int i = 0; i < participantsArray.size(); i++) {
                try {
                    JSONObject participantJson = (JSONObject) participantsArray.get(i);
                    Participant p = ImpParticipant.fromJsonObj(participantJson);
                    this.addParticipant(p);
                } catch (AlreadyExistsInArray e) {

                }
            }
            return true;

        } catch (FileNotFoundException ex) {
            System.out.println("File");
        } catch (IOException ex) {
            System.out.println("2");
        } catch (ParseException ex) {
            System.out.println("3");
        }
        return false;

    }
}
