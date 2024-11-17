package neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import tabelas.*;

import java.util.*;

public class Neo4jService implements AutoCloseable {
    private final Driver driver;
    private final List<Professor> professores;
    private final List<Departamento> departamentos;
    private final List<Curso> cursos;
    private final List<Aluno> alunos;
    private final List<Disciplina> disciplinas;
    private final List<MatrizCurricular> matrizCurricular;
    private final List<HistoricoAluno> historicoAlunos;
    private final List<GrupoAluno> grupoAlunos;
    private final List<HistoricoTCC> historicoTCC;
    private final List<HistoricoProfessor> historicoProfessores;

    public Neo4jService(String uri, String user, String password,
                        List<Professor> professores, List<Departamento> departamentos,
                        List<Curso> cursos, List<Aluno> alunos, List<Disciplina> disciplinas,
                        List<MatrizCurricular> matrizCurricular, List<HistoricoAluno> historicoAlunos,
                        List<GrupoAluno> grupoAlunos, List<HistoricoTCC> historicoTCC,
                        List<HistoricoProfessor> historicoProfessores) {

        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
        this.professores = professores;
        this.departamentos = departamentos;
        this.cursos = cursos;
        this.alunos = alunos;
        this.disciplinas = disciplinas;
        this.matrizCurricular = matrizCurricular;
        this.historicoAlunos = historicoAlunos;
        this.grupoAlunos = grupoAlunos;
        this.historicoTCC = historicoTCC;
        this.historicoProfessores = historicoProfessores;
    }

    @Override
    public void close() {
        if (driver != null) {
            driver.close();
        }
    }

    // Método para verificar a conexão
    private boolean isConnected() {
        try (Session session = driver.session()) {
            session.run("RETURN 1");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return false;
        }
    }

    public void insertDataIntoDB() {
        if (!isConnected()) {
            System.out.println("Conexão com o banco de dados falhou. Verifique as credenciais e a URI.");
            return;
        }

        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            session.writeTransaction(tx -> {
                clearDatabase(tx);
                insertProfessores(tx);
                insertDepartamentos(tx);
                insertCursos(tx);
                insertAlunos(tx);
                insertDisciplinas(tx);
                insertMatrizCurricular(tx);
                insertHistoricoAlunos(tx);
                insertGrupoAlunos(tx);
                insertHistoricoTCC(tx);
                insertHistoricoProfessores(tx);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearDatabase(Transaction tx) {
        tx.run("MATCH (n) DETACH DELETE n");
    }

    private void insertProfessores(Transaction tx) {
        for (Professor professor : professores) {
            tx.run("CREATE (p:Professor {idProfessor: $id, nome: $nome, sobrenome: $sobrenome, formacao: $formacao, cpf: $cpf})",
                    Values.parameters("id", professor.getIdProfessor(), "nome", professor.getNome(),
                            "sobrenome", professor.getSobrenome(), "formacao", professor.getFormacao(), "cpf", professor.getCpf()));
        }
    }

    private void insertDepartamentos(Transaction tx) {
        for (Departamento departamento : departamentos) {
            tx.run("CREATE (d:Departamento {idDepartamento: $idDepartamento, titulo: $titulo, verba: $verba})",
                    Values.parameters("idDepartamento", departamento.getIdDepartamento(),
                            "titulo", departamento.getTitulo(), "verba", departamento.getVerba()));
            tx.run("MATCH (d:Departamento {idDepartamento: $idDepartamento}), (p:Professor {idProfessor: $idProfessor}) " +
                            "MERGE (d)-[:CHEFIADO_POR]->(p)",
                    Values.parameters("idDepartamento", departamento.getIdDepartamento(), "idProfessor", departamento.getIdProfessor()));
        }
    }

    private void insertCursos(Transaction tx) {
        for (Curso curso : cursos) {
            tx.run("CREATE (c:Curso {idCurso: $idCurso, titulo: $titulo})",
                    Values.parameters("idCurso", curso.getIdCurso(), "titulo", curso.getTitulo()));
            tx.run("MATCH (c:Curso {idCurso: $idCurso}), (d:Departamento {idDepartamento: $idDepartamento}), " +
                            "(p:Professor {idProfessor: $idProfessor}) " +
                            "MERGE (c)-[:PERTENCE_A]->(d) " +
                            "MERGE (p)-[:RESPONSAVEL_POR]->(c)",
                    Values.parameters("idCurso", curso.getIdCurso(), "idDepartamento", curso.getIdDepartamento(),
                            "idProfessor", curso.getIdProfessor()));
        }
    }

    private void insertAlunos(Transaction tx) {
        for (Aluno aluno : alunos) {
            String dataAdesao = aluno.getDataAdesao().toString();

            tx.run("CREATE (a:Aluno {idAluno: $idAluno, nome: $nome, sobrenome: $sobrenome, dataAdesao: $dataAdesao, cpf: $cpf})",
                    Values.parameters("idAluno", aluno.getIdAluno(),
                            "nome", aluno.getNome(), "sobrenome", aluno.getSobrenome(),
                            "dataAdesao", dataAdesao, "cpf", aluno.getCpf()));

            tx.run("MATCH (a:Aluno {idAluno: $idAluno}), (c:Curso {idCurso: $idCurso}) " +
                            "MERGE (a)-[:INSCRITO_EM]->(c)",
                    Values.parameters("idAluno", aluno.getIdAluno(), "idCurso", aluno.getIdCurso()));
        }
    }


    private void insertDisciplinas(Transaction tx) {
        for (Disciplina disciplina : disciplinas) {
            tx.run("CREATE (d:Disciplina {idDisciplina: $idDisciplina, titulo: $titulo})",
                    Values.parameters("idDisciplina", disciplina.getIdDisciplina(), "titulo", disciplina.getTitulo()));
        }
    }

    private void insertMatrizCurricular(Transaction tx) {
        for (MatrizCurricular mc : matrizCurricular) {
            tx.run("MATCH (c:Curso {idCurso: $idCurso}), (d:Disciplina {idDisciplina: $idDisciplina}) " +
                            "MERGE (c)-[:TEM_MATRIZ {notaDeCorte: $notaDeCorte}]->(d)",
                    Values.parameters("idCurso", mc.getIdCurso(), "idDisciplina", mc.getIdDisciplina(), "notaDeCorte", mc.getNotaDeCorte()));
        }
    }

    private void insertHistoricoAlunos(Transaction tx) {
        for (HistoricoAluno ha : historicoAlunos) {
            tx.run("MATCH (a:Aluno {idAluno: $idAluno}), (d:Disciplina {idDisciplina: $idDisciplina}) " +
                            "MERGE (a)-[:CURSOU {media: $media, semestre: $semestre, ano: $ano}]->(d)",
                    Values.parameters("idAluno", ha.getIdAluno(), "idDisciplina", ha.getIdDisciplina(),
                            "media", ha.getMedia(), "semestre", ha.getSemestre(), "ano", ha.getAno()));
        }
    }

    private void insertGrupoAlunos(Transaction tx) {
        for (GrupoAluno ga : grupoAlunos) {
            tx.run("MATCH (a:Aluno {idAluno: $idAluno}) " +
                            "CREATE (g:Grupo {idGrupo: $idGrupo})-[:TEM_ALUNO]->(a)",
                    Values.parameters("idAluno", ga.getIdAluno(), "idGrupo", ga.getIdGrupo()));
        }
    }

    private void insertHistoricoTCC(Transaction tx) {
        for (HistoricoTCC ht : historicoTCC) {
            tx.run("MATCH (g:Grupo {idGrupo: $idGrupo}), (p:Professor {idProfessor: $idProfessor}) " +
                            "MERGE (g)-[:ORIENTADO_POR {semestre: $semestre, ano: $ano, nota: $nota}]->(p)",
                    Values.parameters("idGrupo", ht.getIdGrupoAluno(), "idProfessor", ht.getIdProfessor(),
                            "semestre", ht.getSemestre(), "ano", ht.getAno(), "nota", ht.getNota()));
        }
    }

    private void insertHistoricoProfessores(Transaction tx) {
        for (HistoricoProfessor hp : historicoProfessores) {
            tx.run("MATCH (p:Professor {idProfessor: $idProfessor}), (d:Disciplina {idDisciplina: $idDisciplina}) " +
                            "MERGE (p)-[:MINISTROU {semestre: $semestre, ano: $ano}]->(d)",
                    Values.parameters(
                            "idProfessor", hp.getIdProfessor(),
                            "idDisciplina", hp.getIdDisciplina(),  // Corrigido: passando o valor correto de idDisciplina
                            "semestre", hp.getSemestre(),
                            "ano", hp.getAno()
                    ));
        }
    }

    // Consultas

    public void getHistoricoEscolar(String nome) {
        List<Record> lista_historico = new ArrayList<>(); // Inicializando a lista de Record

        try (Session session = driver.session()) {
            String query = "MATCH (a:Aluno {nome: $nome})-[r:CURSOU]->(d:Disciplina) " +
                    "RETURN d.titulo AS disciplina, r.semestre AS semestre, r.ano AS ano, r.media AS notaFinal";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nome", nome);

            // Executa a consulta e armazena os resultados na lista_historico
            lista_historico = session.run(query, parameters).list();

            if (lista_historico.isEmpty()) {
                System.out.println("\n\nNenhum resultado encontrado.");
            } else {
                System.out.println("\n\nHistórico de Matérias Realizadas pelo Aluno:");
                for (Record record : lista_historico) {
                    System.out.println("Disciplina: " + record.get("disciplina").asString());
                    System.out.println("Semestre: " + record.get("semestre").asString());
                    System.out.println("Ano: " + record.get("ano").asInt());
                    System.out.println("Nota Final: " + record.get("notaFinal").asFloat());
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para capturar e exibir qualquer exceção que possa ocorrer
        }
    }


    public void getDisciplinasMinistradasPorProfessor(String nomeProfessor) {
        // Consultando no banco de dados Neo4j
        try (Session session = driver.session()) {
            // Corrigindo o nome do parâmetro para garantir consistência
            String query = "MATCH (p:Professor {nome: $nomeProfessor})-[m:MINISTROU]->(d:Disciplina) " +
                    "RETURN p.nome AS professor, d.titulo AS disciplina, m.semestre AS semestre, m.ano AS ano LIMIT 25";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nomeProfessor", nomeProfessor);  // Corrigido para o nome do parâmetro correto

            // Executa a consulta e armazena os resultados na lista_disciplinas
            List<Record> lista_disciplinas = session.run(query, parameters).list();

            if (lista_disciplinas.isEmpty()) {
                System.out.println("\n\nNenhuma disciplina encontrada para o professor " + nomeProfessor);
            } else {
                System.out.println("\n\nDisciplinas Ministradas por " + nomeProfessor + ":");
                for (Record record : lista_disciplinas) {

                    // Exibe as informações de forma segura
                    System.out.println("Disciplina: " + record.get("disciplina"));
                    System.out.println("Semestre: " + record.get("semestre"));
                    System.out.println("Ano: " + record.get("ano"));
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Para capturar e exibir qualquer exceção que possa ocorrer
        }
    }


    public void getAlunosFormados(int ano) {
        try (Session session = driver.session()) {
            String query = "MATCH (a:Aluno)-[c:CURSOU]->(d:Disciplina) " +
                    "WHERE c.semestre = $semestre AND c.media >= 5 AND c.ano = $ano " +
                    "RETURN DISTINCT a.nome AS nomeAluno, a.sobrenome AS sobrenomeAluno";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("semestre", "Segundo");
            parameters.put("ano", ano);

            // Executa a consulta e armazena os resultados na lista_alunos
            List<Record> lista_alunos = session.run(query, parameters).list();

            if (lista_alunos.isEmpty()) {
                System.out.println("\n\nNenhum aluno formado no ano " + ano);
            } else {
                System.out.println("\n\nAlunos Formados em " + ano + ":");
                for (Record record : lista_alunos) {
                    System.out.println("Nome: " + record.get("nomeAluno").asString());
                    System.out.println("Sobrenome: " + record.get("sobrenomeAluno").asString());
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para capturar e exibir qualquer exceção que possa ocorrer
        }
    }

    public void getProfessoresChefes() {
        List<Record> lista_professores = new ArrayList<>(); // Inicializando a lista de Record

        try (Session session = driver.session()) {
            String query = "MATCH (d:Departamento)-[:CHEFIADO_POR]-> (p:Professor)" +
                    "RETURN p.nome AS nomeProfessor, d.titulo AS departamento";
            Map<String, Object> parameters = new HashMap<>(); // Nenhum parâmetro é necessário para esta consulta

            // Executa a consulta e armazena os resultados na lista_professores
            lista_professores = session.run(query, parameters).list();

            if (lista_professores.isEmpty()) {
                System.out.println("\n\nNenhum professor chefe encontrado.");
            } else {
                System.out.println("\n\nProfessores Chefes de Departamento:");
                for (Record record : lista_professores) {
                    System.out.println("Professor: " + record.get("nomeProfessor").asString());
                    System.out.println("Departamento: " + record.get("departamento").asString());
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para capturar e exibir qualquer exceção que possa ocorrer
        }
    }

    public void getGruposDeTCC() {
        List<Record> lista_grupos = new ArrayList<>(); // Inicializando a lista de Record

        try (Session session = driver.session()) {
            String query = "MATCH (g:Grupo)-[:ORIENTADO_POR]->(p:Professor) " +
                    "MATCH (g:Grupo)-[:TEM_ALUNO]->(a:Aluno) " +
                    "RETURN g.idGrupo AS idGrupo, p.nome AS orientador, g.titulo AS grupoTCC,"+
                    "COLLECT(a.nome) AS alunos";

            Map<String, Object> parameters = new HashMap<>(); // Nenhum parâmetro é necessário para esta consulta

            // Executa a consulta e armazena os resultados na lista_grupos
            lista_grupos = session.run(query, parameters).list();

            if (lista_grupos.isEmpty()) {
                System.out.println("\n\nNenhum grupo de TCC encontrado.");
            } else {
                System.out.println("\n\nGrupos de TCC:");
                for (Record record : lista_grupos) {
                    System.out.println("ID do Grupo: " + record.get("idGrupo"));
                    System.out.println("Orientador: " + record.get("orientador"));

                    // Usa um Set para garantir que os alunos não se repitam
                    List<String> alunos = new ArrayList<>(new HashSet<>(record.get("alunos").asList(Value::asString)));
                    System.out.println("Alunos: " + String.join(", ", alunos));

                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para capturar e exibir qualquer exceção que possa ocorrer
        }
    }

    public void showAllData() {
        // Iniciando uma sessão no banco
        try (Session session = driver.session()) {
            // Obtendo todos os labels dinamicamente
            String getLabelsQuery = "CALL db.labels()";
            Result labelsResult = session.run(getLabelsQuery);

            // Iterando sobre os labels retornados
            while (labelsResult.hasNext()) {
                System.out.println("--------------------------------");
                Record labelRecord = labelsResult.next();
                String label = labelRecord.get("label").asString();
                System.out.println("Label: " + label);

                try {
                    // Consultando todos os nós do label
                    String query = String.format("MATCH (n:%s) RETURN n", label);
                    Result nodesResult = session.run(query);

                    // Verificando e exibindo os dados de cada nó
                    if (nodesResult.hasNext()) {
                        while (nodesResult.hasNext()) {
                            Record nodeRecord = nodesResult.next();
                            Node node = nodeRecord.get("n").asNode();
                            for (String key : node.keys()) {
                                System.out.println(key + ": " + node.get(key).asObject());
                            }
                            System.out.println(); // Linha em branco para separar os nós
                        }
                    } else {
                        System.out.println("Nenhum dado encontrado para o label: " + label);
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao mostrar dados do label " + label + ": " + e.getMessage());
                }
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter os labels: " + e.getMessage());
        }
    }

}
