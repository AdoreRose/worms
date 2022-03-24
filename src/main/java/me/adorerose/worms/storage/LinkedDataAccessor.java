package me.adorerose.worms.storage;

import org.bukkit.configuration.ConfigurationSection;

public interface LinkedDataAccessor {
    default void write(ConfigurationSection section) { }
    default void read(ConfigurationSection section) { }
}
