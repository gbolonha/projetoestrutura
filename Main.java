import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    static ArvoreAVL axionAVL = new ArvoreAVL();
    static ArvoreAVL titaniumAVL = new ArvoreAVL();
    static ArvoreAVL queenBeeAVL = new ArvoreAVL();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            menu();
            String opt = sc.nextLine().trim();
            if (opt.equals("1")) inserirAxion();
            else if (opt.equals("2")) exibirAxion();
            else if (opt.equals("3")) inserirTitanium();
            else if (opt.equals("4")) exibirTitanium();
            else if (opt.equals("5")) unificar();
            else if (opt.equals("6")) inserirQueenBee();
            else if (opt.equals("7")) exibirQueenBee();
            else if (opt.equals("8")) exibirPorLetra();
            else if (opt.equals("9")) buscarPorNome();
            else if (opt.equals("10")) topNSalarios();
            else if (opt.equals("11")) { System.out.println("Saindo..."); break; }
            else System.out.println("Opção inválida.");
            System.out.println();
        }
    }

    static void menu() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("1. Inserir dados de funcionários na empresa Axion");
        System.out.println("2. Exibir lista de dados dos funcionários de Axion ordenados pelo Nome Completo");
        System.out.println("3. Inserir dados de funcionários na empresa Titanium");
        System.out.println("4. Exibir lista de dados dos funcionários de Titanium ordenados pelo Nome Completo");
        System.out.println("5. Unificar dados de Axion e Titanium na empresa QueenBee");
        System.out.println("6. Inserir dados de novo funcionário em QueenBee");
        System.out.println("7. Exibir lista de dados dos funcionários de QueenBee ordenados pelo Nome Completo");
        System.out.println("8. Exibir a lista de funcionários de QueenBee cujos nomes completos começam pela letra LETRA");
        System.out.println("9. Buscar os dados de um funcionário a partir de seu Nome Completo");
        System.out.println("10. Exibir a relação dos funcionários com os n salários mais altos em QueenBee em ordem decrescente de salário");
        System.out.println("11. Sair");
        System.out.print("Escolha uma opção: ");
    }

    static void inserirAxion() {
        System.out.print("Quantos funcionários deseja inserir na Axion? ");
        int k = readInt();
        for (int i = 0; i < k; i++) {
            System.out.println("Funcionário " + (i+1));
            String nome = readLine("Nome Completo: ");
            String nasc = readLine("Data de Nascimento (dd/mm/aaaa): ");
            String contrat = readLine("Data de Contratação (dd/mm/aaaa): ");
            String dept = readLine("Departamento: ");
            String cargo = readLine("Cargo: ");
            double salario = readDouble("Salário: ");
            Funcionario f = new Funcionario(nome, 0, nasc, contrat, dept, cargo, salario); 
            axionAVL.inserirFuncionario(f);
            System.out.println("Inserido.");
        }
    }

    static void exibirAxion() {
        System.out.println("Funcionários da Axion (sem ID):");
        axionAVL.imprimirTodos(false); // ID = false
    }

    static void inserirTitanium() {
        System.out.print("Quantos funcionários deseja inserir na Titanium? ");
        int k = readInt();
        for (int i = 0; i < k; i++) {
            System.out.println("Funcionário " + (i+1));
            String nome = readLine("Nome Completo: ");
            String idStr = readLine("ID (valor inteiro): ");
            int id = 0;
            try { id = Integer.parseInt(idStr.trim()); } catch (Exception ex) { id = 0; }
            String nasc = readLine("Data de Nascimento (dd/mm/aaaa): ");
            String contrat = readLine("Data de Contratação (dd/mm/aaaa): ");
            String dept = readLine("Departamento: ");
            String cargo = readLine("Cargo: ");
            double salario = readDouble("Salário: ");
            Funcionario f = new Funcionario(nome, id, nasc, contrat, dept, cargo, salario);
            titaniumAVL.inserirFuncionario(f);
            System.out.println("Inserido.");
        }
    }

    static void exibirTitanium() {
        System.out.println("Funcionários da Titanium (com ID):");
        titaniumAVL.imprimirTodos(true); // ID = true
    }

    static void unificar() {
        List<Funcionario> fusao = new ArrayList<>();
        fusao.addAll(axionAVL.obterTodosFuncionarios());
        fusao.addAll(titaniumAVL.obterTodosFuncionarios());
        int n = fusao.size();
        if (n == 0) {
            System.out.println("Não há funcionários para unificar.");
            return;
        }
        queenBeeAVL = new ArvoreAVL();
        for (int i = 0; i < n; i++) {
            Funcionario fonte = fusao.get(i);
            // Atribui ID sequencial (Requisito R5)
            Funcionario f = new Funcionario(fonte.nomeCompleto, i+1, fonte.dataNascimento, fonte.dataContratacao, fonte.departamento, fonte.cargo, fonte.salario); 
            queenBeeAVL.inserirFuncionario(f);
        }
        System.out.printf("Unificação concluída. %d funcionários inseridos em QueenBee. IDs atribuídos de 1 a %d.\n", n, n);
    }

    static void inserirQueenBee() {
        if (queenBeeAVL == null) queenBeeAVL = new ArvoreAVL();
        String nome = readLine("Nome Completo: ");
        String nasc = readLine("Data de Nascimento (dd/mm/aaaa): ");
        String contrat = readLine("Data de Contratação (dd/mm/aaaa): ");
        String dept = readLine("Departamento: ");
        String cargo = readLine("Cargo: ");
        double salario = readDouble("Salário: ");
        int novoId = queenBeeAVL.tamanho() + 1; // ID sequencial para novo funcionário
        Funcionario f = new Funcionario(nome, novoId, nasc, contrat, dept, cargo, salario);
        queenBeeAVL.inserirFuncionario(f);
        System.out.println("Funcionário inserido em QueenBee com ID " + novoId);
    }

    static void exibirQueenBee() {
        if (queenBeeAVL == null || queenBeeAVL.tamanho() == 0) {
            System.out.println("QueenBee vazia. Execute a opção 5 primeiro.");
            return;
        }
        System.out.println("Funcionários da QueenBee (com ID):");
        queenBeeAVL.imprimirTodos(true); // ID = true
    }

    static void exibirPorLetra() {
        if (queenBeeAVL == null || queenBeeAVL.tamanho() == 0) {
            System.out.println("QueenBee vazia. Execute a opção 5 primeiro.");
            return;
        }
        System.out.print("Informe a LETRA: ");
        String s = sc.nextLine().trim();
        if (s.isEmpty()) { System.out.println("Letra inválida."); return; }
        char c = s.charAt(0);
        List<Funcionario> lista = queenBeeAVL.buscarPorLetraInicial(c);
        if (lista.isEmpty()) System.out.println("Nenhum funcionário com nome começando por '" + c + "'.");
        else {
            System.out.println("Funcionários que começam com '" + c + "':");
            for (Funcionario f : lista) System.out.println(f);
        }
    }

    static void buscarPorNome() {
        if (queenBeeAVL == null || queenBeeAVL.tamanho() == 0) {
            System.out.println("QueenBee vazia. Execute a opção 5 primeiro.");
            return;
        }
        String nome = readLine("Informe o Nome Completo: ");
        List<Funcionario> res = queenBeeAVL.buscarPorNomeCompleto(nome);
        if (res.isEmpty()) System.out.println("Nenhum funcionário encontrado com esse nome.");
        else {
            System.out.println("Resultados:");
            for (Funcionario f : res) System.out.println(f);
        }
    }

    static void topNSalarios() {
        if (queenBeeAVL == null || queenBeeAVL.tamanho() == 0) {
            System.out.println("QueenBee vazia. Execute a opção 5 primeiro.");
            return;
        }
        System.out.print("Digite n (quantidade de funcionários com maiores salários): ");
        int n = readInt();
        if (n <= 0) { System.out.println("Valor inválido. n deve ser maior que zero."); return; }
        List<Funcionario> todos = queenBeeAVL.obterTodosFuncionarios();
        
        // Ordena por salário de forma decrescente
        Collections.sort(todos, new Comparator<Funcionario>() {
            public int compare(Funcionario a, Funcionario b) {
                return Double.compare(b.salario, a.salario);
            }
        });
        
        int limite = Math.min(n, todos.size());
        System.out.println("Top " + limite + " maiores salários:");
        for (int i = 0; i < limite; i++) {
            System.out.println((i+1) + ". " + todos.get(i));
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt() {
        while (true) {
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (Exception e) { System.out.print("Entrada inválida. Digite um número inteiro: "); }
        }
    }

    static double readDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = sc.nextLine().trim();
            try { 
                // Permite vírgula ou ponto como separador decimal
                return Double.parseDouble(s.replace(",", ".")); 
            }
            catch (Exception e) { System.out.print("Entrada inválida. Digite um valor numérico (ex: 1200.50): "); }
        }
    }
}
