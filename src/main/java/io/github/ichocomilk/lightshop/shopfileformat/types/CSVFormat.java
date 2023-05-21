package io.github.ichocomilk.lightshop.shopfileformat.types;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.bukkit.Bukkit;

import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;
import io.github.ichocomilk.lightshop.shopfileformat.FileFormat;

public class CSVFormat implements FileFormat {

    private final String shopFolder;

    public CSVFormat(String shopFolder) {
        this.shopFolder = shopFolder;
    }

    @Override
    public PriceItem[] getItems(String shop) {
        try {
            final List<String> lines = Files.readAllLines(Path.of(shopFolder + shop + ".csv"));
            final PriceItem[] items = new PriceItem[lines.size()];
            int count = -1;
    
            for (String line : lines) {
                final String[] split = line.split(",");
                items[++count] = (split.length < 3)
                    ? invalidItem
                    : createItem(getValue(split[1]), getValue(split[2]), split[0], shop);
            }
            return items;
        } catch (IOException e) {
            Bukkit.getLogger().warning("Error on read the file " + shop + ".csv");
            return new PriceItem[0];
        }
    }

    /**
     * Transform a string to number faster than {@link Integer#parseInt(String)}
     */
    private int getValue(String text) {
        final int length = text.length();
        int result = 0;

        for (int i = 0; i < length; i++) {
            result += text.charAt(i) - '0';
            result *= 10;
        }
        return result /= 10;
    }
}