package com.epam.storozhuk.input.impl;

import com.epam.storozhuk.input.AbstractInputReader;
import java.util.Scanner;

/**
 * Class designed specially for reading template from console
 */
public class ConsoleInputReader extends AbstractInputReader {

    @Override
    public void readTemplate() {
        System.out.println("Enter template\n");
        Scanner scanner = new Scanner(System.in);
        setTemplate(scanner.nextLine());
    }
}
