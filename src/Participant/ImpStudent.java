package Participant;


import ma02_resources.participants.Contact;
import ma02_resources.participants.Instituition;
import ma02_resources.participants.Student;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafas
 */
public class ImpStudent extends ImpParticipant implements Student{
    
    private static int counter = 0;
    private int number;

    public ImpStudent( String name, String email, Contact contact, Instituition instituition) {
        super(name, email, contact, instituition);
        this.number = ++counter;
    }
        
    
    @Override
    public int getNumber() {
        return this.number;
    }
    
}
