/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CBL;

import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import ma02_resources.project.Edition;

/**
 *
 * @author rafas
 */
public class ImpCBL implements CBLinterface{
    
    private Edition[] editions;
    private int numOfEditions;
    
    public ImpCBL(int size){
        this.editions = new Edition[size];
        this.numOfEditions = 0;
    }
    public ImpCBL(){
        this.editions = new Edition[1];
        this.numOfEditions = 0;
    }
    /**
     * Função para realocar o tamanho do array destinado a edições
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

    @Override
    public Edition getEdition(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void activateEdition(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
