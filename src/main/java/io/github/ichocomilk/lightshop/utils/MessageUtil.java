package io.github.ichocomilk.lightshop.utils;

import java.util.ArrayList;
import java.util.List;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class MessageUtil {

    public static String translate(String text) {
        return IridiumColorAPI.process(text);
    }

    public static List<String> translate(List<String> lines) {
        final List<String> newLines = new ArrayList<>(lines.size());

        for (String line : lines) {
            newLines.add(translate(line));
        }
        return newLines;
    }
}
