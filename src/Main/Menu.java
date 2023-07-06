/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import CBL.CBLinterface;
import CBL.ImpCBL;
import Managers.InstituitionsManager;
import Managers.ParticipantsManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ma02_resources.participants.Participant;
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
                        /*if (register()) {
                            showParticipantsMenu();
                        }*/
                        break;
                    case 2:
                        System.out.println("Entrar...");
                        /*if (login()) {
                            System.out.println("Registration Success. Login to continue!");
                        }*/
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
    
    public static void main(String[] args) {
        // TODO code application logic here
        /*Menu menu = new Menu();
        menu.initialize();*/
        
    }
    
}
