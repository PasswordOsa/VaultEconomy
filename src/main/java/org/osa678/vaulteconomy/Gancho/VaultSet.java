package org.osa678.vaulteconomy.Gancho;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.osa678.vaulteconomy.data.YamlDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

public class VaultSet extends AbstractEconomy {

    private YamlDataManager dataManager;

    public VaultSet(File dataFile) {
        this.dataManager = new YamlDataManager(dataFile);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "VaultEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return ((int) amount) + " " + (((int) amount) == 1 ? this.currencyNameSingular() : this.currencyNamePlural());
    }

    @Override
    public String currencyNamePlural() {
        return "Cows";
    }

    @Override
    public String currencyNameSingular() {
        return "Cow";
    }

    @Override
    public boolean hasAccount(String playerName) {
        return this.hasAccountByName(playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return this.hasAccountByName(playerName);
    }

    @Override
    public double getBalance(String playerName) {
        return this.getByName(playerName);
    }

    @Override
    public double getBalance(String playerName, String world) {
        return this.getByName(playerName);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return this.hasByName(playerName, amount);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return this.hasByName(playerName, amount);
    }

    private boolean hasAccountByName(String playerName) {
        Map<String, Integer> balances = dataManager.getBalances();
        return balances.containsKey(playerName);
    }

    private double getByName(String playerName) {
        Map<String, Integer> balances = dataManager.getBalances();
        return balances.getOrDefault(playerName, 0);
    }

    private boolean hasByName(String playerName, double amount) {
        Map<String, Integer> balances = dataManager.getBalances();
        return balances.getOrDefault(playerName, 0) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return this.withdrawPlayer(playerName, null, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        if (amount < 0)
            return new EconomyResponse(0, this.getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");

        if (!has(playerName, amount)) {
            return new EconomyResponse(0, this.getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }

        Map<String, Integer> balances = dataManager.getBalances();
        balances.put(playerName, (int) (this.getByName(playerName) - amount));
        dataManager.saveData(); // Guarda los datos actualizados
        System.out.println("Withdraw detected! Map: " + balances);

        return new EconomyResponse(amount, this.getByName(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return this.depositPlayer(playerName, null, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        if (amount < 0)
            return new EconomyResponse(0, this.getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");

        Map<String, Integer> balances = dataManager.getBalances();
        balances.put(playerName, (int) (this.getByName(playerName) + amount));
        dataManager.saveData(); // Guarda los datos actualizados
        System.out.println("Deposit detected! Map: " + balances);

        return new EconomyResponse(amount, this.getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, this.getBalance(player), EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    public static void register(File dataFile) {
        VaultSet vaultSet = new VaultSet(dataFile);
        Bukkit.getServicesManager().register(Economy.class, vaultSet, org.osa678.vaulteconomy.VaultEconomy.getInstance(), ServicePriority.Normal);
    }

}
