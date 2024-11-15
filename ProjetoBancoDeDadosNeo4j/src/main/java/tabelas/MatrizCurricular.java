package tabelas;

public class MatrizCurricular {
    private int idMatrizCurricular;
    private int idCurso;
    private int idDisciplina;
    private double notaDeCorte;

    public MatrizCurricular(int idMatrizCurricular,int idCurso, int idDisciplina, double notaDeCorte) {
        this.idMatrizCurricular = idMatrizCurricular;
        this.idCurso = idCurso;
        this.idDisciplina = idDisciplina;
        this.notaDeCorte = notaDeCorte;
    }

    // Getters
    public int getIdCurso() {
        return idCurso;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public double getNotaDeCorte() {
        return notaDeCorte;
    }

    public int getIdMatrizCurricular() {
        return idMatrizCurricular;
    }

    @Override
    public String toString() {
        return "MatrizCurricular{" +
                "idCurso=" + idCurso +
                ", idDisciplina=" + idDisciplina +
                ", notaDeCorte=" + notaDeCorte +
                '}';
    }
}

