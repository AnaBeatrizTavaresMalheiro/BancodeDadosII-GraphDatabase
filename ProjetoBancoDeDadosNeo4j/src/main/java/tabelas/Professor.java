package tabelas;

public class Professor {
    private int idProfessor;
    private String nome;
    private String sobrenome;
    private String formacao;
    private String cpf;

    public Professor(int idProfessor, String nome, String sobrenome, String formacao, String cpf) {
        this.idProfessor = idProfessor;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.formacao = formacao;
        this.cpf = cpf;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getFormacao() {
        return formacao;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", formacao='" + formacao + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}

