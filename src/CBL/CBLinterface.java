/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CBL;

import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import ma02_resources.project.Edition;

/**
 *
 * @author rafas
 */
public interface CBLinterface {
    
    /**
     * 
     * @param edition especificar a edição para adicionar na CBL
     * @throws EditionAlreadyExist exceção criada para caso a exceção exista no CBL
     */
    public void addEdition(Edition edition) throws EditionAlreadyExist;
    
    /**
     * 
     * @param name especificar o nome da edição para remover na CBL
     * @throws EditionDontExist exceção criada para edições que nao existem no CBL
     */
    public Edition removeEdition(String name) throws EditionDontExist;
    
    /**
     * Funçao que retorna uma edição
     * @param name nome da edição que pretendemos receber
     * @return 
     */
    public Edition getEdition(String name);

    /**
     * Função para mudar o estado de uma edição
     * @param name nome da edição para ativar
     */
    public void activateEdition(String name);
    
}
