import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class PersistenciaDados {

    private static final String CAMINHO_ARQUIVO = "discentes.json";

    public static Map<Discente, Historico> carregarDiscentes(List<RequisitoIntegralizacao> requisitosDoCurso) {
        Map<Discente, Historico> discentes = new HashMap<>();
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(CAMINHO_ARQUIVO)) {
            Type listType = new TypeToken<List<DiscenteDTO>>() {}.getType();
            List<DiscenteDTO> lista = gson.fromJson(reader, listType);

            for (DiscenteDTO dto : lista) {
                Discente discente = new Discente(dto.nome, dto.matricula, dto.senha);
                Historico historico = new Historico(discente, requisitosDoCurso);

                for (String nomeReq : dto.requisitosConcluidos) {
                    for (RequisitoIntegralizacao req : requisitosDoCurso) {
                        if (req.getNome().equalsIgnoreCase(nomeReq)) {
                            historico.adicionarRequisito(req);
                            break;
                        }
                    }
                }

                discentes.put(discente, historico);
            }

        } catch (IOException e) {
            System.out.println(">>> Nenhum arquivo de discentes encontrado, começando vazio.");
        }

        return discentes;
    }

    public static void salvarDiscentes(Map<Discente, Historico> discentes) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<DiscenteDTO> lista = new ArrayList<>();

        for (Map.Entry<Discente, Historico> entry : discentes.entrySet()) {
            Discente discente = entry.getKey();
            Historico historico = entry.getValue();

            List<String> requisitos = new ArrayList<>();
            for (RequisitoIntegralizacao r : historico.getRequisitosConcluidos()) {
                requisitos.add(r.getNome());
            }

            lista.add(new DiscenteDTO(discente.getNome(), discente.getMatricula(), discente.getSenha(), requisitos));
        }

        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar discentes: " + e.getMessage());
        }
    }

    // DTO interno para serialização
    private static class DiscenteDTO {
        String nome;
        String matricula;
        String senha;
        List<String> requisitosConcluidos;

        DiscenteDTO(String nome, String matricula, String senha, List<String> requisitosConcluidos) {
            this.nome = nome;
            this.matricula = matricula;
            this.senha = senha;
            this.requisitosConcluidos = requisitosConcluidos;
        }
    }
}
