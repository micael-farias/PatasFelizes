/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.interfaces.Acao;
import main.interfaces.Inicializador;
import main.interfaces.InicializadorComAcao;
import main.interfaces.InicializadorComDado;
import main.model.Adocao;
import main.model.Adotante;
import main.services.AdocaoServices;
import static main.utils.Constantes.FORM_ANIMAL_DETALHES;
import static main.utils.DateHelper.invalidString;
import main.utils.ValidacaoUtils;
import javafx.scene.control.TextInputControl;
import static main.utils.TextFieldUtils.applyNumericMask;
import static main.utils.TextFieldUtils.autoCapitalizeFirstLetter;

/**
 *
 * @author micha
 */
public class CadastrarAdocaoController extends CustomController implements InicializadorComAcao{

   @FXML
    private TextField bairroTutor;

    @FXML
    private Button cancelarCadastro;

    @FXML
    private TextField cepTutor;

    @FXML
    private TextField cidadeTutor;

    @FXML
    private TextField complementoEnd;

    @FXML
    private TextField nomeTutor;

    @FXML
    private TextField numeroCasa;

    @FXML
    private TextField ruaTutor;

    @FXML
    private Button salvarAdocao;

    @FXML
    private TextField telefoneTutor;
    
    AdocaoServices adocaoServices;
    private Adocao adocao;
    private int idAnimal;
    Acao acao;
    
    public void setListeners(Pane contentFather, Stage primmaryStage, Pane blackShadow) {
       salvarAdocao.setOnMouseClicked(e->{
            if(CadastrarAdocao() == null) return;
            acao.RealizarAcao(new Object[]{});
            App.getInstance().FecharDialog(primmaryStage,blackShadow);
                    
        });
       
       cancelarCadastro.setOnMouseClicked(e->{
                App.getInstance().FecharDialog(primmaryStage, blackShadow);
                    
        });
       
        autoCapitalizeFirstLetter(nomeTutor);
        autoCapitalizeFirstLetter(ruaTutor);
        autoCapitalizeFirstLetter(cidadeTutor);
        autoCapitalizeFirstLetter(bairroTutor);
        autoCapitalizeFirstLetter(complementoEnd);
        applyNumericMask(telefoneTutor);
        applyNumericMask(numeroCasa);
    }
    
    public boolean validarAdocao(String nome, String telefone){
        boolean nomeValido = ValidacaoUtils.validarCampo(nome, nomeTutor, "O nome do tutor não deve ser vazio");
        boolean telefoneValido = ValidacaoUtils.validarCampo(telefone, telefoneTutor, "O telefone do tutor não deve ser vazio");
        return nomeValido && telefoneValido;
    }
    
    
    public Adocao CadastrarAdocao(){
        String tutor = nomeTutor.getText();
        String telefone = telefoneTutor.getText();
        String cep = cepTutor.getText();
        String cidade = cidadeTutor.getText();
        String bairro = bairroTutor.getText();
        String rua = ruaTutor.getText();
        String numero = numeroCasa.getText();
        String complemento = complementoEnd.getText();
        int idAdocao = adocao == null ? -1 : adocao.getId();
        int idAdotante = adocao == null || adocao.getAdotante() == null ? -1 : adocao.getAdotante().getId();
        
        if(!validarAdocao(tutor, telefone)) return null;
        
        return adocaoServices.Salvar(idAdocao, idAdotante, tutor, telefone, cep, rua, cidade, bairro, numero, complemento, idAnimal);
    
    }
    
    public void setData(){
        if(adocao != null){
            Adotante adotante = adocao.getAdotante();
            nomeTutor.setText(adotante.getNome());
            telefoneTutor.setText(adotante.getContato());
            cepTutor.setText(adotante.getCep());
            cidadeTutor.setText(adotante.getCidade());
            bairroTutor.setText(adotante.getBairro());
            ruaTutor.setText(adotante.getRua());
            numeroCasa.setText(adotante.getNumero());
            complementoEnd.setText(adotante.getComplemento());
        }
    }

    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
        this.acao =acao;
        idAnimal = ObterDadoArray(dados, 0) == null ? -1 : (int) ObterDadoArray(dados, 0);
        adocao = ObterDadoArray(dados, 1) == null ? null : (Adocao) ObterDadoArray(dados, 1);
        adocaoServices = new AdocaoServices();
        setListeners(contentFather, primmaryStage, blackShadow);
        setData();
        
    }

    
}
