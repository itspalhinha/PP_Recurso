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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import ma02_resources.participants.Instituition;
import ma02_resources.project.exceptions.InstituitionAlreadyExistException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Participant.ImpInstituition;

public class InstituitionsManager {

    /**
     * Array que armazena instituições
     */
    private static Instituition[] instituitionsList;
    /**
     * Contador de instituições
     */
    private static int instituitionsCounter;
    
    /**
     * Método construtor de InstituitionsManager
     */
    public InstituitionsManager() {
        instituitionsCounter = 0;
        instituitionsList = new Instituition[10];
    }

    /**
     * Obtém o número de instituições existentes
     * @return Número de instituições existentes
     */
    public int getInstituitionsCounter() {
        return instituitionsCounter;
    }

    /**
     * Adiciona mais espaço à lista de instituições caso esteja cheia
     * 
     */
    private void realloc() {
        Instituition[] temp = new Instituition[instituitionsList.length * 2];
        int i = 0;
        for (Instituition p : instituitionsList) {
            temp[i++] = p;
        }
        instituitionsList = temp;
    }

    /**
     * Verifica se uma instituição existe na lista 
     * @param p Instituição a ser verificada
     * @return true se existir, false caso contrário
     */
    public boolean hasInstituition(Instituition p) {
        for (Instituition instituition : instituitionsList) {
            if (instituition != null && instituition.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método adiciona uma instituição à lista, verificando se a instituição já existe
     * e se o array está cheio 
     * @param p Instituição a ser adicionada
     * @throws InstituitionAlreadyExistException se a instituição já existir
     */
    public void addInstituition(Instituition p) throws InstituitionAlreadyExistException {

        if (hasInstituition(p)) {
            throw new InstituitionAlreadyExistException("Instituition with same name already exists!");
        }
        if (instituitionsCounter == instituitionsList.length) {
            realloc();
        }
        instituitionsList[instituitionsCounter++] = p;
    }

    
    /**
     * Essa função remove uma instituição da lista 
     * @param string Nome da instituição a ser removida
     * @return A instituição removida
     */
    public Instituition removeInstituition(String string) {
        Instituition deleted = new ImpInstituition(string, null, null, null, null, null);
        int pos = -1, i = 0;
        while (pos == -1 && i < instituitionsCounter) {

            if (instituitionsList[i].equals(deleted)) {
                pos = i;
                deleted = instituitionsList[i];
            } else {
                i++;
            }
        }
        if (pos == -1) {
            throw new IllegalArgumentException("No Instituition found!");
        }
        for (i = pos; i < instituitionsCounter; i++) {
            instituitionsList[i] = instituitionsList[i + 1];
        }
        instituitionsList[--instituitionsCounter] = null;
        return deleted;
    }

    /**
     * Obtém uma instituição da lista pelo nome
     * @param string Nome a ser pesquisado
     * @return A instituição encontrada
     * @throws IllegalArgumentException se a instituição não for encontrada
     */
    public Instituition getInstituition(String string) throws IllegalArgumentException {
        Instituition p = new ImpInstituition(string, null, null, null, null, null);

        for (int i = 0; i < instituitionsCounter; i++) {

            if (instituitionsList[i].equals(p)) {
                p = instituitionsList[i];
                return p;
            }
        }
        throw new IllegalArgumentException("No Instituition found!");
    }

    /**
     * Obtém todas as instituições da lista 
     * @return Um array com todas as instituições
     * @throws NullPointerException se não houver instituição na lista
     */
    public Instituition[] getInstituitions() throws NullPointerException {
        if (instituitionsCounter > 0){
            Instituition temp[] = new Instituition[instituitionsCounter];

            for (int i = 0; i < instituitionsCounter; i++) {
                temp[i] = instituitionsList[i];
            }
            return temp;  
        } else {
            throw new NullPointerException("No instituitions found!");
        }
        

    }

    /**
     * Exporta os dados das instituições para um arquivo JSON
     * 
     * @param filePath O caminho do arquivo JSON de destino
     * @return true se a exportação for bem sucedida, false caso contrário
     */
    public boolean export(String filePath) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("instituitionsCounter", instituitionsCounter);

        JSONArray instituitionsArray = new JSONArray();
        for (int i = 0; i < instituitionsCounter; i++) {
            try {
                instituitionsArray.add(((ImpInstituition) instituitionsList[i]).toJsonObj());
            } catch (NullPointerException e) {
            }
        }
        jsonObject.put("instituitions", instituitionsArray);

        try ( FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Importa dados de um arquivo JSON para a lista de instituições 
     * @param filePath O caminho do arquivo JSON de origem
     * @return true se a importação for bem sucedida, false caso contrário
     */
    public boolean importData(String filePath) {
        JSONParser parser = new JSONParser();

        try ( FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray instituitionsArray = (JSONArray) jsonObject.get("instituitions");
            for (int i = 0; i < instituitionsArray.size(); i++) {
                try {
                    JSONObject instituitionsJson = (JSONObject) instituitionsArray.get(i);
                    Instituition p = ImpInstituition.fromJsonObj(instituitionsJson);
                    this.addInstituition(p);
                } catch (InstituitionAlreadyExistException e) {

                }
            }
            return true;

        } catch (IOException | ParseException ex) {
            return false;
        }

    }
}
