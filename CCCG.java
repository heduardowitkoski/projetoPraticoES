public class CCCG implements RequisitoIntegralizacao {
    private boolean concluido = false;

    @Override
    public String getNome() {
        return "Componentes Curriculares Complementares (CCCG)";
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
