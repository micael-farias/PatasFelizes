package main.utils;

import javafx.fxml.FXMLLoader;

 public class FXMLLoadResult<T> {
    private T result;
    private FXMLLoader loader;

    public FXMLLoadResult(T result, FXMLLoader loader) {
        this.result = result;
        this.loader = loader;
    }

    public T getResult() {
        return result;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
