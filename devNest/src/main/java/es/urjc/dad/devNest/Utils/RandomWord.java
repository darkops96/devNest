package es.urjc.dad.devNest.Utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
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
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("devNest/src/main/resources/static/Text files/testFile.txt"));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String getRandomWord() {
        Random rn = new Random();
        return words.get(rn.nextInt(0, words.size() - 1));
    }

}
