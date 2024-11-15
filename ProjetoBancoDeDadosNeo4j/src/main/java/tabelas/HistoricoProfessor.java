package tabelas;

public class HistoricoProfessor {
    private int idHistoricoProfessor;
    private int idProfessor;
    private int idDisciplina;
    private String semestre;
    private int ano;

    public HistoricoProfessor(int idHistoricoProfessor, int idProfessor, int idDisciplina, String semestre, int ano) {
        this.idHistoricoProfessor = idHistoricoProfessor;
        this.idProfessor = idProfessor;
        this.idDisciplina = idDisciplina;
        this.semestre = semestre;
        this.ano = ano;
    }

    // Getters
    public int getIdProfessor() {
        return idProfessor;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public String getSemestre() {
        return semestre;
    }

    public int getAno() {
        return ano;
    }

    public int getIdHistoricoProfessor() {
        return idHistoricoProfessor;
    }

    @Override
    public String toString() {
        return "HistoricoProfessor{" +
                "idProfessor=" + idProfessor +
                ", idDisciplina=" + idDisciplina +
                ", semestre='" + semestre + '\'' +
                ", ano=" + ano +
                '}';
    }
}

