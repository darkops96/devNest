package es.urjc.dad.devNest;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@SpringBootApplication
@EnableCaching
@EnableHazelcastHttpSession
public class DevNestApplication {

    private static final Log logger = LogFactory.getLog(DevNestApplication.class);

    public static void main(String[] args) {
        String newLine = System.lineSeparator();
        System.out.println(newLine +
                "*******************************" + newLine +
                "*********** DEVNEST ***********" + newLine +
                "*******************************" + newLine +
                "Developed by:" + newLine +
                "- Pablo de la Hoz Menendez" + newLine +
                "- Carlos Chamizo Cano" + newLine +
                "- Juan Jose Perez Abad" + newLine +
                "*******************************" + newLine);

        SpringApplication.run(DevNestApplication.class, args);
    }

    /**
     * Method in charge of initializing the database cache
     *
     * @param hazelcastInstance
     * @return
     */
    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        logger.info("Initializing Game Jams database cache");
        return new HazelcastCacheManager(hazelcastInstance);
    }

    /**
     * Get instance of hazelcast using custom configuration
     *
     * @param config
     * @return
     */
    @Bean
    public HazelcastInstance hazelcastInstance(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }


    /**
     * Method in charge of initializing Hazelcast
     *
     * @return
     */
    @Bean
    public Config config() {
        logger.info("Initializing Hazelcast");

        // Access Hazelcast configuration
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        // Activate multicast
        joinConfig.getMulticastConfig().setEnabled(true);

        // Create cache
        MapConfig usersMapConfig = new MapConfig().setName("gamejams");

        // Add cache to config
        config.addMapConfig(usersMapConfig);

        return config;
    }
}
