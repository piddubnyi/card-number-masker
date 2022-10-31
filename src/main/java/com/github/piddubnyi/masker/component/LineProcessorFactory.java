package com.github.piddubnyi.masker.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class LineProcessorFactory {

    @Value("${card.number.pattern}")
    private String cardPattern;

    public LineProcessor createProcessor(File outFile) throws IOException {
        return new LineProcessor(outFile, cardPattern);
    }
}
