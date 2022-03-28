package me.adorerose.worms.storage.file;

import java.io.IOException;

public class Configuration extends YamlFileStorage {
    public String MYSQL__HOST = "127.0.0.1";
    public int MYSQL__PORT = 3306;
    public String MYSQL__USER = "cat";
    public String MYSQL__PASSWORD = "123";
    public int WAND_ITEM_ID = 280;

    public Configuration() throws IOException {
        super("config.yml", Configuration.class);
    }

}
