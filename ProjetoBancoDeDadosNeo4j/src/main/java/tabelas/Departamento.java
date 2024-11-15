package tabelas;

public class Departamento {
    private int idDepartamento;
    private String titulo;
    private double verba;
    private int idProfessor;

    public Departamento(int idDepartamento, String titulo, double verba, int idProfessor) {
        this.idDepartamento = idDepartamento;
        this.titulo = titulo;
        this.verba = verba;
        this.idProfessor = idProfessor;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public double getVerba() {
        return verba;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "titulo='" + titulo + '\'' +
                ", verba=" + verba +
                ", idProfessor=" + idProfessor +
                '}';
    }

}

