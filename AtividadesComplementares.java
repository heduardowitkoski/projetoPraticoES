public class AtividadesComplementares implements RequisitoIntegralizacao {
    private int horasConcluidas = 0;
    private final int horasNecessarias = 75;

    @Override
    public String getNome() {
        return "Atividades Complementares (75 horas)";
    }

    public void adicionarHoras(int horas) {
        horasConcluidas += horas;
        if (horasConcluidas > horasNecessarias) {
            horasConcluidas = horasNecessarias;
        }
    }

    @Override
    public boolean isConcluido() {
        return horasConcluidas >= horasNecessarias;
    }

    @Override
    public void concluir() {
        horasConcluidas = horasNecessarias;
    }

    @Override
    public String toString() {
        return getNome() + " - " + horasConcluidas + "/" + horasNecessarias + "h " + (isConcluido() ? "✅" : "❌");
    }
}
