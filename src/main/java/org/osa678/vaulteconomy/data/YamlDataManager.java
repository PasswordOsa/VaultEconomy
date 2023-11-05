package org.osa678.vaulteconomy.data;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class YamlDataManager {

    private final File dataFile;
    private final Map<String, Integer> balances;

    public YamlDataManager(File dataFile) {
        this.dataFile = dataFile;
        this.balances = new HashMap<>();

        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs(); // Crea la carpeta si no existe
            saveData(); // Llama al método para crear el archivo
        } else {
            loadData();
        }
    }

    public void loadData() {
        try (FileReader reader = new FileReader(dataFile)) {
            Yaml yaml = new Yaml();
            balances.putAll(yaml.load(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            FileWriter writer = new FileWriter(dataFile);
            Yaml yaml = new Yaml();
            yaml.dump(balances, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getBalances() {
        return balances;
    }
}
