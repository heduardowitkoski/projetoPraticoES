import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class PersistenciaHistorico {
    private static final String CAMINHO = "historico.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salvar(Historico historico) {
        JsonObject root = new JsonObject();
        JsonArray concluidosArray = new JsonArray();

        for (RequisitoIntegralizacao r : historico.getRequisitosConcluidos()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("nome", r.getNome());

            if (r instanceof AtividadeCurricular ac) {
                obj.addProperty("tipo", "AtividadeCurricular");
                obj.addProperty("codigo", ac.getCodigo());
                obj.addProperty("cargaHoraria", ac.cargaHoraria);
                obj.addProperty("obrigatoria", ac.obrigatoria);
                obj.addProperty("concluido", ac.isConcluido());
            } else if (r instanceof SubmissoesAcademicas s) {
                obj.addProperty("tipo", "SubmissoesAcademicas");
                obj.addProperty("resumos", s.getResumosSubmetidos());
                obj.addProperty("artigo", s.isArtigoSubmetido());

                JsonArray titulos = new JsonArray();
                for (String titulo : s.getTitulosResumos()) {
                    titulos.add(titulo == null ? "" : titulo);
                }
                obj.add("titulosResumos", titulos);
                obj.addProperty("tituloArtigo", s.getTituloArtigo());
            } else if (r instanceof EstagioSupervisionado e) {
                obj.addProperty("tipo", "EstagioSupervisionado");
                obj.addProperty("empresa", e.empresa);
                obj.addProperty("dataInicio", e.dataInicio);
                obj.addProperty("dataTermino", e.dataTermino);
            } else if (r instanceof UnipampaCidada u) {
                obj.addProperty("tipo", "UnipampaCidada");
                obj.addProperty("descricao", u.descricaoAtividade);
            } else if (r instanceof ProjetoFinal pf) {
                obj.addProperty("tipo", "ProjetoFinal");
                obj.addProperty("etapa1", pf.isEtapa1Concluida());
                obj.addProperty("etapa2", pf.isEtapa2Concluida());
            } else if (r instanceof PraticaExtensionista pe) {
                obj.addProperty("tipo", "PraticaExtensionista");
                obj.addProperty("etapa1", pe.isEtapa1Concluida());
                obj.addProperty("etapa2", pe.isEtapa2Concluida());
            } else {
                obj.addProperty("tipo", "Outro");
            }

            concluidosArray.add(obj);
        }

        root.add("concluidos", concluidosArray);

        try (FileWriter writer = new FileWriter(CAMINHO)) {
            gson.toJson(root, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }

    public static void carregar(Historico historico) {
        if (!Files.exists(Paths.get(CAMINHO))) return;

        try (FileReader reader = new FileReader(CAMINHO)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray concluidosArray = root.getAsJsonArray("concluidos");

            List<RequisitoIntegralizacao> requisitos = historico.getRequisitos();

            for (JsonElement elem : concluidosArray) {
                JsonObject obj = elem.getAsJsonObject();
                String nome = obj.get("nome").getAsString();

                RequisitoIntegralizacao r = requisitos.stream()
                        .filter(req -> req.getNome().equals(nome))
                        .findFirst()
                        .orElse(null);

                if (r == null) continue;

                String tipo = obj.has("tipo") ? obj.get("tipo").getAsString() : "";

                switch (tipo) {
                    case "AtividadeCurricular" -> {
                        if (r instanceof AtividadeCurricular ac) {
                            boolean concluido = obj.get("concluido").getAsBoolean();
                            if (concluido) ac.concluir();
                        }
                    }
                    case "SubmissoesAcademicas" -> {
                        if (r instanceof SubmissoesAcademicas s) {
                            JsonArray titulos = obj.getAsJsonArray("titulosResumos");
                            for (JsonElement el : titulos) {
                                String titulo = el.getAsString();
                                if (!titulo.isBlank()) {
                                    s.submeterResumo(titulo);
                                }
                            }

                            if (obj.has("artigo") && obj.get("artigo").getAsBoolean()) {
                                String tituloArtigo = obj.has("tituloArtigo")
                                        ? obj.get("tituloArtigo").getAsString()
                                        : "Artigo sem título";
                                s.submeterArtigo(tituloArtigo);
                            }
                        }
                    }
                    case "EstagioSupervisionado" -> {
                        if (r instanceof EstagioSupervisionado e) {
                            e.empresa = obj.get("empresa").getAsString();
                            e.dataInicio = obj.get("dataInicio").getAsString();
                            e.dataTermino = obj.get("dataTermino").getAsString();
                            e.concluir();
                        }
                    }
                    case "UnipampaCidada" -> {
                        if (r instanceof UnipampaCidada u) {
                            String descricao = obj.get("descricao").getAsString();
                            u.concluirComDescricao(descricao);
                        }
                    }
                    case "ProjetoFinal" -> {
                        if (r instanceof ProjetoFinal pf) {
                            if (obj.has("etapa1") && obj.get("etapa1").getAsBoolean()) pf.concluirEtapa1();
                            if (obj.has("etapa2") && obj.get("etapa2").getAsBoolean()) pf.concluirEtapa2();
                        }
                    }
                    case "PraticaExtensionista" -> {
                        if (r instanceof PraticaExtensionista pe) {
                            if (obj.has("etapa1") && obj.get("etapa1").getAsBoolean()) pe.concluirEtapa1();
                            if (obj.has("etapa2") && obj.get("etapa2").getAsBoolean()) pe.concluirEtapa2();
                        }
                    }
                    default -> {
                        // tipo desconhecido, ignorar
                    }
                }

                if (!historico.getRequisitosConcluidos().contains(r)) {
                    historico.adicionarRequisito(r);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar histórico: " + e.getMessage());
        }
    }
}
