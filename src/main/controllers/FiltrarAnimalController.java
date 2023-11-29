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
import static main.controllers.AnimalFormularioController.textFormatter;
import main.enums.Mapping;
import main.enums.MensagemTipo;
import main.interfaces.Acao;
import main.interfaces.InicializadorComAcao;
import main.model.Animal;
import main.model.FiltrosAnimais;
import main.services.AnimalService;
import main.utils.DateHelper;
import static main.utils.DateHelper.invalidString;
import main.utils.NumberHelper;
import static main.utils.TextFieldUtils.applyNumericMask;
import main.views.textfield.CheckBoxCustomized;
import main.views.textfield.ChoiceBoxCostumized;

public class FiltrarAnimalController implements InicializadorComAcao {

    @FXML
    private ImageView checkBoxFeminino;

    @FXML
    private ImageView checkBoxMasculino, checkBoxSexoDesconhecido;

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
    private Button filtrar, cancelarFiltro;

    private CheckBoxCustomized checkCostumizedFeminino;
    private CheckBoxCustomized checkCostumizedSexoDesconhecido;
    private CheckBoxCustomized checkCostumizedMasculino;
    private CheckBoxCustomized checkCostumizedSim;
    private CheckBoxCustomized checkCostumizedNao;

    private ChoiceBoxCostumized ordenacaoChoiceBox;
    private ChoiceBoxCostumized statusChoiceBox;

    private AnimalService animalService;
    private Acao acao;

    @Override
    public void Inicializar(String telaOrigem, Pane contentFather, Stage primmaryStage, Pane blackShadow, Acao acao, Object[] dados) {
        this.acao = acao;
        animalService = new AnimalService();
        inicializarComponentes(primmaryStage, blackShadow);
        if (AnimalService.filtros != null) setData();
    }

    private void handleFiltrar(Stage primmaryStage, Pane blackShadow) {
        List<Animal> animaisFiltrados = ObterFiltros();
        if (animaisFiltrados != null) {
                 acao.RealizarAcao(new Object[]{ animaisFiltrados, AnimalService.filtros });
            App.getInstance().FecharDialog(primmaryStage, blackShadow);
        }
    }

    private void inicializarComponentes(Stage primmaryStage, Pane blackShadow) {
        checkCostumizedFeminino = new CheckBoxCustomized();
        checkCostumizedSexoDesconhecido = new CheckBoxCustomized();
        checkCostumizedMasculino = new CheckBoxCustomized();
        checkCostumizedSim = new CheckBoxCustomized();
        checkCostumizedNao = new CheckBoxCustomized();

        ordenacaoChoiceBox = new ChoiceBoxCostumized();
        statusChoiceBox = new ChoiceBoxCostumized();

        tiposOrdenacaoChoiceBox.setItems(Mapping.getOrdenacoes());
        statusCheckBox.setItems(Mapping.getStatus());

        checkCostumizedSexoDesconhecido.inicializar(checkBoxSexoDesconhecido);
        checkCostumizedFeminino.inicializar(checkBoxFeminino);
        checkCostumizedMasculino.inicializar(checkBoxMasculino);
        checkCostumizedSim.inicializar(checkBoxSim);
        checkCostumizedNao.inicializar(checkBoxNao);
        statusChoiceBox.initialize(statusCheckBox);
        ordenacaoChoiceBox.initialize(tiposOrdenacaoChoiceBox);
        filtrar.setOnMouseClicked(e -> handleFiltrar(primmaryStage, blackShadow));                
        cancelarFiltro.setOnMouseClicked(e ->{
           App.getInstance().FecharDialog(primmaryStage, blackShadow);
        });
        
        textFormatter(primeiraIntervaloMeses, primeiroIntervaloAno);
        textFormatter(segundoIntervaloMeses, segundoIntervaloAno);

    }

    
    private List<Animal> ObterFiltros() {
        AnimalService.filtros = AnimalService.filtros == null ? new FiltrosAnimais() : AnimalService.filtros;

        AnimalService.filtros.setOrdenacaoSelecionada(ordenacaoChoiceBox.getValue());
        AnimalService.filtros.setStatusSelecionado(statusChoiceBox.getValue());
        AnimalService.filtros.setFiltrarMasculino(checkCostumizedMasculino.getChecked());
        AnimalService.filtros.setFiltrarFeminino(checkCostumizedFeminino.getChecked());
        AnimalService.filtros.setFiltrarSexoDesconhecido(checkCostumizedSexoDesconhecido.getChecked());
        AnimalService.filtros.setFiltrarCastradoSim(checkCostumizedSim.getChecked());
        AnimalService.filtros.setFiltrarCastradoNao(checkCostumizedNao.getChecked());
        AnimalService.filtros.setIntervaloPrimeiroAno(primeiroIntervaloAno.getText());
        AnimalService.filtros.setIntervaloPrimeiroMeses(primeiraIntervaloMeses.getText());
        AnimalService.filtros.setIntervaloSegundoAno(segundoIntervaloAno.getText());
        AnimalService.filtros.setIntervaloSegundoMeses(segundoIntervaloMeses.getText());

        if (!validarIntervaloDataNascimento(AnimalService.filtros.getIntervaloPrimeiroAno(), AnimalService.filtros.getIntervaloPrimeiroMeses(),
                AnimalService.filtros.getIntervaloSegundoAno(), AnimalService.filtros.getIntervaloSegundoMeses())) return null;

        Calendar invervaloUm = DateHelper.ConvertMesAnoToCalendar(AnimalService.filtros.getIntervaloPrimeiroAno(),
                AnimalService.filtros.getIntervaloPrimeiroMeses());
        Calendar invervaloDois = DateHelper.ConvertMesAnoToCalendar(AnimalService.filtros.getIntervaloSegundoAno(),
                AnimalService.filtros.getIntervaloSegundoMeses());

        return animalService.selecionarAnimais(Mapping.GetKeyOrdenacoes(AnimalService.filtros.getOrdenacaoSelecionada()),
                Mapping.GetKeyStatus(AnimalService.filtros.getStatusSelecionado()), AnimalService.filtros.isFiltrarMasculino(),
                AnimalService.filtros.isFiltrarFeminino(), AnimalService.filtros.isFiltrarSexoDesconhecido(), AnimalService.filtros.isFiltrarCastradoSim(), AnimalService.filtros.isFiltrarCastradoNao(),
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
                App.getInstance().SetMensagem(MensagemTipo.ERRO, "O segundo intervalo deve ser maior que o primeiro.", null);
                return false;
            }
        } catch (NumberFormatException e) {
            App.getInstance().SetMensagem(MensagemTipo.ERRO, "Os valores do intervalo de data devem ser números válidos.", null);
            return false;
        }

        return true;
    }

    public void setData() {
        tiposOrdenacaoChoiceBox.setValue(AnimalService.filtros.getOrdenacaoSelecionada());
        ordenacaoChoiceBox.setValue(AnimalService.filtros.getOrdenacaoSelecionada());
        statusCheckBox.setValue(AnimalService.filtros.getStatusSelecionado());
        statusChoiceBox.setValue(AnimalService.filtros.getStatusSelecionado());

        checkCostumizedMasculino.setImage(AnimalService.filtros.isFiltrarMasculino(), checkBoxMasculino);
        checkCostumizedFeminino.setImage(AnimalService.filtros.isFiltrarFeminino(), checkBoxFeminino);
        checkCostumizedSexoDesconhecido.setImage(AnimalService.filtros.isFiltrarSexoDesconhecido(), checkBoxSexoDesconhecido);
        checkCostumizedSim.setImage(AnimalService.filtros.isFiltrarCastradoSim(), checkBoxSim);
        checkCostumizedNao.setImage(AnimalService.filtros.isFiltrarCastradoNao(), checkBoxNao);

        primeiroIntervaloAno.setText(AnimalService.filtros.getIntervaloPrimeiroAno());
        primeiraIntervaloMeses.setText(AnimalService.filtros.getIntervaloPrimeiroMeses());
        segundoIntervaloAno.setText(AnimalService.filtros.getIntervaloSegundoAno());
        segundoIntervaloMeses.setText(AnimalService.filtros.getIntervaloSegundoMeses());
    }
}
