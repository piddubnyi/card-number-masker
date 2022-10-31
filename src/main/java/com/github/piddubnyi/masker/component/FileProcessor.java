package com.github.piddubnyi.masker.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Component
public class FileProcessor {

    private final String inDirectory;
    private final String outDirectory;
    private final LineProcessorFactory lineProcessorFactory;
    @Autowired
    public FileProcessor(
            @Value("${masker.directory.input}") String inDirectory,
            @Value("${masker.directory.output}") String outDirectory,
            LineProcessorFactory lineProcessorFactory
    ) {
        this.inDirectory = inDirectory;
        this.outDirectory = outDirectory;
        this.lineProcessorFactory = lineProcessorFactory;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void processFiles() {
        log.info("Start processing files in: " + inDirectory);
        try (Stream<Path> files = Files.list(Path.of(inDirectory))) {
            files.forEach(this::processFile);
        } catch (IOException e) {
            log.error("Error processing files", e);
        }
        log.info("Done processing files in: " + inDirectory);
    }

    private void processFile(Path path) {
        log.info("Start processing file: " + path);
        File outFile = new File(outDirectory, path.getFileName().toString());
        try (Stream<String> lines = Files.lines(path);
             LineProcessor lineProcessor = lineProcessorFactory.createProcessor(outFile)
        ) {
            lines.forEach(lineProcessor::processLine);
            log.info("Done processing file: " + path+ ", deleted: "+path.toFile().delete());
        } catch (IOException e) {
            log.error("Error processing line in file: " + path, e);
        }
    }
}
