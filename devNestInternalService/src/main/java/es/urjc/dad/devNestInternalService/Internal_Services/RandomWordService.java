package es.urjc.dad.devNestInternalService.Internal_Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

/**
 * Class in vharge of reading the file words file and selecting 2 random ones
 */
@Service
@EnableAsync
public class RandomWordService {

    private List<String> words;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * reads the file and makes an arraylist with all the words so it can be used easier
     */
    @PostConstruct
    public void initRandomWord() {
        words = initializeList();
    }

    /**
     * Auxiliar method to read the TXT
     * @return  an arraylist with all the words
     */
    private List<String> initializeList() {
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

    /**
     * selects a random word from the words array
     * @return a word
     */
    @Async
    public CompletableFuture<String> getRandomWord() {
        Random rn = new Random();
        return CompletableFuture.completedFuture(words.get(rn.nextInt(0, words.size() - 1)));
    }

    /**
     * Access the resource file
     * @return the whole file in a inputStream
     * @throws IOException
     */
    private InputStream getResourceFile() throws IOException {
        Resource randomPool = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "static/topicsPool.txt");
        return randomPool.getInputStream();
    }
}
