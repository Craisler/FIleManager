import java.io.*;
import java.util.*;

//реализовать консольный файловый менеджер на java.io

public class FileManager {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String startPath = ".";
        String currentLocation = new File(startPath).getCanonicalPath();
        String command[] = getCommand(currentLocation);
        while (command[0] != "exit") {
            switch (command[0]) {
                // help – вывод в консоль всех поддерживаемых команд
                case "help" -> {
                    Command.helpCommand();
                }

                //mkdir [name] – создание новой директории с указанным именем
                case "mkdir" -> {
                    if (command.length > 1 && command[1] != null && !command[1].isEmpty()) {
                        String directoryName = command[1];
                        System.out.println(Command.mkdirCommand(currentLocation, directoryName));
                    } else {
                        System.out.println("Введите название папки для создания");
                    }
                    break;
                }

                case "cd" -> {
                    if (command.length > 1 && command[1] != null && !command[1].isEmpty()) {
                        String path = command[1];
                        currentLocation = Command.cdCommand(currentLocation, path);
                    } else {
                        System.out.println("Введите название папки для перехода");
                    }
                    break;
                }

                // ls – распечатать список файлов текущего каталога. Если добавлен ключ -l, то должна быть
                //более подробная информация о файлах: имя – размер – дата последнего изменения
                case "ls" -> {
                    if (command.length == 2) {
                        switch (command[1]) {
                            case "-l" -> {
                                Command.lslCommand(currentLocation);
                                break;
                            }
                            default -> {
                                System.out.println("Введён неизвестный ключ");
                            }
                        }
                    } else {
                        System.out.println(Command.lsCommand(currentLocation));
                    }
                    break;
                }

                // rm [filename] – удаление указанного файла или директории (* возможность удаления не пустого каталога)
                case "rm" -> {
                    String directoryName = command[1];
                    System.out.println(Command.rmCommand(currentLocation, directoryName));
                    break;
                }

                // mv [source] [desƟnaƟon] – переименовать/перенести файл или директорию
                case "mv" -> {
                    String from = command[1];
                    String to = command[2];
                    Command.mvCommand(currentLocation, from, to);
                    break;
                }

                // cp [source] [desƟnaƟon] – скопировать файл
                case "cp" -> {
                    String from = command[1];
                    String to = command[2];
                    Command.cpCommand(currentLocation, from, to);
                    break;
                }

//                 finfo [filename] – получить подробную информацию о файле
                case "finfo" -> {
                    String fileName = command[1];
                    Command.finfoCommand(currentLocation, fileName);
                    break;
                }
                // * find [filename] – найти файл с указанным именем в текущем каталоге или любом его
//подкаталоге
                case "find" -> {
                    String fileName = command[1];
                    Command.findCommand(currentLocation, fileName);
                    break;
                }
                case "pwd" -> {
                    System.out.println(currentLocation);
                    break;
                }
                // exit – завершить работу
                case "exit" -> {
                    return;
                }
                default -> {
                    System.out.println("Введена неизвестная команда");
                }
            }
            command = getCommand(currentLocation);
        }
    }

    public static String[] getCommand(String currentLocation) {
        return SCANNER.nextLine().split(" ");
    }
}
