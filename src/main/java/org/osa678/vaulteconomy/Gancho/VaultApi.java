package org.osa678.vaulteconomy.Gancho;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class VaultApi {

    private static Economy economy = null;
    private static Permission permissions = null;
    private static Chat chat = null;

    private static Map<UUID, String> playerUUIDs = new HashMap<>();

    private VaultApi() {
    }

    private static void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (rsp != null)
            economy = rsp.getProvider();
    }

    private static void setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);

        if (rsp != null)
            chat = rsp.getProvider();
    }

    private static void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);

        if (rsp != null)
            permissions = rsp.getProvider();
    }

    public static boolean hasEconomy() {
        return economy != null;
    }

    public static double getBalance(OfflinePlayer target) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.getBalance(target);
    }

    public static String withdraw(OfflinePlayer target, double amount) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.withdrawPlayer(target, amount).errorMessage;
    }

    public static void updatePlayerUUID(UUID uuid, String playerName) {
        playerUUIDs.put(uuid, playerName);
    }

    public static String getPlayerName(UUID uuid) {
        return playerUUIDs.get(uuid);
    }

    public static String deposit(OfflinePlayer target, double amount) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.depositPlayer(target, amount).errorMessage;
    }

    public static String formatCurrencySymbol(double amount) {
        if (!hasEconomy())
            throw new UnsupportedOperationException("Vault Economy not found, call hasEconomy() to check it first.");

        return economy.format(amount);
        //return amount + " " + (((int) amount) == 1 ? economy.currencyNameSingular() : economy.currencyNamePlural());
    }

    static {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupEconomy();
            setupChat();
            setupPermissions();
        }
    }
}
