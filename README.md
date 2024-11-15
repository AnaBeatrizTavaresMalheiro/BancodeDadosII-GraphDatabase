# Projeto de Migração de Dados: PostgreSQL para Neo4j (Graph Database)

Este projeto tem como objetivo migrar dados de um banco de dados relacional PostgreSQL para um banco de dados de grafos Neo4j, utilizando a linguagem Java. Além disso, o projeto implementa funcionalidades de busca e geração de relatórios complexos a partir dos dados armazenados no Neo4j, aproveitando a modelagem de grafos para fornecer consultas mais eficientes e contextuais.



## Estrutura do Projeto

- **Java**: Linguagem de programação utilizada.
- **Gradle**: Ferramenta de gerenciamento de build.
- **PostgreSQL**: Banco de dados relacional de origem.
- **Neo4j**: Banco de dados de grafos de destino.
- **Railway**: Servidor onde o banco de dados PostgreSQL está hospedado.

## Funcionalidades

- Conectar ao banco de dados PostgreSQL e extrair dados das tabelas.
- Modelar os dados extraídos em um formato orientado a grafos no Neo4j.
- Criar nós e relações no Neo4j para representar entidades e interações acadêmicas.
- Permitir consultas utilizando a linguagem **Cypher**.

## Tecnologias Utilizadas

- **Neo4j Java Driver**: Para conexão e manipulação do banco de dados Neo4j.
- **PostgreSQL JDBC Driver**: Para acessar e consultar o banco de dados PostgreSQL.

## Sobre o Banco de Dados

Este projeto modela um banco de dados acadêmico que gerencia informações sobre alunos, professores, cursos, departamentos, disciplinas e a matriz curricular de uma instituição. Com a migração para Neo4j, o modelo de dados foi adaptado para capturar de maneira mais intuitiva as conexões e interações entre essas entidades.

## Estrutura de Grafos

### Nós

- **Aluno**: Representa os alunos, armazenando informações como nome, sobrenome, data de adesão e CPF.
- **Professor**: Representa os professores, com atributos como nome, sobrenome, formação e CPF.
- **Curso**: Define os cursos oferecidos pela instituição.
- **Departamento**: Representa os departamentos acadêmicos.
- **Disciplina**: Detalha as disciplinas oferecidas.
- **Grupo**: Representa grupos de alunos para projetos, como TCC.

### Relações

- **CHEFIADO_POR**: Relaciona um departamento ao professor chefe.
- **PERTENCE_A**: Relaciona cursos ao departamento responsável.
- **RESPONSAVEL_POR**: Relaciona professores responsáveis por um curso.
- **INSCRITO_EM**: Relaciona alunos aos cursos em que estão matriculados.
- **TEM_MATRIZ**: Relaciona cursos às disciplinas de sua matriz curricular, com propriedades como nota de corte.
- **CURSOU**: Relaciona alunos às disciplinas cursadas, incluindo notas e semestres.
- **TEM_ALUNO**: Relaciona grupos aos seus integrantes.
- **ORIENTADO_POR**: Relaciona grupos aos professores orientadores de TCC, incluindo semestre, ano e nota final.
- **EXPERIENCIA**: Relaciona professores a disciplinas em que possuem experiência, com datas de início e fim.

## Relatórios Possíveis

1. **Histórico Escolar de Alunos**: Lista disciplinas cursadas, semestre, ano e nota final.
2. **Disciplinas Ministradas por Professores**: Mostra disciplinas lecionadas, com informações de período.
3. **Alunos Formados em um Semestre Específico**: Identifica alunos aprovados em todas as disciplinas de sua matriz curricular em um semestre/ano.
4. **Professores Chefes de Departamento**: Lista professores e os departamentos liderados.
5. **Grupos de TCC e Orientadores**: Identifica grupos de TCC, integrantes e orientadores.
6. **Pré-requisitos de Disciplinas** *(opcional)*: Explora dependências entre disciplinas.

## Instruções de Uso

1. Baixe o projeto "ProjetoBancodeDadosMongoDB"
2. Acesse a pasta do projeto e rode o build com Gradle.
3. Acesse a pasta src/main/java/mongodb/org/example/
4. Acesse o arquivo 'Main.java'
5. Acrescente as senhas fornecida via Moodle.
   ![image](https://github.com/user-attachments/assets/913d86b3-6b66-42f8-8aad-71bd78338c89)
7. Execute o arquivo `Main.java` para realizar a migração de dados,criar as relações no Neo4j e verificar consultas.
8. Utilize o painel do Neo4j ou consultar terminal.

## Autores

| <img src="https://avatars.githubusercontent.com/u/84588132?v=4" alt="Ana Beatriz Tavares" width="150"/> | <img src="https://avatars.githubusercontent.com/u/103201200?v=4" alt="Bruno Andwele" width="150"/> |
|-------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| **Ana Beatriz Tavares**                                                                              | **Bruno Andwele**                                                                                   |
| Matrícula: *24.122.019-3*                                                                            | Matrícula: *24.122.030-0*                                                                           |

---
