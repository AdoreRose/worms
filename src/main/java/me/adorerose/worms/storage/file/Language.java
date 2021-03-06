package me.adorerose.worms.storage.file;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Language extends YamlFileStorage {
    public String CUSTOM = "§6Worms §7| §f";
    public String CONFIG_RELOADED = "§6Worms §7| §fКонфигурация перезагружена.";
    public String CMD_NOT_FOUND = "§6Worms §7| §fНеизвестная команда.";
    public String NO_PERMISSION = "§6Worms §7| §fУ Вас нет прав.";
    public String NOT_ENTIRE_SELECTED = "§6Worms §7| §fСначала выделите область.";
    public String ONLY_CONSOLE = "§6Worms §7| §fЭта команда доступна только в консоли.";
    public String NOT_ENOUGH_ARGS = "§6Worms §7| §fНедостаточно аргументов: ";
    public String AREA_MAPPING_STARTED = "§6Worms §7| §fИдёт разметка территории...";
    public String POINTS_IN_DIFF_WORLDS = "§6Worms §7| §fТочки выделения должны находиться в одном мире.";
    public String POS1 = "§6Worms §7| §fПервая точка установлена на §e(%d, %d, %d)";
    public String POS2 = "§6Worms §7| §fВторая точка установлена на §e(%d, %d, %d)";
    public String POINTS_SORTED = "§6Worms §7| §fКоординаты точек были отсортированы: §e(%d, %d, %d) -> (%d, %d, %d)";
    public String NOT_ENOUGH_SEL_VOLUME = "§6Worms §7| §fРазмер выделения должен быть не меньше §c%dx%dx%d!";

    public List<String> TOP_NPC_TITLE = Lists.newArrayList(
            "%player%"
    );

    public Language() throws IOException {
        super("lang.yml", Language.class);
    }

}
