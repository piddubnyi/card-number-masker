package com.github.piddubnyi.masker.component;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Slf4j
public class LineProcessor implements AutoCloseable {

    public static final String MASK = "*";
    public static final int VISIBLE_SIZE = 4;
    private final BufferedWriter writer;
    private final Pattern pattern;

    public LineProcessor(File file, String cardPattern) throws IOException {
        writer = Files.newBufferedWriter(file.toPath());
        pattern = Pattern.compile(cardPattern);
    }

    public void processLine(String line) {
        try {
            writer.write(pattern.matcher(line).replaceAll(this::doReplacement));
            writer.newLine();
        } catch (IOException e) {
            log.error("Error writing updated line", e);
        }
    }

    private String doReplacement(MatchResult matchResult) {
        final String cardNumber = matchResult.group();
        return MASK.repeat(cardNumber.length() - VISIBLE_SIZE)
            + cardNumber.substring(cardNumber.length() - VISIBLE_SIZE);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
