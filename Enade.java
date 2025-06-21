public class Enade implements RequisitoIntegralizacao {
    private boolean concluido = false;

    @Override
    public String getNome() {
        return "Situação regular no ENADE";
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void concluir() {
        concluido = true;
    }

    @Override
    public String toString() {
        return getNome() + (concluido ? " ✅" : " ❌");
    }
}
