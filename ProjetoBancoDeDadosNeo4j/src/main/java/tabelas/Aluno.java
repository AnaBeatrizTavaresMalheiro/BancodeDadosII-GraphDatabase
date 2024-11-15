package tabelas;

import java.util.Date;

public class Aluno {
    private int idAluno;
    private int idCurso;
    private String nome;
    private String sobrenome;
    private Date dataAdesao;
    private String cpf;

    public Aluno(int idAluno, int idCurso, String nome, String sobrenome, Date dataAdesao, String cpf) {
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataAdesao = dataAdesao;
        this.cpf = cpf;
    }

    // Getters
    public int getIdAluno() {
        return idAluno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public Date getDataAdesao() {
        return dataAdesao;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "idCurso=" + idCurso +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataAdesao=" + dataAdesao +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}

