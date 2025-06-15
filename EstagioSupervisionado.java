public class EstagioSupervisionado implements RequisitoIntegralizacao {
    private boolean concluido = false;

    @Override
    public String getNome() {
        return "Estágio Curricular Supervisionado";
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void concluir() {
        concluido = true;
    }
}
