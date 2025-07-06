import com.google.gson.*;
import java.io.*;
import java.util.*;

public class PersistenciaHistorico {
    private static final String ARQUIVO = "historico_unico.json";

    public static void salvar(Historico historico) {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            List<String> nomes = new ArrayList<>();
            for (RequisitoIntegralizacao r : historico.getRequisitosConcluidos()) {
                nomes.add(r.getNome());
            }
            new GsonBuilder().setPrettyPrinting().create().toJson(nomes, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }

    public static Historico carregar(List<RequisitoIntegralizacao> requisitos) {
        File f = new File(ARQUIVO);
        if (!f.exists()) return new Historico(requisitos);

        try (FileReader reader = new FileReader(f)) {
            List<String> nomes = new Gson().fromJson(reader, List.class);
            Historico h = new Historico(requisitos);
            for (RequisitoIntegralizacao r : requisitos) {
                if (nomes.contains(r.getNome())) {
                    r.concluir();
                    h.adicionarRequisito(r);
                }
            }
            return h;
        } catch (IOException e) {
            System.out.println("Erro ao carregar histórico: " + e.getMessage());
            return new Historico(requisitos);
        }
    }
}
