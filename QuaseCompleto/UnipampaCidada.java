import java.util.Scanner;

public class UnipampaCidada implements RequisitoIntegralizacao {
    private boolean concluido = false;
    public String descricaoAtividade = "";

    @Override
    public String getNome() {
        return "UNIPAMPA Cidadã (60h de serviços comunitários)";
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void concluir() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Descrição da atividade realizada: ");
        descricaoAtividade = scanner.nextLine();
        concluido = true;
    }

    // ✅ método novo para uso interno ao carregar da persistência
    public void concluirComDescricao(String descricao) {
        this.descricaoAtividade = descricao;
        this.concluido = true;
    }

    @Override
    public String toString() {
        return getNome() + (concluido ? " ✅ - " + descricaoAtividade : " ❌");
    }
}
