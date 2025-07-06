import com.google.gson.*;
import java.io.FileReader;
import java.util.*;

public class LeitorDeCursoJSON {
    public static Curso lerCursoDeArquivo(String caminho) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject obj = gson.fromJson(reader, JsonObject.class);
            String nomeCurso = obj.get("nome").getAsString();
            JsonArray componentes = obj.getAsJsonArray("componentes");

            List<RequisitoIntegralizacao> requisitos = new ArrayList<>();

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

            // Adiciona componentes fixos
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
