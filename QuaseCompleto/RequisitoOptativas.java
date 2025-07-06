import java.util.List;

public class RequisitoOptativas implements RequisitoIntegralizacao {
    private List<AtividadeCurricular> optativas;
    private int minimoConcluir;

    public RequisitoOptativas(List<AtividadeCurricular> optativas, int minimoConcluir) {
        this.optativas = optativas;
        this.minimoConcluir = minimoConcluir;
    }

    @Override
    public String getNome() {
        return "Optativas (concluir pelo menos " + minimoConcluir + " de " + optativas.size() + ")";
    }

    // Retorna se já concluiu o mínimo necessário entre as optativas
    @Override
    public boolean isConcluido() {
        int count = 0;
        for (AtividadeCurricular ac : optativas) {
            if (ac.isConcluido()) count++;
        }
        return count >= minimoConcluir;
    }

    // Marca o requisito como concluído "forçando" as optativas concluídas (não obrigatório, pode ser vazio)
    @Override
    public void concluir() {
        // Pode deixar vazio, ou marcar as primeiras 'minimoConcluir' como concluídas se quiser
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getNome() + ":\n");
        for (AtividadeCurricular ac : optativas) {
            sb.append("  - ").append(ac.toString()).append(" ").append(ac.isConcluido() ? "✅" : "❌").append("\n");
        }
        return sb.toString();
    }

    public List<AtividadeCurricular> getOptativas() {
        return optativas;
    }
}
