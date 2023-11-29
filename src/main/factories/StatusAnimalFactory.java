/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.factories;

import javafx.scene.paint.Color;
import main.enums.StatusAnimal;

/**
 *
 * @author micha
 */
public class StatusAnimalFactory {
    
    public static StatusAnimal GetStatus(String status){
        switch (status) {
            case "Para adoção" -> {
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
            default  -> {
                return StatusAnimal.PA;
            }
        }
    
    }
    public static String GetStatusString(String status) {
        switch (status) {
            case "PA":
                return "Para adoção";
            case "A":
                return "Adotado";
            case "F":
                return "Falecido";
            case "D":
                return "Desaparecido";
            default:
                return "Para adoção";
        }
    }  
    
    public static Color GetColorStatus(String status){
        switch (status) {
            case "PA" -> {
                return Color.web("#7CD474");
            }
            case "A" -> {
                return Color.web("#3855FC");
            }
            case "F" -> {
                return Color.web("#757575");
            }
            case "D" -> {
                return Color.web("#D47474");
            }
            default -> throw new AssertionError();
        }
    
    }
    
}
