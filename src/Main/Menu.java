/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import CBL.CBLinterface;
import CBL.Evaluation;
import CBL.ImpCBL;
import CBL.ImpEdition;
import CBL.ImpProject;
import CBL.ImpSubmission;
import CBL.ImpTask;
import Exceptions.AlreadyExistsInArray;
import Exceptions.EditionAlreadyExist;
import Exceptions.EditionDontExist;
import Managers.InstituitionsManager;
import Managers.ParticipantsManager;
import Participant.ImpContact;
import Participant.ImpFacilitator;
import Participant.ImpInstituition;
import Participant.ImpParticipant;
import Participant.ImpPartner;
import Participant.ImpStudent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ma02_resources.participants.Contact;
import ma02_resources.participants.Facilitator;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.InstituitionType;
import ma02_resources.participants.Participant;
import ma02_resources.participants.Partner;
import ma02_resources.participants.Student;
import ma02_resources.project.Edition;
import ma02_resources.project.Project;
import ma02_resources.project.Status;
import ma02_resources.project.Submission;
import ma02_resources.project.Task;
import ma02_resources.project.exceptions.IllegalNumberOfParticipantType;
import ma02_resources.project.exceptions.IllegalNumberOfTasks;
import ma02_resources.project.exceptions.InstituitionAlreadyExistException;
import ma02_resources.project.exceptions.ParticipantAlreadyInProject;
import ma02_resources.project.exceptions.TaskAlreadyInProject;
import org.json.simple.JSONObject;

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
    private Participant ParticipantLoggedIn;
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "admin";

    public Menu(ImpCBL cbl, ParticipantsManager pm, InstituitionsManager im) {
        this.cbl = cbl;
        this.im = im;
        this.pm = pm;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    //motor de arranque xD
    public void initialize() {
        int opcao = 0;

        int opcao2 = 2;
        do {
            System.out.println(" ╔═════════════════════╗ ");
            System.out.println(" ║      Hello       ║ ");
            System.out.println(" ╠═════════════════════╣ ");
            System.out.println(" ║ 1. Register      ║ ");
            System.out.println(" ║ 2. Login         ║ ");
            System.out.println(" ║ 0. Exit          ║ ");
            System.out.println(" ╚═════════════════════╝ ");
            System.out.println(" Enter an option: ");
            try {
                opcao = Integer.parseInt(reader.readLine());

                switch (opcao) {
                    case 1:
                        System.out.println(" ╔═════════════════╗");
                        System.out.println(" ║   Registando  ║");
                        System.out.println(" ╚═════════════════╝");
                        if (register()) {
                            System.out.println("Registo feito com sucesso. Faça login para continuar");
                        }
                        break;
                    case 2:
                        System.out.println(" ╔═════════════════╗");
                        System.out.println(" ║    Entrando   ║");
                        System.out.println(" ╚═════════════════╝");
                        if (login()) {
                            System.out.println("Login feito com sucesso.");
                            showParticipantsMenu();
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("╔═══════════════════════════╗");
                        System.out.println("║    Opção inválida     ║");
                        System.out.println("╚═══════════════════════════╝");
                        initialize();
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            } catch (NumberFormatException e) {
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Input inválido.      ║");
                System.out.println("╚═════════════════════════════╝");
            }
        } while (opcao != 0);

    }

    //register
    private boolean register() {
        int opcao = 0;

        do {
            System.out.println(" ╔════════════════════╗ ");
            System.out.println(" ║   Registration  ║ ");
            System.out.println(" ╠════════════════════╣ ");
            System.out.println(" ║ 1. Student      ║ ");
            System.out.println(" ║ 2. Partner      ║ ");
            System.out.println(" ║ 3. Facilitator  ║ ");
            System.out.println(" ║ 0. Exit         ║ ");
            System.out.println(" ╚════════════════════╝ ");
            System.out.println(" Enter an option: ");
            try {
                opcao = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("Erro no input");
            }
        } while (opcao > 3 || opcao < 0);
        if (opcao == 0) {
            return false;
        }
        try {
            System.out.println(" ╔══════════════════════════════════╗");
            System.out.println(" ║   Dados de um participante  ║");
            System.out.println(" ╠══════════════════════════════════╣");
            System.out.print(" ║ Nome: ");
            String nome = reader.readLine();
            System.out.print(" ║ Email: ");
            String email = reader.readLine();
            System.out.println(" ╚══════════════════════════════════╝");
            Contact contact = assignContact();
            Participant p = new ImpParticipant(nome, email, contact, null) {
            };
            assignInstituition(p);

            switch (opcao) {
                case 1:
                    return registerStudent(p);
                case 2:
                    return registerPartner(p);
                case 3:
                    return registerFacilitator(p);
            }

        } catch (IOException e) {
            System.out.println("Erro no input");
        }

        return false;
    }

    private Contact assignContact() {
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

    private Instituition assignInstituition(Participant p) {
        System.out.println(" ╔══════════════════════════════════╗");
        System.out.println(" ║    Seleçaõ de Instituições   ║");
        System.out.println(" ╠══════════════════════════════════╣");
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
        System.out.println(" ║       Instituições      ║");
        Instituition[] instituitions = this.im.getInstituitions();

        int i = 0;
        for (i = 0; i < instituitions.length; i++) {
            System.out.println((i + 1) + ". " + instituitions[i].getName());
        }
        return instituitions;
    }

    /**
     * *
     * Metodo para adicionar um participante como estudante
     *
     * @param p representa o participante que se vai registar como estudante
     * @return retorna true se o participante for registado como student e
     * retorna false se o participante ja existir no Participant manager
     */
    private boolean registerStudent(Participant p) {
        try {
            Student newStudent = new ImpStudent(p.getName(), p.getEmail(), p.getContact(), p.getInstituition());
            pm.addParticipant(newStudent);
            System.out.println("\nEstudante registado!");
            return true;
        } catch (AlreadyExistsInArray ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private boolean registerPartner(Participant p) throws IOException {
        try {
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

    private boolean registerFacilitator(Participant p) {
        try {
            System.out.println("\nIntroduza sua area de atuação: ");
            String areaAtuacao = reader.readLine();
            Facilitator newFacilitator = new ImpFacilitator(areaAtuacao, p.getName(), p.getEmail(), p.getContact(), p.getInstituition());
            pm.addParticipant(newFacilitator);
            System.out.println("\nFacilitador registado!");
            return true;
        } catch (IOException ex) {
            System.out.println("Error reading input.\n\n");
            return false;
        } catch (AlreadyExistsInArray ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    //login
    private boolean login() {
        int count = 0;
        do {
            try {
                System.out.print("\nIntroduza seu email: ");
                String email = reader.readLine();
                if (email.equals(USERNAME)) {
                    System.out.print("\nPassword: ");
                    String password = reader.readLine();
                    adminMenu();
                    if (!password.equals(PASSWORD)) {
                        System.out.println("Login Falhou!\n\n");
                    } else {
                        //
                        loggedInParticipant = null;
                        return true;
                    }
                } else {
                    loggedInParticipant = pm.getParticipant(email);
                    System.out.println("Login com sucesso. Bem-vindo, " + loggedInParticipant.getName() + "!\n\n");
                    return true;
                }
                return true;
            } catch (IOException e) {
                System.out.println("Erro analisando o input.");
            } catch (IllegalArgumentException e) {
                System.out.println("Utilizador nao encontrado\n");
            }
        } while (++count < 3);

        return false;
    }

    private void showParticipantsMenu() {

        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║            Menu            ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ 1. Minhas edições e proj   ║");
            System.out.println("║ 2. Informação pessoal      ║");
            System.out.println("║ 0. Back                    ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Seleciona uma opção: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        showEditionsMenu();
                        break;
                    case 2:
                        showParticipantDetails(loggedInParticipant);
                        break;

                    case 0:
                        exit = true;
                        loggedInParticipant = null;
                        break;
                    default:
                        System.out.println("╔═══════════════════════════╗");
                        System.out.println("║    Opção inválida     ║");
                        System.out.println("╚═══════════════════════════╝");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Input inválido.      ║");
                System.out.println("╚═════════════════════════════╝");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private void showEditionsMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║        Menu de Edições        ║");
            System.out.println("╠════════════════════════════════════╣");
            try {
                Edition[] editions = cbl.getEditionsByParticipant(loggedInParticipant);

                // List the editions
                int i = 0;
                for (i = 0; i < editions.length; i++) {
                    System.out.println("║ " + (i + 1) + ". " + editions[i].getName() + "("
                            + editions[i].getStatus().toString() + ")"
                    );
                }
                System.out.println("║ " + 0 + ". Back");
                System.out.print("║ Introduza o numero da edição: ");
                try {
                    int editionNumber = Integer.parseInt(reader.readLine());

                    // Check if it's valid
                    if (editionNumber == 0) {
                        exit = true;
                    } else if (editionNumber >= 1 && editionNumber <= editions.length) {
                        currentEdition = editions[editionNumber - 1];
                        showProjectsMenu();

                    } else {
                        System.out.println("╔═══════════════════════════╗");
                        System.out.println("║    Opção inválida     ║");
                        System.out.println("╚═══════════════════════════╝");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("╔═════════════════════════════╗");
                    System.out.println("║    Input inválido.      ║");
                    System.out.println("╚═════════════════════════════╝");

                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                exit = true;
            } catch (IOException e) {
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Input inválido.      ║");
                System.out.println("╚═════════════════════════════╝");
            }
            System.out.println("╚════════════════════════════════════╝");
        }
    }

    private void showParticipantDetails(Participant participant) {
        boolean exit = false;
        while (!exit) {

            try {

                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║        Participant Details        ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Nome: " + participant.getName());
                System.out.println("║ Email: " + participant.getEmail());

                if (participant instanceof Facilitator) {
                    System.out.println("║ Area de Atuação: " + ((Facilitator) participant).getAreaOfExpertise());
                } else if (participant instanceof Student) {
                    System.out.println("║ Numero de Estudante: " + ((Student) participant).getNumber());
                } else if (participant instanceof Partner) {
                    System.out.print("║ Vat: " + ((Partner) participant).getVat());
                    System.out.print("║ WebSite: " + ((Partner) participant).getWebsite());
                }

                System.out.println("║ Detalhes do contacto:");
                System.out.println("║ Rua: " + participant.getContact().getStreet());
                System.out.println("║ Cidade: " + participant.getContact().getCity());
                System.out.println("║ Estado: " + participant.getContact().getState());
                System.out.println("║ Codigo Postal: " + participant.getContact().getZipCode());
                System.out.println("║ Pais: " + participant.getContact().getCountry());
                System.out.println("║ Telefone: " + participant.getContact().getPhone());

                System.out.println("║ Detalhes da Instituição:");
                if (participant.getInstituition() != null) {
                    System.out.println("║ Nome: " + participant.getInstituition().getName() + " (" + participant.getInstituition().getType().toString() + ")");
                    System.out.println("║ Email: " + participant.getInstituition().getEmail());
                    System.out.println("║ Website: " + participant.getInstituition().getWebsite());
                } else {
                    System.out.println("║ Sem instituições para o participante!");
                }

                System.out.println("║ --------------------------------");
                System.out.println("║ 1. Muda a informação de contacto");
                System.out.println("║ 2. Atribuir outra instituição");
                System.out.println("║ 0. Back");
                System.out.println("╚══════════════════════════════════════╝");

                System.out.print(" Seleciona uma opção: ");
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        Contact newContact = assignContact();
                        if (newContact != null) {
                            participant.setContact(newContact);
                            System.out.println("║ Contact alterado com sucesso\n");
                        } else {
                            System.out.println("║ Algo correu mal tente outra vez.\n");
                        }
                        break;
                    case 2:
                        assignInstituition(participant);
                        break;
                    case 0:
                        exit = true;
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Input inválido.      ║");
                System.out.println("╚═════════════════════════════╝");

            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("║ Error reading input.");
            }
        }
        System.out.println("╚══════════════════════════════════════╝");
    }

    private void adminMenu() {
        System.out.println("\nOpções para admin\n");
        boolean exit = false;
        while (!exit) {
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║        ==== Admin Menu ====     ║");
            System.out.println("║  1. Controlo das CBL            ║");
            System.out.println("║  2. Controlo Users/Participants ║");
            System.out.println("║  3. Controlo de Instituições    ║");
            System.out.println("║  4. Lista/Reports               ║");
            System.out.println("║  5. Exportar ficheiro em CSV    ║");
            System.out.println("║  0. Sair                        ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Seleciona uma opção: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        showAdminEditionsMenu();
                        break;
                    case 2:
                        showAdminParticipantsMenu();
                        break;
                    case 3:
                        showInstituitionsMenu();
                        break;
                    case 4:
                        // showListingsMenu();
                        break;
                    case 5:
                        System.out.print("Nome do ficheiro:");
                        String nomeFicheiro = reader.readLine();
                        if (cbl.exportToCSV(nomeFicheiro)) {
                            System.out.println("╔════════════════════════════════════════════════╗");
                            System.out.println("║      Sucesso exportando informação.      ║");
                            System.out.println("╚════════════════════════════════════════════════╝");
                        } else {
                            System.out.println("╔════════════════════════════════════════════════╗");
                            System.out.println("║        Erro importando informação.       ║");
                            System.out.println("╚════════════════════════════════════════════════╝");
                        }
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("╔═══════════════════════════════════════════╗");
                        System.out.println("║ Seleção inválida. Tenta outra vez!  ║");
                        System.out.println("╚═══════════════════════════════════════════╝");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("╔════════════════════════════════════════════════════════╗");
                System.out.println("║ Input invalido. Por favor tente com um numero.  ║");
                System.out.println("╚════════════════════════════════════════════════════════╝");
            } catch (IOException e) {
                System.out.println("Erro lendo input.");
            }
        }

    }

    private void showAdminEditionsMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║    ===== Menu de Edições =====   ║");
            System.out.println("║ Numero de Edições: " + cbl.getNumberOfEditions() + "             ║");

            try {
                Edition[] editions = cbl.getEditions();
                int i = 0;
                if (cbl.getNumberOfEditions() != 0) {
                    System.out.println("║   ---- Lista de Edições -----    ║");

                    for (i = 0; i < editions.length; i++) {
                        System.out.println("║ " + (i + 1) + ". " + editions[i].getName() + " (" + editions[i].getStatus().toString() + ")" + "  ║");
                    }
                    System.out.println("║    -------------------          ║");
                }
                System.out.println("║ " + (i + 1) + ". Adicionar/Criar uma edição    ║");
                System.out.println("║ " + (i + 2) + ". Lista de edições incompletas  ║");
                System.out.println("║ " + 0 + ". Back                          ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.print(" Seleciona uma opção:                    ");
                try {
                    int editionNumber = Integer.parseInt(reader.readLine());

                    if (editionNumber == 0) {
                        exit = true;
                    } else if (editions.length != 0 && editionNumber >= 1 && editionNumber <= editions.length) {
                        currentEdition = editions[editionNumber - 1];
                        try {
                            showAdminProjectsMenu();
                        } catch (ParseException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (editionNumber == i + 1) {
                        try {
                            showAddEditions();
                        } catch (EditionAlreadyExist e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (editionNumber == i + 2) {
                        showUncompletedEditions();
                    } else {
                        System.out.println("╔══════════════════════════════════════╗");
                        System.out.println("║    Seleção inválida. Tente outra vez.   ║");
                        System.out.println("╚══════════════════════════════════════╝");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("╔══════════════════════════════════════╗");
                    System.out.println("║    Input inválido. Introduza um numero.   ║");
                    System.out.println("╚══════════════════════════════════════╝");
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro lendo o input.");
            }
        }
    }

    private void showAdminProjectsMenu() throws ParseException {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║ == Edição: " + currentEdition.getName() + "(" + currentEdition.getStatus().toString() + ") == ║");
            Project[] projects = currentEdition.getProjects();
            int i = 0;
            if (currentEdition.getNumberOfProjects() != 0) {
                System.out.println("║      ---- Lista de Projetos -----      ║");

                for (i = 0; i < projects.length; i++) {
                    System.out.println("║ " + (i + 1) + ". " + projects[i].getName() + "                  ║");
                }

                System.out.println("║        ---------------------          ║");
            }

            System.out.println("║ " + (i + 1) + ". Ativar edição                       ║");
            System.out.println("║ " + (i + 2) + ". Remover edição                      ║");
            System.out.println("║ " + ((i + 3) + ". Adicionar Projeto") + "                   ║");
            System.out.println("║ " + ((i + 4) + ". Procura projetos pela tag") + "            ║");
            System.out.println("║ " + 0 + ". Back                                ║");
            System.out.print("║ Selecionar uma opção:                    ║ ");
            try {
                int projectNumber = Integer.parseInt(reader.readLine());

                if (projectNumber == 0) {
                    exit = true;
                } else if (projectNumber >= 1 && projectNumber <= projects.length) {
                    currentProject = projects[projectNumber - 1];
                    showAdminProjectDetails();
                } else if (projectNumber == i + 1) {
                    try {
                        cbl.activateEdition(currentEdition.getName());

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());

                    }
                } else if (projectNumber == i + 2) {
                    System.out.println("Certeza que queres remover esta edição? (sim/nao)");
                    String answer = reader.readLine();

                    if (answer.equalsIgnoreCase("sim")) {
                        try {
                            showRemoveEditionMenu();
                        } catch (EditionDontExist ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        currentEdition = null;
                        exit = true;
                    } else {
                        System.out.println("Remoção cancelada.");
                    }
                } else if (projectNumber == i + 3) {
                    showAddProject();
                } else if (projectNumber == i + 4) {

                    System.out.println("Tag a procurar: ");
                    String tag = reader.readLine();
                    tag = tag.trim();
                    if (tag != null) {
                        listProjectsByTag(tag);
                    }

                } else {
                    System.out.println("╔══════════════════════════════════════╗");
                    System.out.println("║    Seleção inválida. Tente outra vez.   ║");
                    System.out.println("╚══════════════════════════════════════╝");
                }
            } catch (NumberFormatException e) {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║    Input inválido. Entre com um numero.   ║");
                System.out.println("╚══════════════════════════════════════╝");
            } catch (IOException e) {
                System.out.println("Erro lendo o input.");
            }
        }
    }

    private void listProjectsByTag(String tag) {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║========== Projectos com o tag " + tag + " =========║");

            Project[] projects = currentEdition.getProjectsByTag(tag);
            if (projects == null) {
                System.out.println("║ Sem projetos com a tag " + tag + " econtradas! ║");
                exit = true;
            } else {
                int i = 0;
                for (i = 0; i < projects.length; i++) {
                    System.out.println("║ " + (i + 1) + ". " + projects[i].getName() + "              ║");
                }

                System.out.println("║ --------------------------------------         ║");
                System.out.println("║ " + 0 + ". Back                                ║");
                System.out.print("║ Seleciona uma opção:                             ║ ");
                try {
                    int projectNumber = Integer.parseInt(reader.readLine());

                    if (projectNumber == 0) {
                        exit = true;
                    } else if (projectNumber >= 1 && projectNumber <= projects.length) {
                        currentProject = projects[projectNumber - 1];
                        showAdminProjectDetails();
                        exit = true;
                    } else {
                        System.out.println("╔══════════════════════════════════════╗");
                        System.out.println("║    Seleção inválida. Tente outra vez.   ║");
                        System.out.println("╚══════════════════════════════════════╝");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("╔══════════════════════════════════════╗");
                    System.out.println("║    Input inválido. Por favor tente com um numero.   ║");
                    System.out.println("╚══════════════════════════════════════╝");
                } catch (IOException e) {
                    System.out.println("Erro avalidando o input.");
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void showAddEditions() throws EditionAlreadyExist {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║ ===   Adicionar/Criar Edição  === ║");
        try {
            System.out.print("║ Name: ");
            String name = reader.readLine();

            LocalDate start = null;
            while (start == null) {
                System.out.print("║ Data Inicio (yyyy-mm-dd):  ");
                String startDate = reader.readLine();
                try {
                    start = LocalDate.parse(startDate);
                } catch (DateTimeParseException e) {
                    System.out.println("║ Data inválida. Siga o formato yyyy-mm-dd.  ║");
                }
            }

            LocalDate end = null;
            while (end == null) {
                System.out.print("║ Data Fim (yyyy-mm-dd): ");
                String endDate = reader.readLine();
                try {
                    end = LocalDate.parse(endDate);
                } catch (DateTimeParseException e) {
                    System.out.println("║ Data inválida. Siga o formato yyyy-mm-dd.  ║");
                }
            }

            Edition edition = new ImpEdition(name, start, end);

            cbl.addEdition(edition);

        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
    }

    private void showUncompletedEditions() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║=====   Edições incompletas =====║");
        boolean exit = false;
        while (!exit) {
            try {
                Edition[] editions = cbl.uncompletedEditions();

                int i = 0;

                for (i = 0; i < editions.length; i++) {
                    System.out.println("║ " + (i + 1) + ". " + editions[i].getName() + "(" + editions[i].getStatus().toString() + ")" + "║");
                }
                System.out.println("║ " + (i + 1) + ". Back                            ║");
                System.out.println("╚══════════════════════════════════════╝");
                System.out.print(" Seleciona uma opção:                         ");
                try {
                    int editionNumber = Integer.parseInt(reader.readLine());

                    if (editionNumber == i + 1) {
                        exit = true;
                    } else if (editionNumber >= 1 && editionNumber <= editions.length) {
                        currentEdition = editions[editionNumber - 1];
                        try {
                            showAdminProjectsMenu();
                        } catch (ParseException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println("╔════════════════════════════════════════════════════════╗");
                        System.out.println("║ Input invalido. Por favor tente com um numero.  ║");
                        System.out.println("╚════════════════════════════════════════════════════════╝");

                    }
                } catch (NumberFormatException e) {
                    System.out.println("╔════════════════════════════════════════════════════════╗");
                    System.out.println("║ Input invalido. Por favor tente com um numero.  ║");
                    System.out.println("╚════════════════════════════════════════════════════════╝");
                } catch (IOException e) {
                    System.out.println("Erro lendo input.");
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showAdminParticipantsMenu() {
        boolean exit = false;
        while (!exit) {
            Participant[] participantes = listParticipants();
            int contador = participantes.length;

            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║   === Menu de Participantes ===  ║");
            System.out.println("");

            for (int i = 0; i < participantes.length; i++) {
                System.out.println((i + 1) + ". " + participantes[i].getName());
            }

            System.out.println("║" + (contador + 1) + ". Criar um participante" + "          ║");
            System.out.println("║" + (contador + 2) + ". Excluir participante" + "           ║");
            System.out.println("║" + 0 + ". Voltar" + "                         ║");
            System.out.println("╚════════════════════════════════════════╝");

            try {
                int numeroParticipante = Integer.parseInt(reader.readLine());

                // Verificar se o número do participante é válido
                if (numeroParticipante == 0) {
                    exit = true;
                } else if (numeroParticipante == (contador + 2)) {
                    System.out.println("Selecione o número do participante que deseja remover: ");
                    int removeParticipant = Integer.parseInt(reader.readLine());

                    if (contador != 0 && removeParticipant >= 1 && removeParticipant <= participantes.length) {
                        System.out.println("Tem certeza de que deseja remover este participante? (sim/não)");
                        String resposta = reader.readLine();

                        if (resposta.equalsIgnoreCase("sim")) {
                            try {
                                pm.removeParticipant(participantes[removeParticipant - 1].getEmail());
                                System.out.println("Removido com sucesso!\n");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Remoção cancelada");
                        }
                    } else {
                        System.out.println("Seleção inválida");
                    }
                } else if (numeroParticipante == (contador + 1)) {
                    if (register()) {
                        System.out.println("Registro realizado com sucesso!");
                    }
                } else {
                    System.out.println("Seleção inválida. Por favor, tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");

            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            }
        }
    }

    private Participant[] listParticipants() {

        System.out.println(" ===== Todos Participantes registados ======= ");
        Participant[] participants = pm.getParticipants();
        int counter = 0;
        if (pm.getNumberOfFacilitators() > 0) {
            System.out.println("Facilitators: ");
            for (int i = 0; i < pm.getNumberOfFacilitators(); i++) {
                if (participants[counter] instanceof Facilitator) {
                    System.out.println((counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ")");
                    counter++;
                }
            }
        }

        if (pm.getNumberOfStudents() > 0) {
            System.out.println("Students: ");
            for (int i = 0; i < pm.getNumberOfStudents(); i++) {
                if (participants[counter] instanceof Student) {
                    System.out.println((counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ")");
                    counter++;
                }
            }
        }

        if (pm.getNumberOfPartners() > 0) {
            System.out.println("Partners: ");
            for (int i = 0; i < pm.getNumberOfPartners(); i++) {
                if (participants[counter] instanceof Partner) {
                    System.out.println((counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ")");
                    counter++;
                }
            }
        }
        return participants;
    }

    private void showInstituitionsMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔═════════════════════════════════════════════════════════╗");
            System.out.println("║          ==== Menu de Instituições ====          ║");
            System.out.println("║ 1. Listar instituições                           ║");
            System.out.println("║ 2. Adicionar instituição                         ║");
            System.out.println("║ 0. Sair                                          ║");
            System.out.println("╚═════════════════════════════════════════════════════════╝");
            System.out.print("Selecione uma opção: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        listInstituitions();
                        break;
                    case 2:
                        showAddInstituition();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Seleção inválida. Tente novamente!\n");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input inválido. Por favor, insira um número.\n\n");
            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            }
        }
    }

    private void listInstituitions() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║      === Seleção de Instituições ===       ║");
            try {
                Instituition[] instituitions = getInstituitionsOutput();
                int i = instituitions.length;

                System.out.println("║ " + 0 + ". Voltar                   ║");
                System.out.println("╚═══════════════════════════════════════════╝");
                System.out.print("Selecione uma instituição:");
                try {
                    int institutionNumber = Integer.parseInt(reader.readLine());

                    // Verifique se é válido
                    if (institutionNumber >= 1 && institutionNumber <= instituitions.length) {
                        Instituition instituicaoSelecionada = instituitions[institutionNumber - 1];
                        showInstituitionDetails(instituicaoSelecionada);
                    } else if (institutionNumber == 0) {
                        exit = true;
                    } else {
                        System.out.println("║ Seleção inválida. Por favor, tente novamente. ║\n\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("║ Entrada inválida. Por favor, insira um número. ║\n\n");
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            }
        }
    }

    private void showInstituitionDetails(Instituition instituition) {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║      === Detalhes da Instituição ===       ║");
                System.out.println("║ Nome: " + instituition.getName());
                System.out.println("║ Email: " + instituition.getEmail());
                System.out.println("║ Website: " + instituition.getWebsite());
                System.out.println("║ Descrição: " + instituition.getDescription());
                System.out.println("║ Tipo de Instituição: " + instituition.getType());

                System.out.println("║ Detalhes de Contato:");
                System.out.println("║ Rua: " + instituition.getContact().getStreet());
                System.out.println("║ Cidade: " + instituition.getContact().getCity());
                System.out.println("║ Estado: " + instituition.getContact().getState());
                System.out.println("║ Codigo Postal: " + instituition.getContact().getZipCode());
                System.out.println("║ País: " + instituition.getContact().getCountry());
                System.out.println("║ Telefone: " + instituition.getContact().getPhone());

                System.out.println("║ --------------------------------");
                System.out.println("║ 1. Alterar informações de contato");
                System.out.println("║ 2. Alterar tipo");
                System.out.println("║ 3. Alterar website");
                System.out.println("║ 4. Alterar descrição");
                System.out.println("║ 5. Remover esta Instituição");
                System.out.println("║ 6. Voltar");

                int opcao = Integer.parseInt(reader.readLine());

                switch (opcao) {
                    case 1:
                        Contact novoContato = assignContact();
                        if (novoContato != null) {
                            instituition.setContact(novoContato);
                            System.out.println("Contato atualizado com sucesso\n");
                        } else {
                            System.out.println("Algo deu errado. Por favor, tente novamente.\n");
                        }
                        break;
                    case 2:
                        changeType(instituition);
                        break;
                    case 3:
                        changeWebsite(instituition);
                        break;
                    case 4:
                        changeDescription(instituition);
                        break;
                    case 5:
                        System.out.println("\nTem certeza de que deseja remover esta instituição? (sim/não)");
                        String resposta = reader.readLine();

                        if (resposta.equalsIgnoreCase("sim")) {
                            showRemoveInstituition(instituition);
                            exit = true;
                        } else {
                            System.out.println("Remoção cancelada.");
                        }
                        break;
                    case 6:
                        exit = true;
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.\n\n");

            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao ler o input.");
            }
        }
    }

    private void changeType(Instituition instituition) {
        boolean completo = false;
        while (!completo) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║      == Tipos Disponíveis ==      ║");
            System.out.println("║ 1. UNIVERSIDADE                   ║");
            System.out.println("║ 2. EMPRESA                        ║");
            System.out.println("║ 2. ONG                            ║");
            System.out.println("║ 2. OUTRO                          ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Selecione o tipo: ");
            try {
                int tipo = Integer.parseInt(reader.readLine());
                switch (tipo) {
                    case 1:
                        instituition.setType(InstituitionType.UNIVERSITY);
                        completo = true;
                        break;
                    case 2:
                        instituition.setType(InstituitionType.COMPANY);
                        completo = true;
                        break;
                    case 3:
                        instituition.setType(InstituitionType.NGO);
                        completo = true;
                        break;
                    case 4:
                        instituition.setType(InstituitionType.OTHER);
                        completo = true;
                        break;
                    default:
                        System.out.println("Seleção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.\n\n");
            } catch (IOException ex) {
                System.out.println("Erro ao ler o input.");
            }
        }
    }

    private void changeWebsite(Instituition instituition) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║ == Alterar informações do website == ");
        System.out.print("║Novo Website: ");
        try {
            String website = reader.readLine();

            instituition.setWebsite(website);
            System.out.println("║Website atualizado com sucesso.\n");
            System.out.println("╚════════════════════════════════════════════╝");

        } catch (IOException ex) {
            System.out.println("Erro ao ler o input.");
        }
    }

    private void changeDescription(Instituition instituition) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║ == Alterar descrição da Instituição == ║");
        System.out.print("║ Nova Descrição: ");
        try {
            String description = reader.readLine();

            instituition.setDescription(description);
            System.out.println("║Descrição atualizada com sucesso.\n");
            System.out.println("╚════════════════════════════════════════════╝");

        } catch (IOException ex) {
            System.out.println("Erro ao ler o input.");
        }
    }

    private void showRemoveInstituition(Instituition instituition) {
        boolean complete = false;
        try {
            Instituition removedInstituition = im.removeInstituition(instituition.getName());
            System.out.println("Instituição removida com sucesso.");
            while (!complete) {
                try {
                    System.out.println("Deseja salvar em um arquivo JSON? (sim/não)");
                    String saveAnswer = reader.readLine();
                    if (saveAnswer.equalsIgnoreCase("sim")) {
                        System.out.println("Nome do arquivo para salvar: ");
                        String name = reader.readLine();
                        if (name.equalsIgnoreCase("cbl") || name.equalsIgnoreCase("instituitions2")
                                || name.equalsIgnoreCase("project_template") || name.equalsIgnoreCase("users")) {
                            System.out.println("Nome inválido.");
                        } else {
                            String path = null;
                            if (name.contains(".json")) {
                                path = "files/" + name;
                            } else {
                                path = "files/" + name + ".json";
                            }
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("Instituição removida", ((ImpInstituition) removedInstituition).toJsonObj());
                                FileWriter fileWriter = new FileWriter(path);
                                fileWriter.write(jsonObject.toJSONString());
                                fileWriter.close();
                                complete = true;
                            } catch (IOException e) {
                                e.getMessage();
                            }
                        }
                    } else {
                        complete = true;
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler o input.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAddInstituition() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║ == Adicionar Instituição == ");
        try {
            System.out.print("║\nNome: ");
            String name = reader.readLine();
            System.out.print("║\nEmail: ");
            String email = reader.readLine();
            System.out.print("║\nWebsite: ");
            String website = reader.readLine();
            System.out.print("║\nDescrição: ");
            String description = reader.readLine();
            System.out.println("╚════════════════════════════════════════════╝");

            Contact contact = assignContact();

            Instituition instituition = new ImpInstituition(name, email, website, description, contact, null);

            changeType(instituition);

            try {
                im.addInstituition(instituition);
                System.out.println("Instituição adicionada com sucesso.\n");
            } catch (InstituitionAlreadyExistException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o input.\n");
        }
    }

    private void showAdminProjectDetails() { //// nesta função que vamos fazer a evaluation
        boolean exit = false;
        while (!exit) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║  === Detalhes da Instituição ===  ║");
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║    ===== Project Details =====    ║");
            System.out.println("║ Project: " + currentProject.getName());
            System.out.println("║ Description: " + currentProject.getDescription());
            System.out.print("║ Tags: ");

            String[] tags = currentProject.getTags();
            for (String s : tags) {
                System.out.print("#" + s + " ");
            }
            System.out.println("║ \nStatus: " + (currentProject.isCompleted() ? "Completed" : "Incomplete"));
            System.out.println("║ Participants: " + currentProject.getNumberOfParticipants() + "/" + currentProject.getMaximumNumberOfParticipants());
            System.out.println("║  -- Facilitators: " + currentProject.getNumberOfFacilitators() + "/" + currentProject.getMaximumNumberOfFacilitators());
            System.out.println(" -- Students: " + currentProject.getNumberOfStudents() + "/" + currentProject.getMaximumNumberOfStudents());
            System.out.println(" -- Partners: " + currentProject.getNumberOfPartners() + "/" + currentProject.getMaximumNumberOfPartners());
            System.out.println("\nTasks: " + currentProject.getNumberOfTasks() + "/" + currentProject.getMaximumNumberOfTasks());

            Task[] tasks = currentProject.getTasks();

            int i = 0;
            for (i = 0; i < tasks.length; i++) {
                System.out.println((i + 1) + ". " + tasks[i].getTitle());
            }
            System.out.println(" ----------------------- ");
            System.out.println((i + 1) + ". Remove this project");
            System.out.println((i + 2) + ". Project progress");
            System.out.println((i + 3) + ". List participants");
            System.out.println((i + 4) + ". Add participants (" + (currentProject.getNumberOfParticipants() == currentProject.getMaximumNumberOfParticipants() ? "Not Available" : "Available") + ")");
            System.out.println((i + 5) + ". Add Task (" + (currentProject.getNumberOfTasks() == currentProject.getMaximumNumberOfTasks() ? "Not Available" : "Available") + ")");
            System.out.println((i + 6) + ". Avaliar Estudantes");
            System.out.println("\n" + 0 + ". Back");
            System.out.print("Select an option: ");
            try {
                int taskNumber = Integer.parseInt(reader.readLine());

                // Verifique se o número da tarefa é válido
                if (taskNumber == 0) {
                    exit = true;
                } else if (taskNumber == (i + 1)) {
                    System.out.println("Are you sure you want to remove this project? (yes/no)");
                    String answer = reader.readLine();

                    if (answer.equalsIgnoreCase("yes")) {
                        try {
                            cbl.getEdition(currentEdition.getName()).removeProject(currentProject.getName());
                            System.out.println("Project removed successfully!\n");
                            exit = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else if (taskNumber == (i + 2)) {
                    showProjectProgress();
                } else if (taskNumber == (i + 3)) {
                    if (currentProject.getNumberOfParticipants() > 0) {
                        listaPP(); //fazer a evaluation
                    } else {
                        System.out.println("No participants in project. Add participants");
                    }
                } else if (taskNumber == (i + 4)) {
                    if (currentProject.getMaximumNumberOfParticipants() != currentProject.getNumberOfParticipants()) {
                        try {
                            showAddParticipant();
                            System.out.println("Added Successfully!\n");
                        } catch (IllegalNumberOfParticipantType | ParticipantAlreadyInProject e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Maximum amount of Participants reached!\n");
                    }

                } else if (taskNumber == (i + 5)) {
                    if (currentProject.getNumberOfTasks() == currentProject.getMaximumNumberOfTasks()) {
                        try {
                            showAddTask();
                        } catch (IllegalNumberOfTasks | TaskAlreadyInProject e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Maximum amount of Tasks reached!\n");
                    }
                }else if(taskNumber == (i + 6)){
                    showEvaluationsMenu();
                } else if (taskNumber >= 1 && taskNumber <= tasks.length) {
                    Task selectedTask = tasks[taskNumber - 1];
                    showAdminTaskDetails(selectedTask);

                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");

            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void showAdminTaskDetails(Task task) {
        boolean exit = false;
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       === Detalhes Task ===       ║");
        System.out.println("║ Titulo: " + task.getTitle());
        System.out.println("║ Descrição: " + task.getDescription());
        System.out.println("║ Inicio: " + task.getStart().toString());
        System.out.println("║ Fim: " + task.getEnd().toString());
        System.out.println("║ Numero de submissoes: " + task.getNumberOfSubmissions());

        System.out.println("\nSeleciona uma opção:");
        System.out.println("1. Listar Submissões");
        System.out.println("2. Extender dia limite");
        System.out.println("0. Back");
        System.out.print("\nSeleciona uma opção: ");
        int option = 0;
        try {
            option = Integer.parseInt(reader.readLine());

            switch (option) {
                case 1:
                    listSubmissions(task);
                    break;
                case 2:
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("║        == Extend Deadline ==         ║");

                    System.out.println("║ Number of Days to extend: ");
                    int days = Integer.parseInt(reader.readLine());

                    System.out.println("║ \nThe task " + task.getTitle() + " end date will be extended by " + days + " days.");
                    task.extendDeadline(days);
                    System.out.println("║ New End Date: " + task.getEnd().toString());
                    break;
                case 0:
                    exit = true;
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.\n\n");
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void listSubmissions(Task task) {
        boolean exit = false;
        while (!exit) {
            int i = 0;
            Submission[] submissions = null;
            if (task.getNumberOfSubmissions() > 0) {

                submissions = task.getSubmissions();

                for (i = 0; i < submissions.length; i++) {
                    System.out.println((i + 1) + ". " + submissions[i].getText());
                }
            } else {
                System.out.println("  --- Nenhuma submissão encontrada! ---\n\n");
            }

            System.out.println(0 + ". Back");
            System.out.print("\nSeleciona: ");
            try {
                int submissionNumber = Integer.parseInt(reader.readLine());

                if (submissionNumber == 0) {
                    exit = true;
                } else if (submissionNumber >= 1 && submissionNumber <= submissions.length) {
                    Submission selectedSubmission = submissions[submissionNumber - 1];
                    showSubmissionDetails(selectedSubmission);
                } else {
                    System.out.println("Invalid selection. Please try again.\n\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private void showSubmissionDetails(Submission submission) {
        boolean exit = false;

        while (!exit) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║         == Submission ==          ║");
            System.out.println("║ Student: " + submission.getStudent().getName() + "(" + submission.getStudent().getEmail() + ")");
            System.out.println("║ Text: " + submission.getText());
            System.out.println("║ Date: " + submission.getDate().toString());
            System.out.println("\n║ 0. Back");
            System.out.print("\nSelect an option: ");
            try {
                int option = Integer.parseInt(reader.readLine());
                if (option == 0) {
                    exit = true;
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        System.out.println("╚══════════════════════════════════════╝");
    }

    private void showProjectsMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║        ===== Menu Projetos =====        ║");
            System.out.println("║ Edition: " + currentEdition.getName() + "(" + currentEdition.getStatus().toString() + ")");

            Project[] projects = currentEdition.getProjectsOf(loggedInParticipant.getEmail());

            int i = 0;
            for (i = 0; i < projects.length; i++) {
                System.out.println("║ " + (i + 1) + ". " + projects[i].getName());
            }

            System.out.println("║ " + 0 + ". Back");
            System.out.print("║ Select an option: ");
            try {
                int projectNumber = Integer.parseInt(reader.readLine());

                // Verifique se o número do projeto é válido
                if (projectNumber == 0) {
                    exit = true;
                } else if (projectNumber >= 1 && projectNumber <= projects.length) {
                    currentProject = projects[projectNumber - 1];
                    showProjectDetails();
                } else {
                    System.out.println("Invalid selection. Please try again.\n\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private void showProjectDetails() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║       ===== Project Details =====       ║");
            System.out.println("║ Projeto: " + currentProject.getName());
            System.out.println("║ Descrição: " + currentProject.getDescription());
            System.out.print("║ Tags: ");

            String[] tags = currentProject.getTags();
            for (String s : tags) {
                System.out.print("#" + s + " ");
            }
            System.out.println("║ \n║ Status: " + (currentProject.isCompleted() ? "Completed" : "Incomplete"));
            System.out.println("║ Participantes: " + currentProject.getNumberOfParticipants() + "/" + currentProject.getMaximumNumberOfParticipants());
            System.out.println("║  -- Facilitadores: " + currentProject.getNumberOfFacilitators() + "/" + currentProject.getMaximumNumberOfFacilitators());
            System.out.println("║ -- Estudantes: " + currentProject.getNumberOfStudents() + "/" + currentProject.getMaximumNumberOfStudents());
            System.out.println("║ -- Parceiros: " + currentProject.getNumberOfPartners() + "/" + currentProject.getMaximumNumberOfPartners());
            System.out.println("║ \n║ Tasks: " + currentProject.getNumberOfTasks() + "/" + currentProject.getMaximumNumberOfTasks());

            Task[] tasks = currentProject.getTasks();
            int i = 0;
            for (i = 0; i < tasks.length; i++) {
                System.out.println("║ " + (i + 1) + ". " + tasks[i].getTitle());
            }
            if (loggedInParticipant instanceof Facilitator) {
                System.out.println("║ \n║" + (i + 1) + ". Avaliar Estudantes");
            } else if (loggedInParticipant instanceof Student) {
                System.out.println("║ \n║ " + (i + 1) + ". Fazer Autoavaliação");
            }

            System.out.println("║ \n║ " + 0 + ". Back");
            System.out.print("║ Introduza o numero de tasks: ");
            try {
                int taskNumber = Integer.parseInt(reader.readLine());

                // Verifique se o número da tarefa é válido
                if (taskNumber == 0) {
                    exit = true;
                } else if (taskNumber >= 1 && taskNumber <= tasks.length) {
                    Task selectedTask = tasks[taskNumber - 1];
                    showTaskDetails(selectedTask);
                } else if (taskNumber == i + 1) {
                    showEvaluationsMenu();
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
//            System.out.print("Fazer autoavaliação (sim/nao):");
//            try {
//                String auto = reader.readLine();
//                switch (auto) {
//                    case "sim":
//                        System.out.print("Nota: ");
//                        int nota = Integer.parseInt(reader.readLine());
//                        break;
//                    default:
//                        break;
//                }
//
//            } catch (IOException ex) {
//                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        System.out.println("╚══════════════════════════════════╝");
    }

    private Student listStudentsOfProject() throws NullPointerException {
        boolean exit = false;
        while (!exit) {
            Student[] students = ((ImpProject) currentProject).getStudents();
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║    ===== Estudantes do Projeto =====    ║");
            System.out.println("║ Projeto: " + currentProject.getName());

            int i = 0;
            for (i = 0; i < students.length; i++) {
                System.out.println("║ " + (i + 1) + ". " + students[i].getEmail());
            }

            System.out.println("║ \n║ " + 0 + ". Back");

            System.out.print("║ Introduza o numero de estudante: ");
            try {
                int studentNumber = Integer.parseInt(reader.readLine());

                // Verifique se o número é válido
                if (studentNumber == 0) {
                    exit = true;
                } else if (studentNumber >= 1 && studentNumber <= students.length) {
                    return students[studentNumber - 1];
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
            System.out.println("╚══════════════════════════════════╝");
        }
        return null;
    }

    private void showEvaluationsMenu() {

        if (loggedInParticipant != null && loggedInParticipant instanceof Student) {
            addEvaluationMenu((Student) loggedInParticipant);
        } else {

            try {
                Student student = listStudentsOfProject();
                addEvaluationMenu(student);
            } catch (NullPointerException e) {
                System.out.println("╔══════════════════════════════════╗");
                System.out.println("║    ===== Sem Estudantes no Projeto =====    ║");
                System.out.println("╚══════════════════════════════════╝");
            }
        }

    }

    private void addEvaluationMenu(Student student) {
        boolean exit = false;
        while (!exit) {
            try {
                Evaluation studentEvaluation;

                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║       Ficha do estudante     ║");
                System.out.println("╠══════════════════════════════════════╣");
                System.out.println("║ Nome: " + student.getName());
                System.out.println("║ Email: " + student.getEmail());
                try {
                    studentEvaluation = ((ImpProject) currentProject).evaluationOf(student);
                    try {
                        System.out.println("║ Auto-Avaliação: " + studentEvaluation.getSelfEvaluation());
                    } catch (NullPointerException a) {
                        System.out.println("║ Auto-Avaliação: Sem avaliação ");
                    }
                    try {
                        System.out.println("║ Hetero Avaliação: " + studentEvaluation.getHeteroevaluation());
                        try {
                            System.out.println("║  Avaliado por: " + studentEvaluation.getFacilitator());
                        } catch (NullPointerException a) {
                            System.out.println("║  Avaliado por: Admin");
                        }
                    } catch (NullPointerException a) {
                        System.out.println("║ Hetero Avaliação: Sem avaliação ");
                    }
                } catch (NullPointerException ex) {
                    studentEvaluation = null;
                    System.out.println("║ Auto-Avaliação: Sem avaliação ");
                    System.out.println("║ Hetero Avaliação: Sem avaliação ");
                }
                System.out.println("╠══════════════════════════════════════╣");
                if (student.equals(loggedInParticipant)) {
                    System.out.println("║ 1. Adicionar autoAvaliação");
                } else {
                    System.out.println("║ 1. Adicionar Hetero Avaliação");
                }
                System.out.println("║ \n║  0. Back");
                System.out.println("╚═════════════════════════════════════╝");

                System.out.print("Introduza sua opção: ");
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        System.out.print("A sua nota: ");
                        float grade = Float.parseFloat(reader.readLine());
                        try {

                            if (student.equals(loggedInParticipant)) {
                                ((ImpProject) currentProject).addSelfEvaluation(student, grade);
                            } else {
                                if (loggedInParticipant != null && loggedInParticipant instanceof Facilitator) {
                                    ((ImpProject) currentProject).addHeteroEvaluation(student, (Facilitator) loggedInParticipant, grade);
                                } else {
                                    ((ImpProject) currentProject).addHeteroEvaluation(student, null, grade);
                                }
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 0:
                        exit = true;
                    default:
                        System.out.println("Invalid selection. Please try again.\n\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");

            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }

        }
    }

    private void showTaskDetails(Task task) {
        boolean isStudent = false, exit = false;
        if (loggedInParticipant instanceof Student) {
            isStudent = true;
        }
        while (!exit) {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║      === Detalhes Tasks ===     ║");
            System.out.println("║ Titulo: " + task.getTitle());
            System.out.println("║ Descrição: " + task.getDescription());
            System.out.println("║ Inicio: " + task.getStart().toString());
            System.out.println("║ Fim: " + task.getEnd().toString());
            System.out.println("║ Numero de submissões: " + task.getNumberOfSubmissions());
            System.out.println("║ \n║ Seleciona uma opção:");

            if (isStudent && currentEdition.getStatus() == Status.ACTIVE) {
                System.out.println("║ 1. Listar Submissões");
                System.out.println("║ 2. Adicionar Submissão");
                System.out.println("║ 3. Back");
            } else {
                System.out.println("║ 1. Listar Submissões");
                System.out.println("║ 2. Back");
            }

            int option = 0;
            try {
                if (isStudent) {
                    do {
                        System.out.print("║ Opção: ");
                        option = Integer.parseInt(reader.readLine());
                    } while (option < 1 || option > 3);

                } else {
                    do {
                        System.out.print("║ Opção: ");
                        option = Integer.parseInt(reader.readLine());
                    } while (option < 1 || option > 2);
                }

                if (option == 1) {
                    listSubmissions(task);
                } else if (option == 2 && isStudent && currentEdition.getStatus() == Status.ACTIVE) {
                    if (submitWork(task)) {
                        System.out.println("Submissão feita com sucesso!");
                    } else {
                        System.out.println("Submissão falhou!");
                    }
                } else {
                    exit = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        System.out.println("╚══════════════════════════════╝");
    }

    
    private boolean submitWork(Task task) {
        System.out.println("=== Submit Work ===");
        System.out.println("Student: " + loggedInParticipant.getEmail());
        try {
            System.out.print("Enter the text: ");
            String text = reader.readLine();

            Submission newSubmission = new ImpSubmission((Student) loggedInParticipant, text);
            System.out.println("Date of Submission: " + newSubmission.getDate().toString());

            try {
                task.addSubmission(newSubmission);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;

        } catch (IOException e) {
            System.out.println("Error reading input.");
            return false;
        }
    }


    private void showProjectProgress() {
        boolean exit = false;

        System.out.println("╔═══════════════════════════════════╗");
        System.out.println("║  == Progresso do Projeto ==  ║");
        System.out.println(currentProject.toString());
        System.out.println("\n");
        System.out.println("║ 1. Back");
        while (!exit) {
            try {
                System.out.print("║ Seleciona opção: ");
                int option = Integer.parseInt(reader.readLine());
                if (option == 1) {
                    exit = true;
                } else {
                    System.out.println("║ Invalid Selection");
                }
            } catch (NumberFormatException e) {
                System.out.println("║ Invalid input. Please enter a number.");
            } catch (IOException ex) {
                System.out.println("║ Error reading input!");
            }
        }
        System.out.println("╚══════════════════════════════════════╝");
    }

    private Participant[] listaPP() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   === Todos os participantes registados ===   ║");
        Participant[] participants = pm.getParticipants();
        int counter = 0;
        if (currentProject.getNumberOfFacilitators() > 0) {
            System.out.println("║ Facilitadores: ");
            for (int i = 0; i < currentProject.getNumberOfFacilitators(); i++) {
                if (participants[counter] instanceof Facilitator) {
                    ImpFacilitator facilitator = (ImpFacilitator) participants[counter];
                    System.out.println("║ " + (counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ") - Hetero Evaluation: " + facilitator.getEvaluation());
                    if (facilitator.getEvaluation() == null) {
                        System.out.println();
                        System.out.print("\tNota: ");
                        try {
                            int notaStudent = Integer.parseInt(reader.readLine());
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        System.out.println((counter + 1) + ". " + facilitator.getName() + "(" + facilitator.getEmail() + ") - Hetero Evaluation: " + facilitator.getEvaluation());
                        System.out.println();
                    }
                    counter++;
                }
            }
        }

        if (currentProject.getNumberOfStudents() > 0) {
            System.out.println("║ Estudantes: ");
            for (int i = 0; i < currentProject.getNumberOfStudents(); i++) {
                if (participants[counter] instanceof Student) {
                    ImpStudent student = (ImpStudent) participants[counter];
                    System.out.println("║ " + (counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ")- Hetero Evaluation: " + student.getEvaluation());
                    if (student.getEvaluation() == null) {
                        System.out.print("\tNota: ");
                        try {
                            int notaStudent = Integer.parseInt(reader.readLine());
                            student.setHeteroEv(notaStudent);
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println((counter + 1) + ". " + student.getName() + "(" + student.getEmail() + ") - Hetero Evaluation: " + student.getEvaluation());
                        System.out.println();
                    }
                    counter++;
                }
            }
        }

        if (currentProject.getNumberOfPartners() > 0) {
            System.out.println("║ Parceiros: ");
            for (int i = 0; i < currentProject.getNumberOfPartners(); i++) {
                if (participants[counter] instanceof Partner) {
                    ImpPartner partner = (ImpPartner) participants[counter];
                    System.out.println("║ " + (counter + 1) + ". " + participants[counter].getName() + "(" + participants[counter].getEmail() + ") - Hetero Evaluation: " + partner.getEvaluation());
                    if (partner.getEvaluation() == null) {
                        System.out.println();
                        System.out.print("\tNota: ");
                        try {
                            int notaStudent = Integer.parseInt(reader.readLine());
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println((counter + 1) + ". " + partner.getName() + "(" + partner.getEmail() + ") - Hetero Evaluation: " + partner.getEvaluation());
                        System.out.println();
                    }

                    counter++;
                }
            }
        }

        System.out.println("╚═══════════════════════════════════════════════╝");
        return participants;
    }

    private void showAddParticipant() throws IllegalNumberOfParticipantType, ParticipantAlreadyInProject {

        Participant[] participants = listParticipants();
        int counter = participants.length;
        System.out.println((counter + 2) + ". Back");

        try {
            int participantNumber = Integer.parseInt(reader.readLine());

            if (counter != 0 && participantNumber >= 1 && participantNumber <= participants.length) {
                Participant selectedParticipant = participants[participantNumber - 1];
                currentProject.addParticipant(selectedParticipant);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");

        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

    }

    private void showAddTask() throws IllegalNumberOfTasks, TaskAlreadyInProject {
        System.out.println("╔═════════════════════════════════════════╗");
        System.out.println("║        === Adionar Task ===        ║");
        try {

            System.out.print("\nTitulo: ");
            String title = reader.readLine();

            System.out.print("\nDescrição: ");
            String description = reader.readLine();

            LocalDate start = null;
            while (start == null) {
                System.out.print("Data Inicio (yyyy-mm-dd): ");
                String startDate = reader.readLine();
                try {
                    start = LocalDate.parse(startDate);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Siga o formato yyyy-mm-dd");
                }
            }

            LocalDate end = null;
            while (end == null) {
                System.out.print("Data Fim (yyyy-mm-dd): ");
                String endDate = reader.readLine();
                try {
                    end = LocalDate.parse(endDate);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Siga o formato yyyy-mm-dd");
                }
            }

            Task task = new ImpTask(title, description, start, end, 0);
            currentProject.addTask(task);
            System.out.println("Task adicionado com sucesso.\n");
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
        System.out.println("╚════════════════════════════════════════════╝");
    }

    private void showAddProject() {
        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║     ===== Adiciona um Projeto Novo =====     ║");
        try {

            System.out.print("\nNome: ");
            String name = reader.readLine();

            System.out.print("\nDescrição: ");
            String description = reader.readLine();

            System.out.println("Tags: (tag1,tag2,tag3) ");
            String allTags = reader.readLine();

            String[] tagsArray = allTags.split(",");
            for (int i = 0; i < tagsArray.length; i++) {
                tagsArray[i] = tagsArray[i].trim();
            }
            currentEdition.addProject(name, description, tagsArray);
            System.out.println("Projeto adicionado com sucesso");

        } catch (IOException | ParseException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("╚═════════════════════════════════════════════════════╝");
    }

    private void showRemoveEditionMenu() throws EditionDontExist {
        boolean complete = false;

        try {
            Edition removedEdition = cbl.removeEdition(currentEdition.getName());
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║       Edição removida com sucesso.      ║");
            while (!complete) {
                try {

                    System.out.println("Desejas guarda-lo num ficheiro JSON? (sim/no)");
                    String saveAnswer = reader.readLine();
                    if (saveAnswer.equalsIgnoreCase("yes")) {

                        System.out.println("Nome do ficheiro: ");
                        String name = reader.readLine();
                        if (name.equalsIgnoreCase("cbl") || name.equalsIgnoreCase("instituitions")
                                || name.equalsIgnoreCase("project_template") || name.equalsIgnoreCase("users")) {
                            System.out.println("Nome invalido.");
                        } else {
                            String path = null;
                            if (name.contains(".json")) {
                                path = "files/" + name;
                            } else {
                                path = "files/" + name + ".json";
                            }
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("Edição removida", ((ImpEdition) removedEdition).toJsonObj());
                                FileWriter fileWriter = new FileWriter(path);
                                fileWriter.write(jsonObject.toJSONString());
                                fileWriter.close();
                                complete = true;

                            } catch (IOException e) {
                                e.getMessage();
                            }
                        }

                    } else {
                        complete = true;
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("╚══════════════════════════════════╝");
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        /*Menu menu = new Menu();
        menu.initialize();*/

        ParticipantsManager pm = new ParticipantsManager();
        CBLinterface cbl = new ImpCBL();
        InstituitionsManager im = new InstituitionsManager();

        if (pm.importData("src/Files/users3.json") && im.importData("src/Files/instituitions.json")) {
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║    Sucesso importando utilizadores.      ║");
            System.out.println("╚════════════════════════════════════════════════╝");
        }
        if (cbl.importDataJSON("files/cbl2.json")) {

            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║      Sucesso importando informação.      ║");
            System.out.println("╚════════════════════════════════════════════════╝");
        } else {
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║        Erro importando informação.       ║");
            System.out.println("╚════════════════════════════════════════════════╝");
        }

        Menu menu = new Menu((ImpCBL) cbl, pm, im);
        menu.initialize();        
        
        
                if (cbl.exportJSON("files/cbl2.json")) {
                    System.out.println("╔════════════════════════════════════════════════╗");
                    System.out.println("║      Sucesso exportando informação.      ║");
                    System.out.println("╚════════════════════════════════════════════════╝");
                } else {
                    System.out.println("╔════════════════════════════════════════════════╗");
                    System.out.println("║        Erro importando informação.       ║");
                    System.out.println("╚════════════════════════════════════════════════╝");
                }
                if (pm.export("src/Files/users3.json") && im.export("src/Files/instituitions.json")) {
                    System.out.println("╔════════════════════════════════════════════════╗");
                    System.out.println("║      Sucesso importando utilizadores.    ║");
                    System.out.println("╚════════════════════════════════════════════════╝");
                }
                

        
    }

}
