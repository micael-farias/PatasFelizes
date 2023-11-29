/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.views.textfield;

import javafx.scene.control.ChoiceBox;

/**
 *
 * @author micha
 */
public class ChoiceBoxCostumized {

    private String value;
    
    public void initialize(ChoiceBox choiceBox){
         choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = newValue.toString();
        });
    }
    
    public String getValue(){
        return value;
    }
        
    public void setValue(String value){
        this.value = value;
    }
}
