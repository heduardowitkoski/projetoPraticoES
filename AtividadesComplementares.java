public class AtividadesComplementares implements RequisitoIntegralizacao {
    private boolean concluido = false;

    @Override
    public String getNome() {
        return "Atividades Complementares de Graduação";
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
