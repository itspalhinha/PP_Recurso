/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import CBL.CBLinterface;
import CBL.ImpCBL;
import Exceptions.AlreadyExistsInArray;
import Managers.InstituitionsManager;
import Managers.ParticipantsManager;
import Participant.ImpContact;
import Participant.ImpFacilitator;
import Participant.ImpParticipant;
import Participant.ImpPartner;
import Participant.ImpStudent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;

/**
 *
 * @author rafas
 */
public class Menu {

    private BufferedReader reader;
    private CBLinterface cbl;
    private ParticipantsManager pm;
    private InstituitionsManager im;
    private Participant loggedInParticipant;
    private Edition currentEdition;
    private Project currentProject;
    
    public Menu(ImpCBL cbl, ParticipantsManager pm, InstituitionsManager im) {
        this.cbl = cbl;
        this.im = im;
        this.pm = pm;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    //motor de arranque xD
    public void initialize(){
        int opcao = 0;
        
        int opcao2 = 2;
        do{
           System.out.println(" ------------------- ");
           System.out.println("|        Hello      |");
           System.out.println("|      1-Register   |");
           System.out.println("|       2-Login     |");
           System.out.println("|       0-Sair      |");
           System.out.println(" ------------------- ");  
           System.out.println("Opção: ");
           try {
                opcao = Integer.parseInt(reader.readLine());

                switch (opcao) {
                    case 1:
                        System.out.println("Registo...");
                        if (register()) {
                            System.out.println("Registo feito com sucesso. Faça login para continuar");

                        }
                        break;
                    case 2:
                        System.out.println("Entrar...");
                        if (login()) {
                            //showParticipantsMenu();
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida");
                        initialize();
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            } catch (NumberFormatException e) {
                System.out.println("Input invalido. Tente novamente");

            }
        }while(opcao != 0);
        
    }
    
    
    
    //register
    private boolean register(){
        int opcao = 0;
        
        do{
           System.out.println(" ------------------- ");
           System.out.println("|    Registar como:  |");
           System.out.println("|       1-Student    |");
           System.out.println("|       2-Partner    |");
           System.out.println("|     3-Facilitator  |");
           System.out.println("|        0-Sair      |");
           System.out.println(" ------------------- ");  
           System.out.println("Opção: ");
           try{
               opcao = Integer.parseInt(reader.readLine());
           }catch(IOException e){
               System.out.println("Erro no input");
           }
        }while(opcao > 3 || opcao < 0);
        if(opcao == 0){
            return false;
        }
        try{
            System.out.println("------------------------------------");
            System.out.println("Dados de um paricipante por default");
            System.out.println("------------------------------------");
            System.out.println("\nNome: ");
            String nome = reader.readLine();
            System.out.println("\nEmail: ");
            String email = reader.readLine();
            Contact contact = assignContact();
            Participant p = new ImpParticipant(nome, email, contact, null) {};
            assignInstituition(p);
            
            switch(opcao){
                case 1: 
                    return registerStudent(p);
                case 2:
                    return registerPartner(p);
                case 3: 
                    return registerFacilitator(p);
            }
            
        }catch(IOException e){
               System.out.println("Erro no input");
           }
        
        return false;
    } 
    private Contact assignContact(){
        try {
            System.out.print("\nMorada: ");
            String street = reader.readLine();
            System.out.print("\nCidade: ");
            String city = reader.readLine();
            System.out.print("\nEstado: ");
            String state = reader.readLine();
            System.out.print("\nCodigo Postal: ");
            String zipCode = reader.readLine();
            System.out.print("\nPais: ");
            String country = reader.readLine();
            System.out.print("\nTelefone: ");
            String phone = reader.readLine();

            Contact contact = new ImpContact(street, city, state, zipCode, country, phone);
            return contact;

        } catch (IOException ex) {
            System.out.println("Error reading imput.");
            Contact contact = assignContact();
            return contact;
        } 
    }
    private Instituition assignInstituition(Participant p){
        System.out.println("=== Institutions Selection ===");
        try {           
            Instituition[] instituitions = getInstituitionsOutput();
            int i = instituitions.length;

            System.out.println((i + 1) + ". Sem instituição");
            System.out.print("Seleciona uma instituição: ");
            try {
                int institutionNumber = Integer.parseInt(reader.readLine());

                // check if it's valid
                if (institutionNumber >= 1 && institutionNumber <= instituitions.length) {
                    Instituition selectedInstitution = instituitions[institutionNumber - 1];
                    p.setInstituition(selectedInstitution);
                    System.out.println("Registado com sucesso em " + instituitions[institutionNumber - 1].getName());
                } else if (institutionNumber == i + 1) {
                    p.setInstituition(null);
                    System.out.println("Nao associado a nenhuma Instituição.");
                } else {
                    System.out.println("Seleção inválida. Tenta outra vez.\n\n");
                    assignInstituition(p);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input invalido. Introduza um numero.\n\n");
                assignInstituition(p);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro lendo o input.");
        } 
        return null;
    }
    private Instituition[] getInstituitionsOutput() {
        System.out.println("--- Instituições ---");
        Instituition[] instituitions = this.im.getInstituitions();

        int i = 0;
        for (i = 0; i < instituitions.length; i++) {
            System.out.println((i + 1) + ". " + instituitions[i].getName());
        }
        return instituitions;
    }
    /***
     * Metodo para adicionar um participante como estudante
     * 
     * @param p representa o participante que se vai registar como estudante
     * @return retorna true se o participante for registado como student
     *          e retorna false se o participante ja existir no Participant manager
     */
    private boolean registerStudent(Participant p){
        try{
            Student newStudent = new ImpStudent(p.getName(), p.getEmail(), p.getContact(), p.getInstituition());
            pm.addParticipant(newStudent);
            System.out.println("\nEstudante registado!");
            return true;
        }catch(AlreadyExistsInArray ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    private boolean registerPartner(Participant p) throws IOException{
        try{
            System.out.println("\nIntroduza o seu respetivo VAT: ");
            String vat = reader.readLine();
            System.out.println("\nIntroduza o nome do seu respetivo WEBSITE: ");
            String website = reader.readLine();
            Partner newPartner = new ImpPartner(vat, website, p.getName(), p.getEmail(), p.getContact(), p.getInstituition());
            pm.addParticipant(newPartner);
            System.out.println("\nParceiro registado!");
            return true;
        } catch (IOException ex) {
            System.out.println("Error reading input.\n\n");
            return false;
        } catch (AlreadyExistsInArray ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    private boolean registerFacilitator(Participant p){
        try{
            System.out.println("\nIntroduza sua area de atuação: ");
            String areaAtuacao = reader.readLine();
            Facilitator newFacilitator = new ImpFacilitator(areaAtuacao, p.getName(), p.getEmail(), p.getContact(), p.getInstituition());
            pm.addParticipant(p);
            System.out.println("\nFacilitador registado!");
            return true;
        }catch (IOException ex) {
            System.out.println("Error reading input.\n\n");
            return false;
        } catch (AlreadyExistsInArray ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    
    //login
    private boolean login(){
        return false;
    }
    

    
    
    public static void main(String[] args) {
        // TODO code application logic here
        /*Menu menu = new Menu();
        menu.initialize();*/
        
        ParticipantsManager pm = new ParticipantsManager();
        CBLinterface cbl = new ImpCBL();
        InstituitionsManager im = new InstituitionsManager();
        
        Menu start = new Menu((ImpCBL) cbl, pm, im);
        start.initialize();
    }

    
    
}
