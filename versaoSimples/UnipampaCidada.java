public class UnipampaCidada implements RequisitoIntegralizacao {
    private boolean concluido = false;

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
        concluido = true;
    }

    @Override
    public String toString() {
        return getNome() + (concluido ? " ✅" : " ❌");
    }
}