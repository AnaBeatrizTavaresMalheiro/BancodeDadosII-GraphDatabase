package tabelas;

public class HistoricoAluno {
    private int idHistoricoAluno;
    private int idAluno;
    private int idDisciplina;
    private double media;
    private String semestre;
    private int ano;

    public HistoricoAluno(int idHistoricoAluno, int idAluno, int idDisciplina, double media, String semestre, int ano) {
        this.idHistoricoAluno = idHistoricoAluno;
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
        this.media = media;
        this.semestre = semestre;
        this.ano = ano;
    }

    // Getters
    public int getIdAluno() {
        return idAluno;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public double getMedia() {
        return media;
    }

    public String getSemestre() {
        return semestre;
    }

    public int getAno() {
        return ano;
    }

    public int getIdHistoricoAluno() {
        return idHistoricoAluno;
    }

    @Override
    public String toString() {
        return "HistoricoAluno{" +
                "idAluno=" + idAluno +
                ", idDisciplina=" + idDisciplina +
                ", media=" + media +
                ", semestre='" + semestre + '\'' +
                ", ano=" + ano +
                '}';
    }
}

