package org.osa678.vaulteconomy;

import org.bukkit.plugin.java.JavaPlugin;
import org.osa678.vaulteconomy.Comandos.*;
import org.osa678.vaulteconomy.Gancho.VaultSet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class VaultEconomy extends JavaPlugin {

    private static final Map<UUID, String> playerTags = new HashMap<>();


    @Override
    public void onEnable() {
        File dataFile = new File(new File(getDataFolder(), "data"), "balances.yml");
        VaultSet.register(dataFile);

        getCommand("economy").setExecutor(new VaultComandos());

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            // No es necesario llamar VaultSet.register() nuevamente aquí
            // Simplemente asegúrate de que el servicio de economía esté registrado correctamente en Vault.
        }
    }

    @Override
    public void onDisable() {

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public static Map<UUID, String> getPlayerTags() {
        return playerTags;
    }

    public static VaultEconomy getInstance() {
        return getPlugin(VaultEconomy.class);
    }
}
