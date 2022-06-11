public class Curso {
    private long codigo;
    private String nome;
    private int carga_horaria;
    private String descricao;

    public Curso(long codigo, String nome, int carga_horaria, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.carga_horaria = carga_horaria;
        this.descricao = descricao;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
