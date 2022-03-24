package me.adorerose.worms.storage.file;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Language extends YamlFileStorage {
    public String CONFIG_RELOADED = "§aКонфигурация перезагружена.";

    public List<String> TOP_NPC_TITLE = Lists.newArrayList(
            "%player%"
    );

    public Language() throws IOException {
        super("lang.yml", Language.class);
    }

}
