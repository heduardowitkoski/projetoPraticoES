import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    public static void registrar(String mensagem) {
        try (FileWriter fw = new FileWriter("log.txt", true)) {
            fw.write(LocalDateTime.now() + " - " + mensagem + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no log: " + e.getMessage());
        }
    }
}
