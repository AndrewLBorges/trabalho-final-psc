public class Aluno extends Usuario {
    private long matricula;

    public Aluno(String nome_completo, long cpf, String endereco, String email, String celular, long matricula){
        this.setNome_completo(nome_completo);
        this.setEndereco(endereco);
        this.setEndereco(email);
        this.setCelular(celular);
        this.matricula = matricula;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }
}
