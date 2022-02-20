package es.urjc.dad.devNest.Internal_Services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomWord {

    private List<String> words;

    public RandomWord() {
        words = initializeList();
    }

    List<String> initializeList() {
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

    public String getRandomWord() {
        Random rn = new Random();
        return words.get(rn.nextInt(0, words.size() - 1));
    }

    static String getResourceFile() throws IOException {
        File resource = new ClassPathResource("static/Text_Files/topicsPool.txt").getFile();
        return resource.getAbsolutePath();
    }
}
