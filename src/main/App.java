package main;

import main.utils.InicializarFormulario;


public class App {
    
    private static InicializarFormulario inicializar;
    
    public static InicializarFormulario getInstance(){
        if(inicializar == null){
            inicializar = new InicializarFormulario();
        }
        
        return inicializar;
    }  
}
