/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.factories;

import main.enums.StatusAnimal;

/**
 *
 * @author micha
 */
public class StatusAnimalFactory {
    
    public static StatusAnimal GetStatus(String status){
        switch (status) {
            case "Para adocao" -> {
                return StatusAnimal.PA;
            }
            case "Adotado" -> {
                return StatusAnimal.A;
            }
            case "Falecido" -> {
                return StatusAnimal.F;
            }
            case "Desaparecido" -> {
                return StatusAnimal.D;
            }
            default -> throw new AssertionError();
        }
    
    }
    
}
