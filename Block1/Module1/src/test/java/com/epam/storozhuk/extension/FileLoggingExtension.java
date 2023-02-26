package com.epam.storozhuk.extension;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FileLoggingExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String outputFileName = String.format("%s_%s.txt", context.getTestClass().get().getSimpleName(),
                context.getDisplayName());
        try {
            Files.createDirectory(Paths.get("testResults"));
        } catch (FileAlreadyExistsException ex) {
            System.out.println("testResult already exists");
        }

        File file = new File("testResults/" + outputFileName);
        try {
            file.createNewFile();
        } catch (FileAlreadyExistsException ex) {
            System.out.println(outputFileName + " already exists");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            String stringToOutput;
            try {
                Throwable result = context.getExecutionException().get();
                stringToOutput = String
                        .format("%s was thrown during the execution in %s_%s", result.getClass().getName(),
                                context.getTestClass().get().getSimpleName(), context.getDisplayName());
            } catch (NoSuchElementException ex) {
                stringToOutput = String
                        .format("The test %s_%s has been finished", context.getTestClass().get().getSimpleName(),
                                context.getDisplayName());
            }
            fos.write(stringToOutput.getBytes(StandardCharsets.UTF_8));
        }
    }
}
