import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class NoAVL {
    String chave;
    List<Funcionario> lista;
    NoAVL esquerda;
    NoAVL direita;
    int altura;

    NoAVL(String chave, Funcionario f) {
        this.chave = chave;
        this.lista = new ArrayList<>();
        this.lista.add(f);
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }
}
