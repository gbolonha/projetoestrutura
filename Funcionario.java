class Funcionario {
    String nomeCompleto;
    int id;
    String dataNascimento;
    String dataContratacao;
    String departamento;
    String cargo;
    double salario;

    Funcionario(String nomeCompleto, int id, String dataNascimento, String dataContratacao, String departamento, String cargo, double salario) {
        this.nomeCompleto = nomeCompleto;
        this.id = id;
        this.dataNascimento = dataNascimento;
        this.dataContratacao = dataContratacao;
        this.departamento = departamento;
        this.cargo = cargo;
        this.salario = salario;
    }

    // Usado para Titanium e QueenBee (mostra o ID)
    public String toString() {
        return String.format("Nome: %s | ID: %d | Nasc.: %s | Contr.: %s | Dept.: %s | Cargo: %s | Salário: %.2f",
                nomeCompleto, id, dataNascimento, dataContratacao, departamento, cargo, salario);
    }
    
    // Usado para Axion (OMITE o ID)
    public String toAxionString() {
        return String.format("Nome: %s | Nasc.: %s | Contr.: %s | Dept.: %s | Cargo: %s | Salário: %.2f",
                nomeCompleto, dataNascimento, dataContratacao, departamento, cargo, salario);
    }
}
