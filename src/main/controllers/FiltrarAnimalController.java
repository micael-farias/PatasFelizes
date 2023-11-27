package main.controllers;

import java.util.Calendar;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.App;
import main.enums.Mapping;
import main.enums.MensagemTipo;
import main.interfaces.Acao;
import main.interfaces.InicializadorComAcao;
import main.model.Animal;
import main.model.FiltrosAnimalModel;
import main.services.AnimalService;
import main.utils.DateHelper;
import static main.utils.DateHelper.invalidString;
import main.utils.NumberHelper;
import main.views.textfield.CheckBoxCustomized;
import main.views.textfield.ChoiceBoxCostumized;

public class FiltrarAnimalController implements InicializadorComAcao {

    @FXML
    private ImageView checkBoxFeminino;

    @FXML
    private ImageView checkBoxMasculino;

    @FXML
    private ImageView checkBoxNao;

    @FXML
    private ImageView checkBoxSim;

    @FXML
    private TextField primeiraIntervaloMeses;

    @FXML
    private TextField primeiroIntervaloAno;

    @FXML
    private TextField segundoIntervaloAno;

    @FXML
    private TextField segundoIntervaloMeses;

    @FXML
    private ChoiceBox<String> statusCheckBox;

    @FXML
    private ChoiceBox<String> tiposOrdenacaoChoiceBox;

    @FXML
    private Button filtrar;

    private CheckBoxCustomized checkCostumizedFeminino;
    private CheckBoxCustomized checkCostumizedMasculino;
    private CheckBoxCustomized checkCostumizedSim;
    private CheckBoxCustomized checkCostumizedNao;

    private ChoiceBoxCostumized ordenacaoChoiceBox;
    private ChoiceBoxCostumized statusChoiceBox;

    private static FiltrosAnimalModel filtros;
    private AnimalService animalService;
    private Acao acao;

    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao,
            Object[] dados) {
        this.acao = acao;
        animalService = new AnimalService();
        inicializarComponentes(primmaryStage, blackShadow);
        if (filtros != null) setData();
    }

    private void handleFiltrar(Stage primmaryStage, Pane blackShadow) {
        List<Animal> animaisFiltrados = ObterFiltros();
        if (animaisFiltrados != null) {
            acao.RealizarAcao(animaisFiltrados);
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        }
    }

    private void inicializarComponentes(Stage primmaryStage, Pane blackShadow) {
        checkCostumizedFeminino = new CheckBoxCustomized();
        checkCostumizedMasculino = new CheckBoxCustomized();
        checkCostumizedSim = new CheckBoxCustomized();
        checkCostumizedNao = new CheckBoxCustomized();

        ordenacaoChoiceBox = new ChoiceBoxCostumized();
        statusChoiceBox = new ChoiceBoxCostumized();

        tiposOrdenacaoChoiceBox.setItems(Mapping.getOrdenacoes());
        statusCheckBox.setItems(Mapping.getStatus());

        checkCostumizedFeminino.inicializar(checkBoxFeminino);
        checkCostumizedMasculino.inicializar(checkBoxMasculino);
        checkCostumizedSim.inicializar(checkBoxSim);
        checkCostumizedNao.inicializar(checkBoxNao);
        statusChoiceBox.initialize(statusCheckBox);
        ordenacaoChoiceBox.initialize(tiposOrdenacaoChoiceBox);
        filtrar.setOnMouseClicked(e -> handleFiltrar(primmaryStage, blackShadow));
    }

    
    private List<Animal> ObterFiltros() {
        filtros = filtros == null ? new FiltrosAnimalModel() : filtros;

        filtros.setOrdenacaoSelecionada(ordenacaoChoiceBox.getValue());
        filtros.setStatusSelecionado(statusChoiceBox.getValue());
        filtros.setFiltrarMasculino(checkCostumizedMasculino.getChecked());
        filtros.setFiltrarFeminino(checkCostumizedFeminino.getChecked());
        filtros.setFiltrarCastradoSim(checkCostumizedSim.getChecked());
        filtros.setFiltrarCastradoNao(checkCostumizedNao.getChecked());
        filtros.setIntervaloPrimeiroAno(primeiroIntervaloAno.getText());
        filtros.setIntervaloPrimeiroMeses(primeiraIntervaloMeses.getText());
        filtros.setIntervaloSegundoAno(segundoIntervaloAno.getText());
        filtros.setIntervaloSegundoMeses(segundoIntervaloMeses.getText());

        if (!validarIntervaloDataNascimento(filtros.getIntervaloPrimeiroAno(), filtros.getIntervaloPrimeiroMeses(),
                filtros.getIntervaloSegundoAno(), filtros.getIntervaloSegundoMeses())) return null;

        Calendar invervaloUm = DateHelper.ConvertMesAnoToCalendar(filtros.getIntervaloPrimeiroAno(),
                filtros.getIntervaloPrimeiroMeses());
        Calendar invervaloDois = DateHelper.ConvertMesAnoToCalendar(filtros.getIntervaloSegundoAno(),
                filtros.getIntervaloSegundoMeses());

        return animalService.selecionarAnimais(Mapping.GetKeyOrdenacoes(filtros.getOrdenacaoSelecionada()),
                Mapping.GetKeyStatus(filtros.getStatusSelecionado()), filtros.isFiltrarMasculino(),
                filtros.isFiltrarFeminino(), filtros.isFiltrarCastradoSim(), filtros.isFiltrarCastradoNao(),
                invervaloDois, invervaloUm);
    }

    private boolean validarIntervaloDataNascimento(String primeiroAno, String primeiroMeses, String segundoAno, String segundoMeses) {
        if (invalidString(primeiroAno) && invalidString(primeiroMeses) && invalidString(segundoAno) && invalidString(segundoMeses)) {
            return true;
        }

        try {
            int primeiroAnoInt = NumberHelper.IntegerParse(primeiroAno);
            int primeiroMesesInt = NumberHelper.IntegerParse(primeiroMeses);
            int segundoAnoInt = NumberHelper.IntegerParse(segundoAno);
            int segundoMesesInt = NumberHelper.IntegerParse(segundoMeses);

            if (segundoAnoInt < primeiroAnoInt || (segundoAnoInt == primeiroAnoInt && segundoMesesInt < primeiroMesesInt)) {
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "O segundo intervalo deve ser maior que o primeiro.");
                return false;
            }
        } catch (NumberFormatException e) {
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Os valores do intervalo de data devem ser números válidos.");
            return false;
        }

        return true;
    }

    public void setData() {
        tiposOrdenacaoChoiceBox.setValue(filtros.getOrdenacaoSelecionada());
        ordenacaoChoiceBox.setValue(filtros.getOrdenacaoSelecionada());
        statusCheckBox.setValue(filtros.getStatusSelecionado());
        statusChoiceBox.setValue(filtros.getStatusSelecionado());

        checkCostumizedMasculino.setImage(filtros.isFiltrarMasculino(), checkBoxMasculino);
        checkCostumizedFeminino.setImage(filtros.isFiltrarFeminino(), checkBoxFeminino);
        checkCostumizedSim.setImage(filtros.isFiltrarCastradoSim(), checkBoxSim);
        checkCostumizedNao.setImage(filtros.isFiltrarCastradoNao(), checkBoxNao);

        primeiroIntervaloAno.setText(filtros.getIntervaloPrimeiroAno());
        primeiraIntervaloMeses.setText(filtros.getIntervaloPrimeiroMeses());
        segundoIntervaloAno.setText(filtros.getIntervaloSegundoAno());
        segundoIntervaloMeses.setText(filtros.getIntervaloSegundoMeses());
    }
}
