public class AtividadeCurricular implements RequisitoIntegralizacao {
    private String nome;
    private int cargaHoraria;
    private boolean obrigatoria;
    private boolean concluido = false;

    public AtividadeCurricular(String nome, int cargaHoraria, boolean obrigatoria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.obrigatoria = obrigatoria;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public boolean isObrigatoria() {
        return obrigatoria;
    }

    @Override
    public boolean isConcluido() {
        return concluido;
    }

    @Override
    public void concluir() {
        this.concluido = true;
    }

    @Override
    public String toString() {
        return nome + " (" + cargaHoraria + "h)" + (obrigatoria ? " [Obrigatória]" : " [Opcional]");
    }
}
