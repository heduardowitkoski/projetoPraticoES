import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorDeCursoTXT {
    public static Curso lerCursoDeArquivo(String caminho) {
        String nomeCurso = "";
        List<AtividadeCurricular> atividades = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean lendoComponentes = false;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Curso:")) {
                    nomeCurso = linha.substring(6).trim();
                } else if (linha.startsWith("Componentes:")) {
                    lendoComponentes = true;
                } else if (lendoComponentes && linha.startsWith("-")) {
                    // Ex: - Nome da disciplina | 100 | true
                    String[] partes = linha.substring(1).split("\\|");
                    if (partes.length == 3) {
                        String nome = partes[0].trim();
                        int carga = Integer.parseInt(partes[1].trim());
                        boolean obrigatoria = Boolean.parseBoolean(partes[2].trim());
                        atividades.add(new AtividadeCurricular(nome, carga, obrigatoria));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return new Curso(nomeCurso, atividades);
    }
}
