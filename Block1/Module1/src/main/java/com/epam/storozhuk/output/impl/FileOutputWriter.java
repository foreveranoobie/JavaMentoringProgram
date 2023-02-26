package com.epam.storozhuk.output.impl;

import com.epam.storozhuk.output.OutputWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Prints converted template to file
 */
public class FileOutputWriter implements OutputWriter {

    private String outputDir;

    public FileOutputWriter(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void writeOutput(String result) throws IOException {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(Files.createFile(Paths.get(outputDir)).toFile());
        } catch (FileAlreadyExistsException ex) {
            fos = new FileOutputStream(Paths.get(outputDir).toFile());
        }
        fos.write(result.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }
}
