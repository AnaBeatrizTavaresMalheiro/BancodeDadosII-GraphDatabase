package tabelas;

public class Disciplina {
    private String titulo;
    private int idDisciplina;

    public Disciplina(int idDisciplina, String titulo) {
        this.idDisciplina = idDisciplina;
        this.titulo = titulo;
    }

    // Getter
    public String getTitulo() {
        return titulo;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "titulo='" + titulo + '\'' +
                '}';
    }
}

