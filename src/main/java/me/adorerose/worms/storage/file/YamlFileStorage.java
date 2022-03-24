package me.adorerose.worms.storage.file;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.storage.DataStorage;
import me.adorerose.worms.storage.LinkedDataAccessor;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Map;

public abstract class YamlFileStorage extends DataStorage<YamlConfiguration> {
    private File file;
    private Class<? extends DataStorage<YamlConfiguration>> storageClass;

    protected YamlFileStorage(String fileName, Class<? extends YamlFileStorage> storageClass) throws IOException {
        super(new YamlConfiguration());
        File pluginDir = WormsPlugin.getInstance().getDataFolder();
        this.storageClass = storageClass;
        this.file = new File(pluginDir.getAbsolutePath() + File.separator + fileName);
        if (!file.exists()) {
            if (!Files.exists(pluginDir.toPath())) Files.createDirectory(pluginDir.toPath());
            file.createNewFile();
        }
    }

    public void save(SavingTarget target) {
        try {
            for (Field field: storageClass.getFields()) {
                super.handle.set(field.getName().toLowerCase().replaceAll("__", "."), field.get(this));
            }
            if (target.contains(SavingTarget.LINKED_ACCESSORS) && super.linkedAccessors != null) {
                for (Map.Entry<String, LinkedDataAccessor> accessor: super.linkedAccessors.entrySet()) {
                    ConfigurationSection section = handle.getConfigurationSection(accessor.getKey());
                    if (section == null) section = handle.createSection(accessor.getKey());
                    accessor.getValue().write(section);
                }
            }
            handle.save(file);
        } catch (IllegalAccessException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load() {
        try {
            handle.load(file);
            for (Field field: storageClass.getDeclaredFields()) {
                Object value = handle.get(field.getName().toLowerCase().replaceAll("__", "."));
                if (value != null) field.set(this, value);
                else if (field.get(this) != null) save(SavingTarget.STORAGE);
            }
            if (super.linkedAccessors != null) {
                for (Map.Entry<String, LinkedDataAccessor> accessor: super.linkedAccessors.entrySet()) {
                    ConfigurationSection section = handle.getConfigurationSection(accessor.getKey());
                    accessor.getValue().read(section);
                }
            }
        } catch (IllegalAccessException | IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }

}
