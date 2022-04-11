package me.adorerose.worms.storage.file;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Language extends YamlFileStorage {
    public String CONFIG_RELOADED = "§aКонфигурация перезагружена.";
    public String CMD_NOT_FOUND = "§cНеизвестная команда.";
    public String NO_PERMISSION = "§cУ Вас нет прав.";
    public String ONLY_CONSOLE = "§cЭта команда доступна только в консоли.";
    public String NOT_ENOUGH_ARGS = "§cНедостаточно аргументов: ";

    public List<String> TOP_NPC_TITLE = Lists.newArrayList(
            "%player%"
    );

    public Language() throws IOException {
        super("lang.yml", Language.class);
    }

}
