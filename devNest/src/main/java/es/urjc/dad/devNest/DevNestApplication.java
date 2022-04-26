package es.urjc.dad.devNest;

import java.util.Collections;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@SpringBootApplication
@EnableHazelcastHttpSession
public class DevNestApplication {
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

    @Bean
    public Config config()
    {
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("127.0.0.1"));

        return config;
    }
}
