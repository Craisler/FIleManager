import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Command {
    public static void findCommand(String currentLocation, String fileName) {
        ArrayList<File> fileList = new ArrayList<>();
        getFiles(new File(currentLocation), fileList, fileName);
        for (File file : fileList) {
            System.out.println("Найденные файлы:" + file.getAbsolutePath());
        }
    }

    private static void getFiles(File currentLocation, List<File> fileList, String fileName) {
        if (currentLocation.isDirectory()) {
            File[] directoryFiles = currentLocation.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        getFiles(file, fileList, fileName);
                    } else {
                        if (file.getName().toLowerCase().endsWith(fileName.toLowerCase())) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }

    public static void mvCommand(String currentLocation, String from, String to) {
        Path pathFrom = Paths.get(currentLocation, from);
        Path pathTo = Paths.get(currentLocation, to);
        try {
            Files.move(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void finfoCommand(String currentLocation, String fileName) throws IOException {
        Path file = Paths.get(currentLocation, fileName);
        if (Files.exists(file)) {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            System.out.println("Файл: " + fileName);
            System.out.println("Дата создания: " + attr.creationTime());
            System.out.println("Дата изменения: " + attr.lastModifiedTime());
            System.out.println("Размер: " + attr.size() + " байт");
            System.out.println("Владелец: " + Files.getOwner(file));
        } else {
            System.out.println("Указанного файла не существует");
        }
    }

    public static void lslCommand(String currentLocation) {
        File dirFiles = new File(currentLocation);
        File[] files = dirFiles.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                Date date = new Date(file.lastModified());
                System.out.println("Файл:" + file.getName() + " " + "(Размер: " + file.length() + " байт, последнее изменение: " + date + ")\n");
            }
        }
    }

    public static void cpCommand(String currentLocation, String from, String to) {
        Path pathFrom = Paths.get(currentLocation, from);
        Path pathTo = Paths.get(currentLocation, to);
        try {
            Files.copy(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String cdCommand(String currentLocation, String path) throws IOException {
        return new File(currentLocation, path).getCanonicalPath();
    }

    public static boolean rmCommand(String currentLocation, String directoryName) {
        File file = new File(currentLocation + File.separator + directoryName);
        return file.delete();
    }

    public static List<String> lsCommand(String currentLocation) {
        File file = new File(currentLocation);
        return Arrays.stream(file.listFiles())
                .map(File::getName)
                .toList();
    }

    public static boolean mkdirCommand(String currentLocation, String directoryName) {
        File file = new File(currentLocation + File.separator + directoryName);
        return file.mkdir();
    }

    public static void helpCommand() {
        System.out.println("""
                Доступные команды:
                ls – распечатать список файлов текущего каталога. Если добавлен ключ -l, то должна быть
                более подробная информация о файлах: имя – размер – дата последнего изменения
                cd [path] – переход в указанную поддиректорию. cd .. – переход в родительский каталог.
                mkdir [name] – создание новой директории с указанным именем
                rm [filename] – удаление указанного файла или директории (* возможность удаления не
                пустого каталога)
                mv [source] [desƟnaƟon] – переименовать/перенести файл или директорию
                cp [source] [desƟnaƟon] – скопировать файл
                finfo [filename] – получить подробную информацию о файле
                help – вывод в консоль всех поддерживаемых команд
                find [filename] – найти файл с указанным именем в текущем каталоге или любом его
                подкаталоге
                exit – завершить работу
                                """);
    }
}