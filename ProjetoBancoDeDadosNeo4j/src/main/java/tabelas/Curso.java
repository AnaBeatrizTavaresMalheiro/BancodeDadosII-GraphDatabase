package tabelas;

public class Curso {
    private int idCurso;
    private int idProfessor;
    private int idDepartamento;
    private String titulo;

    public Curso(int idCurso, int idProfessor, int idDepartamento, String titulo) {
        this.idCurso = idCurso;
        this.idProfessor = idProfessor;
        this.idDepartamento = idDepartamento;
        this.titulo = titulo;
    }

    // Getters
    public int getIdProfessor() {
        return idProfessor;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idProfessor=" + idProfessor +
                ", idDepartamento=" + idDepartamento +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}

