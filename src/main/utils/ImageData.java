/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

import javafx.scene.image.Image;

/**
 *
 * @author micha
 */
public class ImageData {
    private Image Imagem;
    private long TamanhoEmBytes;

    public ImageData(Image Imagem, long TamanhoEmBytes) {
        this.Imagem = Imagem;
        this.TamanhoEmBytes = TamanhoEmBytes;
    }

    public Image getImagem() {
        return Imagem;
    }

    public void setImagem(Image Imagem) {
        this.Imagem = Imagem;
    }

    public long getTamanhoEmBytes() {
        return TamanhoEmBytes;
    }

    public void setTamanhoEmBytes(long TamanhoEmBytes) {
        this.TamanhoEmBytes = TamanhoEmBytes;
    }
    
    
}
