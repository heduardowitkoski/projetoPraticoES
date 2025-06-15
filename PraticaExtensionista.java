public class PraticaExtensionista implements RequisitoIntegralizacao {
    private boolean etapa1Concluida = false;
    private boolean etapa2Concluida = false;

    @Override
    public String getNome() {
        return "Prática Extensionista (I e II)";
    }

    @Override
    public boolean isConcluido() {
        return etapa1Concluida && etapa2Concluida;
    }

    public void concluirEtapa1() {
        etapa1Concluida = true;
    }

    public void concluirEtapa2() {
        etapa2Concluida = true;
    }

    @Override
    public void concluir() {
        etapa1Concluida = true;
        etapa2Concluida = true;
    }

    @Override
    public String toString() {
        return getNome() + " - Etapa I: " + (etapa1Concluida ? "Concluída" : "Pendente") +
                ", Etapa II: " + (etapa2Concluida ? "Concluída" : "Pendente");
    }
}
