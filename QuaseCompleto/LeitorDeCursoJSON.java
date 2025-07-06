import com.google.gson.*;
import java.io.FileReader;
import java.util.*;

public class LeitorDeCursoJSON {
    public static Curso lerCursoDeArquivo(String caminho) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject obj = gson.fromJson(reader, JsonObject.class);
            String nomeCurso = obj.get("nome").getAsString();

            List<RequisitoIntegralizacao> requisitos = new ArrayList<>();

            // Leitura das componentes obrigatórias (array "componentes")
            JsonArray componentes = obj.getAsJsonArray("componentes");
            for (JsonElement e : componentes) {
                JsonObject c = e.getAsJsonObject();
                String codigo = c.get("codigo").getAsString();
                String nome = c.get("nome").getAsString();
                int cargaHoraria = c.get("cargaHoraria").getAsInt();
                String area = c.has("area") ? c.get("area").getAsString() : "Geral";
                int semestre = c.has("semestre") ? c.get("semestre").getAsInt() : 0;
                boolean obrigatoria = c.get("obrigatoria").getAsBoolean();

                List<String> prereqs = new ArrayList<>();
                if (c.has("prerequisitos")) {
                    for (JsonElement p : c.getAsJsonArray("prerequisitos"))
                        prereqs.add(p.getAsString());
                }

                requisitos.add(new AtividadeCurricular(codigo, nome, cargaHoraria, area, semestre, prereqs, obrigatoria));
            }

            // Leitura das disciplinas optativas, se existir (array "optativas")
            if (obj.has("optativas")) {
                JsonArray optativasJson = obj.getAsJsonArray("optativas");
                List<AtividadeCurricular> listaOptativas = new ArrayList<>();

                for (JsonElement e : optativasJson) {
                    JsonObject c = e.getAsJsonObject();
                    String codigo = c.get("codigo").getAsString();
                    String nome = c.get("nome").getAsString();
                    int cargaHoraria = c.get("cargaHoraria").getAsInt();
                    String area = c.has("area") ? c.get("area").getAsString() : "Geral";
                    int semestre = c.has("semestre") ? c.get("semestre").getAsInt() : 0;
                    // As optativas têm obrigatoria = false
                    boolean obrigatoria = false;

                    List<String> prereqs = new ArrayList<>();
                    if (c.has("prerequisitos")) {
                        for (JsonElement p : c.getAsJsonArray("prerequisitos"))
                            prereqs.add(p.getAsString());
                    }

                    listaOptativas.add(new AtividadeCurricular(codigo, nome, cargaHoraria, area, semestre, prereqs, obrigatoria));
                }

                // Cria o requisito optativas com mínimo 3 para concluir
                RequisitoOptativas requisitoOptativas = new RequisitoOptativas(listaOptativas, 3);
                requisitos.add(requisitoOptativas);
            }

            // Componentes fixos já existentes
            requisitos.add(new Enade());
            requisitos.add(new EstagioSupervisionado());
            requisitos.add(new ProjetoFinal());
            requisitos.add(new PraticaExtensionista());
            requisitos.add(new AtividadesComplementares());
            requisitos.add(new CCCG());
            requisitos.add(new SubmissoesAcademicas());
            requisitos.add(new UnipampaCidada());

            return new Curso(nomeCurso, requisitos);
        } catch (Exception e) {
            System.out.println("Erro ao ler o JSON: " + e.getMessage());
            return null;
        }
    }
}
