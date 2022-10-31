package com.github.piddubnyi.masker.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
class LineProcessorTest {

    private static final String OUT_DIRECTORY = "./log/test/out";
    private File testFile;

    @BeforeEach
    void setUp() {
        Path.of(OUT_DIRECTORY).toFile().mkdirs();
        testFile = Path.of(OUT_DIRECTORY, "test_out_"+System.currentTimeMillis()+".log").toFile();
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    @Test
    void processesLineWithoutPattern() throws IOException {
        final String testLine = "test";
        try (LineProcessor lineProcessor = createProcessor()){
            lineProcessor.processLine(testLine);
        }
        Assertions.assertTrue(Files.readAllLines(testFile.toPath()).contains(testLine));
    }

    @Test
    void processesLineWithPattern() throws IOException {
        final String testLine = "test 1111222233334444";
        try (LineProcessor lineProcessor = createProcessor()){
            lineProcessor.processLine(testLine);
        }
        Assertions.assertFalse(Files.readAllLines(testFile.toPath()).contains(testLine));
        Assertions.assertTrue(Files.readAllLines(testFile.toPath()).contains("test ************4444"));
    }

    private LineProcessor createProcessor() throws IOException {
        return new LineProcessor(testFile, "[0-9]{16}");
    }
}