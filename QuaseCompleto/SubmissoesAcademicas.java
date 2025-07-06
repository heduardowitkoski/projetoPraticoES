public class SubmissoesAcademicas implements RequisitoIntegralizacao {
    private int resumosSubmetidos = 0;
    private boolean artigoSubmetido = false;

    private final String[] titulosResumos = new String[2];
    private String tituloArtigo = null;

    @Override
    public String getNome() {
        return "Submissões Acadêmicas (2 resumos + 1 artigo)";
    }

    public void submeterResumo(String titulo) {
        for (int i = 0; i < titulosResumos.length; i++) {
            if (titulosResumos[i] == null || titulosResumos[i].isBlank()) {
                titulosResumos[i] = titulo;
                resumosSubmetidos++;
                break;
            }
        }
    }

    public void submeterArtigo(String titulo) {
        if (!artigoSubmetido) {
            artigoSubmetido = true;
            tituloArtigo = titulo;
        }
    }

    public int getResumosSubmetidos() {
        return resumosSubmetidos;
    }

    public boolean isArtigoSubmetido() {
        return artigoSubmetido;
    }

    public String[] getTitulosResumos() {
        return titulosResumos;
    }

    public String getTituloArtigo() {
        return tituloArtigo;
    }

    @Override
    public boolean isConcluido() {
        return resumosSubmetidos >= 2 && artigoSubmetido;
    }

    @Override
    public void concluir() {
        // Este método não deve ser chamado diretamente fora do Main
        // A submissão individual é feita via método submeterResumo/submeterArtigo
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNome())
                .append(" - Resumos: ").append(resumosSubmetidos).append("/2")
                .append(", Artigo: ").append(artigoSubmetido ? "✅" : "❌");

        if (titulosResumos[0] != null) sb.append(" | R1: \"").append(titulosResumos[0]).append("\"");
        if (titulosResumos[1] != null) sb.append(" | R2: \"").append(titulosResumos[1]).append("\"");
        if (tituloArtigo != null) sb.append(" | Artigo: \"").append(tituloArtigo).append("\"");

        return sb.toString();
    }
}
