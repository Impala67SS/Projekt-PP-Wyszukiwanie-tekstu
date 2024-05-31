import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

    private static boolean foundAnyMatch = false;
    private static StringBuilder matchingFilesInfo = new StringBuilder();

    public static void main(String[] args) throws IOException {
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(420, 120));  

        JPanel labelPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelPanel.add(new JLabel("Wpisz tekst, którego szukasz:", SwingConstants.CENTER));
        panel.add(labelPanel, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        panel.add(textField, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, panel, "Wyszukiwarka tekstu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.CANCEL_OPTION || textField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Brak wpisanego tekstu.", "Błąd", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String pattern = textField.getText().toLowerCase();
        Path currentDir = Paths.get(".");

        final String finalPattern = pattern;

        Files.walkFileTree(currentDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    if (searchFile(file, finalPattern)) {
                        foundAnyMatch = true;
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        if (!foundAnyMatch) {
            JOptionPane.showMessageDialog(null, "Brak dopasowań.", "Wynik", JOptionPane.INFORMATION_MESSAGE);
        } else {          
            JTextArea textArea = new JTextArea(matchingFilesInfo.toString());
            textArea.setFont(new Font("Arial", Font.PLAIN, 16));  
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400)); 

            JOptionPane.showMessageDialog(null, scrollPane, "Wynik wyszukiwania", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private static boolean searchFile(Path file, String pattern) {
        boolean foundMatch = false;
        try {
            List<String> lines = Files.readAllLines(file);
            Pattern regexPattern = convertPatternToRegex(pattern);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).toLowerCase();
                Matcher matcher = regexPattern.matcher(line);
                if (matcher.find()) {
                    foundMatch = true;
                    matchingFilesInfo.append(String.format("Plik: %s, Linia: %d, Dopasowanie: %s%n", file, i + 1, matcher.group()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundMatch;
    }
    private static Pattern convertPatternToRegex(String pattern) {
        String regex = pattern
            .replace(".", "\\.")
            .replace("*", ".*")
            .replace("?", "(?:.|\\R)");
        return Pattern.compile(regex);
    }
}
