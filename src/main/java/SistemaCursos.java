import interfaces.GerenciadorEntidades;
import entidades.*;
import excecoes.AlunoException;
import interfaces.Validador;
import utils.OpcaoMenu;
import utils.UtilEntrada;
import utilsPersistencia.GerenciadorRelacionamento;
import validadores.ValidadorEmail;

import java.util.List;

public class SistemaCursos {
    private final Validador<String> validador_email;
    private final GerenciadorEntidades<Curso> gerenciadorCursos;
    private final GerenciadorEntidades<Aluno> gerenciadorAlunos;
    private final GerenciadorEntidades<Professor> gerenciadorProfessores;
    private final GerenciadorEntidades<Sala> gerenciadorSalas;
    private final GerenciadorRelacionamento gerenciadorRelacionamento;

    public SistemaCursos(GerenciadorEntidades<Aluno> gerenciadorAlunos, GerenciadorEntidades<Professor> gerenciadorProfessores, GerenciadorEntidades<Sala> gerenciadorSalas, GerenciadorEntidades<Curso> gerenciadorCursos){
        this.validador_email = new ValidadorEmail();
        this.gerenciadorCursos = gerenciadorCursos;
        this.gerenciadorAlunos = gerenciadorAlunos;
        this.gerenciadorProfessores = gerenciadorProfessores;
        this.gerenciadorSalas = gerenciadorSalas;
        this.gerenciadorRelacionamento = new GerenciadorRelacionamento();
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
                var cursos = gerenciadorCursos.retornarEntidades();
                if(!cursos.isEmpty()) {
                    listarDetalhesCursos(cursos);
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

        gerenciadorCursos.cadastrar(curso);
    }

    private boolean cursoJaCadastrado(long codigoCurso){
        return gerenciadorCursos.retornarEntidades()
                .stream().anyMatch(curso -> curso.getCodigo() == codigoCurso);
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
        List<Curso> cursos = gerenciadorCursos.retornarEntidades();
        Aluno aluno;
        Curso curso;

        if(alunos.isEmpty() || cursos.isEmpty()) {
            System.out.println("Cadastre ao menos um aluno e um curso para realizar a matricula!");
            return;
        }

        System.out.println("Qual aluno voce deseja matricular? ");
        mostrarListaAlunos(alunos);
        aluno = buscarAluno(alunos);

        if(aluno == null)
            return;

        System.out.println("Em qual curso voce deseja cadastrar o aluno?");
        mostrarListaCursos(cursos);
        curso = buscarCurso(cursos);

        if(curso == null)
            return;

        if(curso.temProfessorCadastrado() && curso.temSalaCadastrada()) {
            try{
                curso.matricularAluno(aluno);
                aluno.registrarCursoMatriculado(curso);
                gerenciadorRelacionamento.relacionarAlunoCurso((int)curso.getCodigo(), (int)aluno.getMatricula());
            }
            catch (AlunoException exception){
                System.out.println(exception.getMessage());
            }
            return;
        }
        System.out.println("Curso nao possui professor ou sala cadastrados. Complete o cadastro do curso para matricular um aluno.");
    }

    private void mostrarListaAlunos(List<Aluno> alunos){
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

    private void mostrarListaCursos(List<Curso> cursos){
        if(cursos.isEmpty())
            System.out.println("Nao existem cursos cadastrados!");

        for(Curso curso: cursos){
            System.out.println(curso.getNome() + ", codigo: " + curso.getCodigo());
        }
    }

    private Curso buscarCurso(List<Curso> cursos){
        long codigo;

        System.out.println("Digite o codigo do curso no qual deseja cadastrar: ");
        codigo = UtilEntrada.entradaInteira();

        if(existeCursoPorCodigo(cursos, codigo)){
            return cursos.stream().filter(curso -> curso.getCodigo() == codigo).findFirst().get();
        }

        System.out.println("Codigo de curso nao existe ou esta incorreto!");
        return null;
    }

    private boolean existeCursoPorCodigo(List<Curso> cursos, long codigo){
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
        gerenciadorSalas.cadastrar(sala);
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
        gerenciadorProfessores.cadastrar(professor);
    }

    private void alocarProfessor(){
        List<Professor> professores = gerenciadorProfessores.retornarEntidades();
        List<Curso> cursos = gerenciadorCursos.retornarEntidades();
        Professor professor;
        Curso curso;

        if(professores.isEmpty() || cursos.isEmpty()) {
            System.out.println("Cadastre ao menos um professor e um curso para realizar a alocacao!");
            return;
        }

        System.out.println("\nQual professor voce deseja matricular? ");
        mostrarListaProfessores(professores);
        professor = buscarProfessor(professores);

        if(professor == null)
            return;

        System.out.println("Em qual curso voce deseja cadastrar o professor?");
        mostrarListaCursos(cursos);
        curso = buscarCurso(cursos);

        if(curso == null)
            return;
        else {
            if(!curso.cadastrarProfessor(professor))
                System.out.println("Professor ja alocado!");
            else{
                gerenciadorRelacionamento.relacionarProfessorCurso((int)curso.getCodigo(), (int)professor.getCodigo_funcionario());
            }
        }
    }

    private void mostrarListaProfessores(List<Professor> professores){
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
        List<Curso> cursos = gerenciadorCursos.retornarEntidades();
        Sala sala;
        Curso curso;

        if(salas.isEmpty() || cursos.isEmpty()) {
            System.out.println("Cadastre ao menos uma sala e um curso para realizar a alocacao!");
            return;
        }

        System.out.println("Qual sala voce deseja alocar? ");
        mostrarListaSalas(salas);
        sala = buscarSala(salas);

        if(sala == null)
            return;

        System.out.println("Em qual curso voce deseja alocar a sala?");
        mostrarListaCursos(cursos);
        curso = buscarCurso(cursos);

        if(curso == null)
            return;
        else {
            if(!curso.cadastrarSala(sala))
                System.out.println("Sala ja alocada!");
            else{
                gerenciadorRelacionamento.relacionarSalaCurso((int) curso.getCodigo(), sala.getNome());
            }
        }
    }

    private void mostrarListaSalas(List<Sala> salas){
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

    private void listarDetalhesCursos(List<Curso> cursos){
        System.out.println("\nDetalhes dos cursos: \n");
        for(Curso curso : cursos){
            if(curso.temProfessorCadastrado() && curso.temSalaCadastrada() && curso.getAlunos_matriculados().size() > 0)
            {
                System.out.println("- Curso de " + curso.getNome() + " ministrado pelo professor " + curso.getProfessor().getNome_completo() + " e cursado pelos seguintes alunos: ");

                for(Aluno aluno : curso.getAlunos_matriculados()){
                    System.out.println("\t- " + aluno.getNome_completo());
                }
            }
            System.out.println("O cadastro do curso " + curso.getNome() + " nao foi concluido. Conclua alocando um professor, uma sala e alunos a ele para ver os detalhes.");
        }
    }
}
