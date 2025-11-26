
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class ArvoreAVL {
    NoAVL raiz;
    int totalFuncionarios;

    ArvoreAVL() {
        this.raiz = null;
        this.totalFuncionarios = 0;
    }

    int altura(NoAVL n) {
        return n == null ? 0 : n.altura;
    }

    int fatorBalanceamento(NoAVL n) {
        return n == null ? 0 : altura(n.esquerda) - altura(n.direita);
    }

    void atualizarAltura(NoAVL n) {
        n.altura = 1 + Math.max(altura(n.esquerda), altura(n.direita));
    }

    NoAVL rotacionarDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;
        x.direita = y;
        y.esquerda = T2;
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    NoAVL rotacionarEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;
        y.esquerda = x;
        x.direita = T2;
        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    NoAVL inserirNo(NoAVL no, Funcionario f) {
        if (no == null) {
            totalFuncionarios++;
            return new NoAVL(f.nomeCompleto, f);
        }
        int cmp = f.nomeCompleto.compareToIgnoreCase(no.chave);
        if (cmp < 0) {
            no.esquerda = inserirNo(no.esquerda, f);
        } else if (cmp > 0) {
            no.direita = inserirNo(no.direita, f);
        } else {
            // Lidar com nomes idênticos (requisito R6)
            no.lista.add(f); 
            totalFuncionarios++;
            return no;
        }
        atualizarAltura(no);
        int fb = fatorBalanceamento(no);
        
        // Rotações (casos de desbalanceamento)
        if (fb > 1 && f.nomeCompleto.compareToIgnoreCase(no.esquerda.chave) < 0) {
            return rotacionarDireita(no); // Caso Esquerda-Esquerda
        }
        if (fb < -1 && f.nomeCompleto.compareToIgnoreCase(no.direita.chave) > 0) {
            return rotacionarEsquerda(no); // Caso Direita-Direita
        }
        if (fb > 1 && f.nomeCompleto.compareToIgnoreCase(no.esquerda.chave) > 0) {
            no.esquerda = rotacionarEsquerda(no.esquerda);
            return rotacionarDireita(no); // Caso Esquerda-Direita
        }
        if (fb < -1 && f.nomeCompleto.compareToIgnoreCase(no.direita.chave) < 0) {
            no.direita = rotacionarDireita(no.direita);
            return rotacionarEsquerda(no); // Caso Direita-Esquerda
        }
        return no;
    }

    void inserirFuncionario(Funcionario f) {
        raiz = inserirNo(raiz, f);
    }

    List<Funcionario> coletarEmOrdem(NoAVL no, List<Funcionario> acumulador) {
        if (no == null) return acumulador;
        coletarEmOrdem(no.esquerda, acumulador);
        acumulador.addAll(no.lista);
        coletarEmOrdem(no.direita, acumulador);
        return acumulador;
    }

    List<Funcionario> obterTodosFuncionarios() {
        List<Funcionario> lista = new ArrayList<>();
        return coletarEmOrdem(raiz, lista);
    }

    // Método auxiliar recursivo para impressão
    void imprimirEmOrdem(NoAVL no, boolean mostrarId) {
        if (no == null) return;
        imprimirEmOrdem(no.esquerda, mostrarId);
        for (Funcionario f : no.lista) {
            if (mostrarId) {
                System.out.println(f.toString());
            } else {
                System.out.println(f.toAxionString());
            }
        }
        imprimirEmOrdem(no.direita, mostrarId);
    }

    // Método público para impressão
    void imprimirTodos(boolean mostrarId) {
        if (raiz == null) {
            System.out.println("Nenhum registro.");
            return;
        }
        imprimirEmOrdem(raiz, mostrarId);
    }

    List<Funcionario> buscarPorNomeCompleto(String nome) {
        NoAVL atual = raiz;
        while (atual != null) {
            int cmp = nome.compareToIgnoreCase(atual.chave);
            if (cmp == 0) {
                return new ArrayList<>(atual.lista);
            } else if (cmp < 0) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }
        return new ArrayList<>();
    }

    List<Funcionario> buscarPorLetraInicial(char c) {
        List<Funcionario> todos = obterTodosFuncionarios();
        List<Funcionario> resultado = new ArrayList<>();
        for (Funcionario f : todos) {
            if (!f.nomeCompleto.isEmpty() && Character.toUpperCase(f.nomeCompleto.charAt(0)) == Character.toUpperCase(c)) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    int tamanho() {
        return totalFuncionarios;
    }
}
