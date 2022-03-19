package es.urjc.dad.devNest.Internal_Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

@Service
public class RandomWord {

    private List<String> words;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void initRandomWord() {
        words = initializeList();
    }

    List<String> initializeList() {
        List<String> aux = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getResourceFile()));
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

    private InputStream getResourceFile() throws IOException {
        Resource randomPool = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "static/Text_Files/topicsPool.txt");
        return randomPool.getInputStream();
    }
}
