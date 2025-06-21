import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorDeCursoJSON {

    public static Curso lerCursoDeArquivo(String caminho) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            String nomeCurso = jsonObject.get("nome").getAsString();
            JsonArray componentesJson = jsonObject.getAsJsonArray("componentes");

            List<RequisitoIntegralizacao> requisitos = new ArrayList<>();

            for (JsonElement elemento : componentesJson) {
                JsonObject obj = elemento.getAsJsonObject();

                // Defina codigo e area como "N/A" se não estiverem no JSON
                String codigo = obj.has("codigo") ? obj.get("codigo").getAsString() : "N/A";
                String nome = obj.get("nome").getAsString();
                int cargaHoraria = obj.get("cargaHoraria").getAsInt();
                String area = obj.has("area") ? obj.get("area").getAsString() : "Geral";
                int semestre = obj.has("semestre") ? obj.get("semestre").getAsInt() : 0;

                List<String> prerequisitos = new ArrayList<>();
                if (obj.has("prerequisitos")) {
                    JsonArray prereqArray = obj.getAsJsonArray("prerequisitos");
                    for (JsonElement e : prereqArray) {
                        prerequisitos.add(e.getAsString());
                    }
                }

                boolean obrigatoria = obj.get("obrigatoria").getAsBoolean();

                requisitos.add(new AtividadeCurricular(
                        codigo,
                        nome,
                        cargaHoraria,
                        area,
                        semestre,
                        prerequisitos,
                        obrigatoria
                ));
            }

            // Adiciona requisitos institucionais fixos (não estão no JSON)
            requisitos.add(new Enade());
            requisitos.add(new EstagioSupervisionado());
            requisitos.add(new ProjetoFinal());
            requisitos.add(new PraticaExtensionista());
            requisitos.add(new AtividadesComplementares());
            requisitos.add(new CCCG());
            requisitos.add(new SubmissoesAcademicas());
            requisitos.add(new UnipampaCidada());

            return new Curso(nomeCurso, requisitos);

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON: " + e.getMessage());
            return null;
        }
    }

}

