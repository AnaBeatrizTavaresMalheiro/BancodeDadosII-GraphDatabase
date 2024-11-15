package org.example;

import neo4j.Neo4jService;
import org.neo4j.driver.Record;
import tabelas.*;
import org.neo4j.driver.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String URL = "jdbc:postgresql://autorack.proxy.rlwy.net:57661/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "";
    private static final String URI_NEO4J = "neo4j+s://73736f2a.databases.neo4j.io:7687";
    private static final String PASSWORD_NEO4J = "";
    private static final String USER_NEO4J = "neo4j";

    public static void main(String[] args) {
        // Conexão com o PostgreSQL
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Neo4jService neo4jService = new Neo4jService(
                     URI_NEO4J, USER_NEO4J, PASSWORD_NEO4J,
                     getProfessores(connection),
                     getDepartamentos(connection),
                     getCursos(connection),
                     getAlunos(connection),
                     getDisciplinas(connection),
                     getMatrizCurricular(connection),
                     getHistoricoAlunos(connection),
                     getGrupoAlunos(connection),
                     getHistoricoTCC(connection),
                     getHistoricoProfessores(connection)
             )) {
            LOGGER.log(Level.INFO, "Conexões com PostgreSQL e Neo4j estabelecidas.");
            neo4jService.insertDataIntoDB();

            neo4jService.getHistoricoEscolar("Luiz");
            neo4jService.getDisciplinasMinistradasPorProfessor("Pedro");
            neo4jService.getAlunosFormados(2003);
            neo4jService.getProfessoresChefes();
            neo4jService.getGruposDeTCC();
            neo4jService.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao conectar ao PostgreSQL.", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro geral ao executar a aplicação.", e);
        }
    }

    // Métodos de consulta para cada tabela
    private static List<Professor> getProfessores(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.professor";
        List<Professor> professores = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                professores.add(new Professor(
                        rs.getInt("Id_Professor"),
                        rs.getString("Nome"),
                        rs.getString("Sobrenome"),
                        rs.getString("Formacao"),
                        rs.getString("CPF")
                ));
            }
        }
        return professores;
    }

    private static List<Departamento> getDepartamentos(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.departamento";
        List<Departamento> departamentos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                departamentos.add(new Departamento(
                        rs.getInt("Id_Departamento"),
                        rs.getString("Titulo"),
                        rs.getDouble("Verba"),
                        rs.getInt("Id_Professor")
                ));
            }
        }
        return departamentos;
    }

    private static List<Curso> getCursos(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.curso";
        List<Curso> cursos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cursos.add(new Curso(
                        rs.getInt("Id_Curso"),
                        rs.getInt("Id_Professor"),
                        rs.getInt("Id_Departamento"),
                        rs.getString("Titulo")
                ));
            }
        }
        return cursos;
    }

    private static List<Aluno> getAlunos(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.aluno";
        List<Aluno> alunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_Curso"),
                        rs.getString("Nome"),
                        rs.getString("Sobrenome"),
                        rs.getDate("Data_Adesao"),
                        rs.getString("CPF")
                ));
            }
        }
        return alunos;
    }

    private static List<Disciplina> getDisciplinas(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.disciplina";
        List<Disciplina> disciplinas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                disciplinas.add(new Disciplina(
                        rs.getInt("Id_Disciplina"),
                        rs.getString("Titulo")
                ));
            }
        }
        return disciplinas;
    }

    private static List<MatrizCurricular> getMatrizCurricular(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.matriz_curricular";
        List<MatrizCurricular> matrizCurricular = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                matrizCurricular.add(new MatrizCurricular(
                        rs.getInt("Id_Matriz_Curricular"),
                        rs.getInt("Id_curso"),
                        rs.getInt("Id_disciplina"),
                        rs.getDouble("nota_de_corte")
                ));
            }
        }
        return matrizCurricular;
    }

    private static List<HistoricoAluno> getHistoricoAlunos(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.historico_aluno";
        List<HistoricoAluno> historicoAlunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historicoAlunos.add(new HistoricoAluno(
                        rs.getInt("Id_Historico_Escolar"),
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_Disciplina"),
                        rs.getDouble("Media"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano")
                ));
            }
        }
        return historicoAlunos;
    }

    private static List<GrupoAluno> getGrupoAlunos(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.grupo_aluno";
        List<GrupoAluno> grupoAlunos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                grupoAlunos.add(new GrupoAluno(
                        rs.getInt("Id_Grupo_Aluno"),
                        rs.getInt("Id_Aluno"),
                        rs.getInt("Id_grupo")
                ));
            }
        }
        return grupoAlunos;
    }

    private static List<HistoricoTCC> getHistoricoTCC(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.historico_tcc";
        List<HistoricoTCC> historicoTCC = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historicoTCC.add(new HistoricoTCC(
                        rs.getInt("Id_Historico_TCC"),
                        rs.getInt("Id_Grupo_Aluno"),
                        rs.getInt("Id_Professor"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano"),
                        rs.getDouble("Nota")
                ));
            }
        }
        return historicoTCC;
    }

    private static List<HistoricoProfessor> getHistoricoProfessores(Connection connection) throws SQLException {
        String query = "SELECT * FROM faculdade.historico_professor";
        List<HistoricoProfessor> historicoProfessores = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historicoProfessores.add(new HistoricoProfessor(
                        rs.getInt("Id_Historico_Professor"),
                        rs.getInt("Id_Professor"),
                        rs.getInt("Id_Disciplina"),
                        rs.getString("Semestre"),
                        rs.getInt("Ano")
                ));
            }
        }
        return historicoProfessores;
    }
}
