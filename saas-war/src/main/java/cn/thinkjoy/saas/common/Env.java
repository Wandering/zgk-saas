package cn.thinkjoy.saas.common;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by douzy on 16/10/19.
 */
@Component
public class Env {
    private Properties props;
    private static final Logger logger = Logger.getLogger(Env.class.getName());

    public Env() {
        try {
            reInit();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void reInit()
            throws FileNotFoundException, IOException {
        String filePath = "/environment.properties";
        File eFile = new File(filePath);
        logger.info("filePath:" + filePath);

        Properties prop = new Properties();

        if (eFile.exists()) {
            logger.info("eFile.exists ");

            FileInputStream in = new FileInputStream(filePath);
            prop.load(in);
        } else {

//            prop.load(this.getClass().getClassLoader().getResourceAsStream("config/environment.properties"));
            prop.load(Env.class.getClassLoader().getResource("config/environment.properties").openStream());
            logger.info("eFile not exists ");
        }

        this.props = prop;
    }

    public String getProp(String key) {
        return (String) this.props.get(key);
    }

    public static void main(String[] args) {
        new Env();
    }
}
