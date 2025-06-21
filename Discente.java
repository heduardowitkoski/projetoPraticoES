public class Discente {
    private String nome;
    private String matricula;
    private String senha;

    public Discente(String nome, String matricula, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return nome + " | Matrícula: " + matricula;
    }

}
