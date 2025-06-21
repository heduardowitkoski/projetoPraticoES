import java.io.FileWriter;
import java.io.IOException;

public class Relatorio {
    public static void gerar(Historico historico) {
        String nomeArquivo = "historico_" + historico.getDiscente().getMatricula() + ".txt";
        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.write("====== HISTÓRICO ACADÊMICO ======\n");
            fw.write("Discente: " + historico.getDiscente().getNome() + "\n");
            fw.write("Matrícula: " + historico.getDiscente().getMatricula() + "\n\n");
            fw.write("Requisitos Concluídos:\n");
            for (RequisitoIntegralizacao r : historico.getRequisitosConcluidos()) {
                fw.write("- " + r.getNome() + "\n");
            }
            fw.write("\n===============================\n");
            System.out.println("Relatório gerado em " + nomeArquivo);
            Log.registrar("Gerou relatório do discente " + historico.getDiscente().getMatricula());
        } catch (IOException e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
