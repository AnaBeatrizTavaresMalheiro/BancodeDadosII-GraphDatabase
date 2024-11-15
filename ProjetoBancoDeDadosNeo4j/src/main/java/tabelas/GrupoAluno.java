package tabelas;

public class GrupoAluno {
    private int idGrupoAluno;
    private int idAluno;
    private int idGrupo;

    public GrupoAluno(int idGrupoAluno, int idAluno, int idGrupo) {
        this.idGrupoAluno = idGrupoAluno;
        this.idAluno = idAluno;
        this.idGrupo = idGrupo;
    }

    // Getters
    public int getIdAluno() {
        return idAluno;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public int getIdGrupoAluno() {
        return idGrupoAluno;
    }

    @Override
    public String toString() {
        return "GrupoAluno{" +
                "idAluno=" + idAluno +
                ", idGrupo=" + idGrupo +
                '}';
    }
}
