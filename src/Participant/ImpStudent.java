/*
 * Nome: Rafael Filipe Silva Medina Coronel
 * Número: 8190348
 * Turma: LSIRCT1
 *
 * Nome: Roger Seiji Hernandez Nakauchi
 * Número: 8210005
 * Turma: LSIRCT1
 */
package Participant;


import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Student;


public class ImpStudent extends ImpParticipant implements Student{
    
    /*
     * Variable that increases Student's number
     */
    private static int counter = 0;
    /*
     * Variable that defines Student's number
     */
    private int number;

    /*
     * This is the constructor method for Student
     *
     * @param name Student name
     * @param email Student email
     * @param email Student contact
     * @param email Student institution
     */
    public ImpStudent( String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.number = ++counter;
    }
        
    
    @Override
    public int getNumber() {
        return this.number;
    }
    
}
