package letter;

import neuron.DefaultData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public final class LetterUtils {

    public static Letter get(String pathName) {
        LinkedList<LinkedList<Double>> representation = new LinkedList<>();
        String name = "";

        try {
            Path path = Paths.get(pathName);
            path = path.isAbsolute() ? path : Paths.get(System.getProperty("user.dir") + pathName);
            File file = path.toFile();
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().replaceAll(DefaultData.LETTER_CHARACTER, "1");
                LinkedList<Double> doubledLine = new LinkedList<>();

                for (char character : line.toCharArray()) {
                    doubledLine.add(Double.parseDouble(character + ""));
                }
                representation.add(new LinkedList<>(doubledLine));
            }
            name = file.getName();

        } catch (FileNotFoundException e) {
            System.err.println("File with letter pattern ( \"" + pathName + "\") not found!");
            e.printStackTrace();
        }

        return new Letter(representation, name);
    }

    public static LinkedList<Letter> getAll(String path) {
        LinkedList<Letter> letters = new LinkedList<>();

        try {
            Files.walk(Paths.get(System.getProperty("user.dir") + path)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    letters.add(new Letter(filePath.toString()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }
}