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
import ma02_resources.project.Edition;


/**
 *
 * Classe que implementa a interface CBLInterface e representa a implementação do CBL (Curso Base de Lógica)
 */
public class ImpCBL implements CBLinterface{
    
    
    private Edition[] editions;
    private int numOfEditions;
    
    /**
     * Construtor que cria uma instância de ImpCBL com um tamanho inicial especificado para o array de edições
     * @param size Tamanho inicial do array de edições
     */
    public ImpCBL(int size){
        this.editions = new Edition[size];
        this.numOfEditions = 0;
    }
    
    /**
     * Construtor que cria uma instância de ImpCBL com um tamanho inicial padrão para o array de edições (1)
     */
    public ImpCBL(){
        this.editions = new Edition[1];
        this.numOfEditions = 0;
    }
    /**
     * Função para realocar o tamanho do array destinado a edições, caso esteja cheio
     */
    public void realloc(){
        Edition[] tempEdition = new Edition[this.editions.length * 2];
        for(int i = 0; i < this.numOfEditions; i++){
            tempEdition[i] = editions[i];
        }
        editions = tempEdition;
    }
    /***
     * Função para procurar uma edição num array de edições
     * @param edition especificar edição que queremos procurar no array de Edições 
     * @return retorna true se existir e false se nao existir
     */
    public boolean existEdition(Edition edition){
        for(int i = 0; i < this.numOfEditions; i++){
            if(editions[i] != null && editions[i].equals(edition)){
                return true;
            }
        }
        return false;
    }
    

    /**
     * Função que adiciona uma nova edição ao CBL
     * @param edition Edição a ser adicionada
     * @throws EditionAlreadyExist Lançada se a edição já existe no CBL
     */
    @Override
    public void addEdition(Edition edition) throws EditionAlreadyExist {
        if(this.numOfEditions == editions.length){
            realloc();
        }
        if(existEdition(edition)){
            throw new EditionAlreadyExist("Edition Already exist in the CBL");
        }
        this.editions[numOfEditions] = edition;
        numOfEditions++;
    }

    /**
     * Remove uma edição do CBL com base no nome da edição
     * @param name Nome da edição a ser removida
     * @return Retorna a edição removida
     * @throws EditionDontExist Lançada se a edição não existe no CBL
     */
    @Override
    public Edition removeEdition(String name) throws EditionDontExist {
        Edition removed = null;
        int exist = 0;
        for(int i = 0; i < this.numOfEditions; i++){
            if(editions[i].getName().equals(name)){
                removed = this.editions[i];
                exist = i;
            }
        }
        if(exist == 0){
            throw new EditionDontExist("Edition dont exist in the CBL");
        }
        for(int j = exist; j < numOfEditions; j++){
            editions[j] = editions[j+1];
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
     * @param name Nome da edição a ser ativada
     */
    @Override
    public void activateEdition(String name) {
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
    
}
