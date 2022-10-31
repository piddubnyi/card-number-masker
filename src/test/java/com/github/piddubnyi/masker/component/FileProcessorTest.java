package com.github.piddubnyi.masker.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileProcessorTest {

    private static final String IN_DIRECTORY = "./log/test/in";
    private static final String OUT_DIRECTORY = "./log/test/out";
    @Mock
    private LineProcessorFactory lineProcessorFactory;
    @Mock
    private LineProcessor lineProcessor;
    private Path file;

    @BeforeEach
    void setUp() throws IOException {
        Path.of(IN_DIRECTORY).toFile().mkdirs();
        Path.of(OUT_DIRECTORY).toFile().mkdirs();
        file = Files.createFile(Path.of(IN_DIRECTORY, "test_input.log"));
        Files.write(file, List.of("line 1", "line 1111222233334444"));
    }

    @AfterEach
    void tearDown() {
        file.toFile().delete();
    }

    @Test
    void processFile() throws IOException {
        FileProcessor fileProcessor = new FileProcessor(IN_DIRECTORY, OUT_DIRECTORY, lineProcessorFactory);
        when(lineProcessorFactory.createProcessor(any())).thenReturn(lineProcessor);

        fileProcessor.processFiles();

        verify(lineProcessorFactory).createProcessor(any(File.class));
        verify(lineProcessor, times(2)).processLine(anyString());
    }
}