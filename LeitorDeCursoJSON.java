import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class LeitorDeCursoJSON {

    public static Curso lerCursoDeArquivo(String caminho) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(caminho)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            String nomeCurso = jsonObject.get("nome").getAsString();
            JsonArray componentesJson = jsonObject.getAsJsonArray("componentes");

            List<RequisitoIntegralizacao> requisitos = new ArrayList<>();

            // Ler componentes curriculares (disciplinas)
            for (JsonElement elemento : componentesJson) {
                JsonObject obj = elemento.getAsJsonObject();

                String nome = obj.get("nome").getAsString();
                int cargaHoraria = obj.get("cargaHoraria").getAsInt();
                boolean obrigatoria = obj.get("obrigatoria").getAsBoolean();

                requisitos.add(new AtividadeCurricular(nome, cargaHoraria, obrigatoria));
            }

            // Adiciona os requisitos fixos do curso (não estão no JSON)
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
