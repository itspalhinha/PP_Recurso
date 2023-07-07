/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CBL;

import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import ma02_resources.participants.Participant;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;

/**
 *
 * @author rafas
 */
public interface CBLinterface {
    
    /**
     * Adiciona uma edição no CBL
     * @param edition especificar a edição para adicionar na CBL
     * @throws EditionAlreadyExist exceção criada para caso a exceção exista no CBL
     */
    public void addEdition(Edition edition) throws EditionAlreadyExist;
    
    /**
     * Remove uma edição no CBL
     * @param name especificar o nome da edição para remover na CBL
     * @throws EditionDontExist exceção criada para edições que nao existem no CBL
     */
    public Edition removeEdition(String name) throws EditionDontExist;
    
    /**
     * Funçao que retorna uma edição
     * @param name nome da edição que pretendemos receber
     * @return true se der certo e false se nao der
     */
    public Edition getEdition(String name);

    /**
     * Função para mudar o estado de uma edição
     * @param name nome da edição para ativar
     */
    public void activateEdition(String name) throws IllegalArgumentException;
    
    /**
     * Este metodo retorna o numero de Edições
     * @return retorna o numero de edições
     */
     public int getNumberOfEditions();

    /**
     * Retorna o numero de edições do participante especificado
     *
     * @param p Participante a ser procurado
     * @return Um array de edições do participante
     * @throws NullPointerException se nao existir nenhuma edição
     */
    public Edition[] getEditionsByParticipant(Participant p);

    /**
     * Este metodo exporta a data do CBL para um ficheiro JSON no especificado 
     * caminho. O ficheiro contém toda informação sobre as edições, task, 
     * participantes e submissoes
     * @param filePath caminho do ficheiro JSON
     * @return true se o export der certo e false se nao der certo
     */
    public boolean exportJSON(String filePath);

    /**
     * Importa data de um ficheiro JSON localizado no caminho especifico
     * O metodo lê o ficheiro JSON, e adiciona as edições para o objeto
     * @param filePath caminho para o ficheiro JSON
     * @return true se o import der certo e false se nao der
     */
    public boolean importDataJSON(String filePath);
    
    
    /***
     * Importa data de um ficheiro CSV localizado no caminho especifico
     * O metodo lê o ficheiro CSV, e adiciona as edições para o objeto
     * @param filePath caminho para o ficheiro CSV
     * @return true se o import der certo e false se nao der
     */
    public boolean importDataCSV(String filePath);
    
    /***
     * Este metodo exporta a data do CBL para um ficheiro CSV no especificado 
     * caminho. O ficheiro contém toda informação sobre as edições, task, 
     * participantes e submissoes
     * @param filePath caminho do ficheiro CSV
     * @return true se o export der certo e false se nao der certo
     */
    public boolean exportCSV(String filePath);
    
    /**
     * Este método retorna um array de objetos Edition representando as edições disponíveis.
     *
     * @return Um array de Edições
     */
    public Edition[] getEditions();

    /**
     * Retorna um array de Edições onde o participante esta envolvido
     *
     * @param participant O participante que queremos procurar
     * @return Um array de Edições onde o participante esta envolvido
     */
    public Project[] getProjectsOf(Participant participant);
    
    public Edition[] uncompletedEditions();
    
}
