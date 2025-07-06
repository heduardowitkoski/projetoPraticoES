public class SubmissoesAcademicas implements RequisitoIntegralizacao {
    private int resumosSubmetidos = 0;
    private boolean artigoSubmetido = false;

    @Override
    public String getNome() {
        return "Submissões Acadêmicas (2 resumos + 1 artigo)";
    }

    public void submeterResumo() {
        if (resumosSubmetidos < 2) {
            resumosSubmetidos++;
        }
    }

    public void submeterArtigo() {
        artigoSubmetido = true;
    }

    @Override
    public boolean isConcluido() {
        return resumosSubmetidos >= 2 && artigoSubmetido;
    }

    @Override
    public void concluir() {
        resumosSubmetidos = 2;
        artigoSubmetido = true;
    }

    @Override
    public String toString() {
        return getNome() + " - Resumos: " + resumosSubmetidos + "/2, Artigo: " + (artigoSubmetido ? "✅" : "❌");
    }
}