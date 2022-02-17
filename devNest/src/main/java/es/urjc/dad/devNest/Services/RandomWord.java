package es.urjc.dad.devNest.Services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomWord {

    private static List<String> words;

    public RandomWord() {
        words = initializeList();
    }

    static List<String> initializeList() {
        List<String> aux = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(getResourceFile()));
            String line = reader.readLine();
            while (line != null) {
                aux.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aux;
    }

    public static String getRandomWord() {
        Random rn = new Random();
        return words.get(rn.nextInt(0, words.size() - 1));
    }

    static private String getResourceFile() throws IOException {
        File resource = new ClassPathResource("static/Text files/testFile.txt").getFile();
        return resource.getAbsolutePath();
    }
}
