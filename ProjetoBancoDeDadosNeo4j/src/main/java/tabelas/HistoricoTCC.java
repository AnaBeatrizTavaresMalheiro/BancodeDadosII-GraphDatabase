package tabelas;

public class HistoricoTCC {
    private int idHistoricoTCC;
    private int idGrupoAluno;
    private int idProfessor;
    private String semestre;
    private int ano;
    private double nota;

    public HistoricoTCC(int idHistoricoTCC, int idGrupoAluno, int idProfessor, String semestre, int ano, double nota) {
        this.idHistoricoTCC = idHistoricoTCC;
        this.idGrupoAluno = idGrupoAluno;
        this.idProfessor = idProfessor;
        this.semestre = semestre;
        this.ano = ano;
        this.nota = nota;
    }

    // Getters
    public int getIdGrupoAluno() {
        return idGrupoAluno;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getSemestre() {
        return semestre;
    }

    public int getAno() {
        return ano;
    }

    public double getNota() {
        return nota;
    }

    public int getIdHistoricoTCC() {
        return idHistoricoTCC;
    }

    @Override
    public String toString() {
        return "HistoricoTCC{" +
                "idGrupoAluno=" + idGrupoAluno +
                ", idProfessor=" + idProfessor +
                ", semestre='" + semestre + '\'' +
                ", ano=" + ano +
                ", nota=" + nota +
                '}';
    }
}

