import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Organizador {
    static Scanner path = new Scanner(System.in);

    static void organize() {
        String route = getFolder();
        try {
            try (Stream<Path> files = Files.list(Path.of(route))) {
                List<Path> listFiles = files.toList();
                for (int i = 0; i < listFiles.size(); i++) {
                    if (Files.isDirectory(listFiles.get(i))){
                        continue;
                    }
                    System.out.println(listFiles.get(i).getFileName().toString());
                    String folderName = switchFolder(listFiles.get(i).getFileName().toString());
                    moveFiles(route, folderName, listFiles.get(i).getFileName().toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static String getFolder() {
        String dir;
        System.out.println("Por favor ingrese a la ruta de la carpeta a organizar\nEjemplo: \"C:\\USUARIOS\\USUARIO\\Descargas\"");
        System.out.print("Ruta: ");
        dir = path.nextLine();
        dir = dir.replace("\"", "");
        return dir;
    }

    static void createFolder(String path, String folderName) {
        File newDir = new File(path + "\\" + folderName);
        if (!newDir.exists()) {
            newDir.mkdir();
        }
    }

    static void moveFiles(String path, String folder, String fileName) {
        createFolder(path, folder);
        try {
            Files.move(Path.of(path + "\\" + fileName), Path.of(path + "\\" + folder + "\\" + fileName));
        } catch (IOException e) {
            System.out.println("move fail");
            throw new RuntimeException(e);
        }
    }

    static String switchFolder(String fileName) {

        String ext = fileName.substring(fileName.lastIndexOf('.'));
        return switch (ext) {
            case ".pdf", ".doc", ".docx", ".txt", ".epub", ".ppt", ".pptx", ".xlsx", ".numbers", ".json", ".csv" ->
                    "Documentos";
            case ".jpg", ".png", ".gif", ".jpeg", ".svg" -> "Imagenes";
            case ".mp4", ".mp3", ".avi", ".mkv", ".webm" -> "Videos y Audios";
            case ".exe", ".iso", ".msi", ".zip", ".rar" -> "Instaladores_y_Comprimidos";
            default -> "Otros";
        };
    }
}
