public class Professor extends Usuario{
    private long codigo_funcionario;

    public Professor(String nome_completo, long cpf, String endereco, String email, String celular, long codigo_funcionario){
        this.setNome_completo(nome_completo);
        this.setEndereco(endereco);
        this.setEndereco(email);
        this.setCelular(celular);
        this.codigo_funcionario = codigo_funcionario;
    }

    public long getCodigo_funcionario() {
        return codigo_funcionario;
    }

    public void setCodigo_funcionario(long codigo_funcionario) {
        this.codigo_funcionario = codigo_funcionario;
    }
}
