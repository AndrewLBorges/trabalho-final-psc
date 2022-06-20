import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;
import entidades.*;
import excecoes.AlunoException;
import interfaces.Validador;
import utils.OpcaoMenu;
import utils.UtilEntrada;
import validadores.ValidadorEmail;

import java.util.ArrayList;
import java.util.List;

public class SistemaCursos {
    private final ConectorBanco conector = new ConectorBanco();
    private final Validador<String> validador_email;
    private List<Curso> cursos;
    private final GerenciadorEntidades<Aluno> gerenciadorAlunos;
    private final GerenciadorEntidades<Professor> gerenciadorProfessores;
    private final GerenciadorEntidades<Sala> gerenciadorSalas;

    public SistemaCursos(GerenciadorEntidades<Aluno> gerenciadorAlunos, GerenciadorEntidades<Professor> gerenciadorProfessores, GerenciadorEntidades<Sala> gerenciadorSalas){
        this.validador_email = new ValidadorEmail();
        this.cursos = new ArrayList<>();
        this.gerenciadorAlunos = gerenciadorAlunos;
        this.gerenciadorProfessores = gerenciadorProfessores;
        this.gerenciadorSalas = gerenciadorSalas;
    }

    public void iniciarSistema(){
        OpcaoMenu opcao = OpcaoMenu.SEM_OPCAO;

        mostrarBoasVindas();

        while(opcao != OpcaoMenu.SAIR){
            mostrarMenu();
            opcao = obterResultadoMenu();
            realizarAcaoMenu(opcao);
        }
        mostrarMensagemSaida();
    }

    private void mostrarBoasVindas(){
        System.out.println("----------------- Bem-vindo(a) ao sistema de cadastro de Cursos -----------------");
    }

    private void mostrarMenu(){
        System.out.println("\nEscolha uma das opções abaixo para continuar: \n");
        System.out.println("Cadastrar curso (1)");
        System.out.println("Cadastrar aluno (2)");
        System.out.println("Matricular aluno em um curso (3)");
        System.out.println("Cadastrar uma sala (4)");
        System.out.println("Cadastrar um professor (5)");
        System.out.println("Alocar um profesor a um curso (6)");
        System.out.println("Alocar uma sala a um curso (7)");
        System.out.println("Listar com detalhes todos os cursos cadastrados (8)");
        System.out.println("Sair (0)");
    }

    private void mostrarMensagemSaida(){
        System.out.println("\n\nObrigado por usar nosso sistema!!");
    }

    private OpcaoMenu obterResultadoMenu(){
        System.out.print("Digite sua opção: ");
        int opcao = -1;

        while (opcao <0 || opcao > 8){
            opcao = (int)UtilEntrada.entradaInteira();

            if(opcao < 0 || opcao > 8)
                System.out.print("\nEntrada Inválida. Tente novamente: ");
        }


        return OpcaoMenu.valueOf(opcao);
    }

    private void realizarAcaoMenu(OpcaoMenu opcao){
        switch (opcao){
            case CADASTRO_CURSO:
                cadastrarCurso();
                break;
            case CADASTRO_ALUNO:
                cadastrarAluno();
                break;
            case MATRICULA_ALUNO:
                if(!gerenciadorAlunos.retornarEntidades().isEmpty()) {
                    matricularAluno();
                    break;
                }
                System.out.println("\nNao existem alunos cadastrados para serem matriculados!");
                break;
            case CADASTRO_SALA:
                cadastrarSala();
                break;
            case CADASTRO_PROFESSOR:
                cadastrarProfessor();
                break;
            case ALOCA_PROFESSOR:
                if(!gerenciadorProfessores.retornarEntidades().isEmpty()) {
                    alocarProfessor();
                    break;
                }
                System.out.println("\nNao existem professores cadastrados para serem alocados a um curso!");
                break;
            case ALOCA_SALA:
                if(!gerenciadorSalas.retornarEntidades().isEmpty()) {
                    alocarSala();
                    break;
                }
                System.out.println("\nNao existem salas cadastradas para serem alocadas a um curso!");
                break;
            case LISTAR_CURSOS:
                if(!cursos.isEmpty()) {
                    listarDetalhesCursos();
                    break;
                }
                System.out.println("\nNenhum curso foi cadastrado!");
                break;
            default:
                break;
        }
    }

    private void cadastrarCurso(){
        Curso curso;
        long codigo;
        String nome, descricao;
        int carga_horaria;

        System.out.print("Digite o codigo do curso: ");
        codigo = UtilEntrada.entradaInteira();

        if(cursoJaCadastrado(codigo)) {
            System.out.println("Ja foi cadastrado um curso com esse codigo!");
            return;
        }

        System.out.print("\nDigite o nome do curso: ");
        nome = UtilEntrada.entradaDeTexto();

        System.out.print("\nDigite a carga horaria do curso: ");
        carga_horaria = (int)UtilEntrada.entradaInteira();

        System.out.print("\nDigite a descricao do curso: ");
        descricao = UtilEntrada.entradaDeTexto();

        curso = new Curso(codigo, nome, carga_horaria, descricao);

        cursos.add(curso);
        conector.inserirCurso(curso);
    }

    private boolean cursoJaCadastrado(long codigoCurso){
        return cursos.stream().anyMatch(curso -> curso.getCodigo() == codigoCurso);
    }

    private void cadastrarAluno(){
        Aluno aluno;
        String nome, endereco, email, celular, cpf;
        long matricula;

        System.out.print("Nome do aluno: ");
        nome = UtilEntrada.entradaDeTexto();

        System.out.print("\nCpf do aluno: ");
        cpf = UtilEntrada.entradaDeTexto();

        System.out.print("\nEndereco do aluno: ");
        endereco = UtilEntrada.entradaDeTexto();

        System.out.print("\nEmail do aluno: ");
        email = validador_email.validar(UtilEntrada.entradaDeTexto());

        System.out.print("\nCelular do aluno: ");
        celular = UtilEntrada.entradaDeTexto();

        System.out.print("\nMatricula do aluno: ");
        matricula = UtilEntrada.entradaInteira();

        if(existeAlunoPorMatricula(gerenciadorAlunos.retornarEntidades(), matricula)){
            System.out.println("Ja existe um aluno com esse numero de matricula cadastrado!");
            return;
        }

        aluno = new Aluno(nome, cpf, endereco, email, celular, matricula);
        gerenciadorAlunos.cadastrar(aluno);
    }

    private void matricularAluno(){
        List<Aluno> alunos = gerenciadorAlunos.retornarEntidades();
        Aluno aluno;
        Curso curso;

        System.out.println("Qual aluno voce deseja matricular? ");
        mostrarListaAlunos(alunos);
        aluno = buscarAluno(alunos);

        if(aluno == null)
            return;

        System.out.println("Em qual curso voce deseja cadastrar o aluno?");
        mostrarListaCursos();
        curso = buscarCurso();

        if(curso == null)
            return;

        if(curso.temProfessorCadastrado() && curso.temSalaCadastrada()) {
            try{
                curso.matricularAluno(aluno);
                aluno.registrarCursoMatriculado(curso);
                conector.matricularAlunoAoCurso((int)curso.getCodigo(), (int)aluno.getMatricula());
            }
            catch (AlunoException exception){
                System.out.println(exception.getMessage());
            }
            return;
        }
        System.out.println("Curso nao possui professor ou sala cadastrados. Complete o cadastro do curso para matricular um aluno.");
    }

    private void mostrarListaAlunos(List<Aluno> alunos){
        if(alunos.isEmpty())
            System.out.println("Nao existem alunos cadastrados!");

        for(Aluno aluno : alunos){
            System.out.println(aluno.getNome_completo() + ", Matricula: " + aluno.getMatricula());
        }
    }

    private Aluno buscarAluno(List<Aluno> alunos){
        long matricula;

        System.out.print("Digite a matricula do aluno que deseja matricular: ");
        matricula = UtilEntrada.entradaInteira();

        if(existeAlunoPorMatricula(alunos, matricula)){
            return alunos.stream().filter(aluno -> aluno.getMatricula() == matricula).findFirst().get();
        }

        System.out.println("Matricula nao existe ou esta incorreta!");
        return null;
    }

    private void mostrarListaCursos(){
        if(cursos.isEmpty())
            System.out.println("Nao existem cursos cadastrados!");

        for(Curso curso: cursos){
            System.out.println(curso.getNome() + ", codigo: " + curso.getCodigo());
        }
    }

    private Curso buscarCurso(){
        long codigo;

        System.out.println("Digite o codigo do curso no qual deseja cadastrar: ");
        codigo = UtilEntrada.entradaInteira();

        if(existeCursoPorCodigo(codigo)){
            return cursos.stream().filter(curso -> curso.getCodigo() == codigo).findFirst().get();
        }

        System.out.println("Codigo de curso nao existe ou esta incorreto!");
        return null;
    }

    private boolean existeCursoPorCodigo(long codigo){
        return cursos.stream().anyMatch(curso -> curso.getCodigo() == codigo);
    }

    private boolean existeAlunoPorMatricula(List<Aluno> alunos, long matricula){
        return alunos.stream().anyMatch(aluno -> aluno.getMatricula() == matricula);
    }

    private void cadastrarSala(){
        Sala sala;
        String nome, local;
        int capacidade;

        System.out.print("Nome da sala: ");
        nome = UtilEntrada.entradaDeTexto();

        if(existeSalaPorNome(gerenciadorSalas.retornarEntidades(), nome)){
            System.out.println("Ja existe uma sala com esse nome!");
            return;
        }

        System.out.print("\nLocal da sala: ");
        local = UtilEntrada.entradaDeTexto();

        System.out.print("Capacidade da sala: ");
        capacidade = (int)UtilEntrada.entradaInteira();

        sala = new Sala(nome, local, capacidade);
        conector.inserirSala(sala);
    }

    private void cadastrarProfessor(){
        Professor professor;
        String nome, endereco, email, celular, cpf;
        long codigo_funcionario;

        System.out.print("Nome do professor: ");
        nome = UtilEntrada.entradaDeTexto();

        System.out.print("\nCpf do professor: ");
        cpf = UtilEntrada.entradaDeTexto();

        System.out.print("\nEndereco do professor: ");
        endereco = UtilEntrada.entradaDeTexto();

        System.out.print("\nEmail do professor: ");
        email = validador_email.validar(UtilEntrada.entradaDeTexto());

        System.out.print("\nCelular do professor: ");
        celular = UtilEntrada.entradaDeTexto();

        System.out.print("\nCodigo de funcionario do professor: ");
        codigo_funcionario = UtilEntrada.entradaInteira();

        if(existeProfessorPorCodigo(gerenciadorProfessores.retornarEntidades(), codigo_funcionario)){
            System.out.println("Ja existe professor com esse codigo de funcionario!");
            return;
        }

        professor = new Professor(nome, cpf, endereco, email, celular, codigo_funcionario);
        conector.inserirProfessor(professor);
    }

    private void alocarProfessor(){
        List<Professor> professores = gerenciadorProfessores.retornarEntidades();
        Professor professor;
        Curso curso;

        System.out.println("\nQual professor voce deseja matricular? ");
        mostrarListaProfessores(professores);
        professor = buscarProfessor(professores);

        if(professor == null)
            return;

        System.out.println("Em qual curso voce deseja cadastrar o professor?");
        mostrarListaCursos();
        curso = buscarCurso();

        if(curso == null)
            return;
        else {
            if(!curso.cadastrarProfessor(professor))
                System.out.println("Professor ja alocado!");
            else{
                conector.alocarProfessorAoCurso((int)curso.getCodigo(), (int)professor.getCodigo_funcionario());
            }
        }
    }

    private void mostrarListaProfessores(List<Professor> professores){
        if(professores.isEmpty())
            System.out.println("Nao existem professores cadastrados!");

        for(Professor professor : professores){
            System.out.println(professor.getNome_completo() + ", codigo de funcionario: " + professor.getCodigo_funcionario());
        }
    }

    private Professor buscarProfessor(List<Professor> professores){
        long codigo;

        System.out.println("\nDigite o codigo do professor que deseja alocar: ");
        codigo = UtilEntrada.entradaInteira();

        if(existeProfessorPorCodigo(professores, codigo)){
            return professores.stream().filter(professor -> professor.getCodigo_funcionario() == codigo).findFirst().get();
        }

        System.out.println("Codigo de professor nao existe ou esta incorreto!");
        return null;
    }

    private boolean existeProfessorPorCodigo(List<Professor> professores, long codigo){
        return professores.stream().anyMatch(professor -> professor.getCodigo_funcionario() == codigo);
    }

    private void alocarSala(){
        List<Sala> salas = gerenciadorSalas.retornarEntidades();
        Sala sala;
        Curso curso;

        System.out.println("Qual sala voce deseja alocar? ");
        mostrarListaSalas(salas);
        sala = buscarSala(salas);

        if(sala == null)
            return;

        System.out.println("Em qual curso voce deseja alocar a sala?");
        mostrarListaCursos();
        curso = buscarCurso();

        if(curso == null)
            return;
        else {
            if(!curso.cadastrarSala(sala))
                System.out.println("Sala ja alocada!");
            else{
                conector.alocarSalaAoCurso((int)curso.getCodigo(), sala.getNome());
            }
        }
    }

    private void mostrarListaSalas(List<Sala> salas){
        if(salas.isEmpty())
            System.out.println("Nao existem salas cadastradas!");

        for(Sala sala : salas){
            System.out.println(sala.getNome() + ", local: " + sala.getLocal());
        }
    }

    private Sala buscarSala(List<Sala> salas){
        String nome;

        System.out.println("Digite o nome da sala que deseja alocar(exatamente como esta cadastrado): ");
        nome = UtilEntrada.entradaDeTexto();

        if(existeSalaPorNome(salas, nome)){
            return salas.stream().filter(sala -> sala.getNome().equals(nome)).findFirst().get();
        }

        System.out.println("Nome de sala nao existe ou esta incorreto!");
        return null;
    }

    private boolean existeSalaPorNome(List<Sala> salas, String nome){
        return salas.stream().anyMatch(sala -> sala.getNome().equals(nome));
    }

    private void listarDetalhesCursos(){
        for(Curso curso : cursos){
            if(curso.temProfessorCadastrado() && curso.temSalaCadastrada() && curso.getAlunos_matriculados().size() > 0)
            {
                System.out.println("Curso de " + curso.getNome() + " ministrado pelo professor " + curso.getProfessor().getNome_completo() + " e cursado pelos seguintes alunos: ");

                for(Aluno aluno : curso.getAlunos_matriculados()){
                    System.out.println("- " + aluno.getNome_completo());
                }
            }
            System.out.println("O cadastro do curso " + curso.getNome() + " nao foi concluido. Conclua inserindo um professor e uma sala a ele para ver os detalhes.");
        }
    }
}
