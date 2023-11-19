package main.views.textfield;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class AutoCompleteTextField extends TextField {

    private final ObservableList<String> suggestions;

    public AutoCompleteTextField() {
        suggestions = FXCollections.observableArrayList();
        configureAutoComplete();
    }

    private void configureAutoComplete() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                hideSuggestions();
            } else {
                showSuggestions(newValue);
            }
        });

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                hideSuggestions();
            }
        });
    }

    private void showSuggestions(String filter) {
        ObservableList<String> filteredSuggestions = FXCollections.observableArrayList();
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().startsWith(filter.toLowerCase())) {
                filteredSuggestions.add(suggestion);
            }
        }

        if (!filteredSuggestions.isEmpty()) {
            // Mostrar as sugestões
            // Aqui você pode usar um ListView, um PopOver ou outra abordagem para exibir as sugestões
            System.out.println("Sugestões: " + filteredSuggestions);
        } else {
            hideSuggestions();
        }
    }

    private void hideSuggestions() {
        System.out.println("Esconder sugestões");
    }

    public void setSuggestions(ObservableList<String> suggestions) {
        this.suggestions.setAll(suggestions);
    }
}
