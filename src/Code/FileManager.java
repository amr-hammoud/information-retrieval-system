package Code;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class FileManager {

    private String path;
    private boolean isDir;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> lines = new ArrayList<>();

    public FileManager(String path) {

        try {
            this.path = path;

            File file = new File(path);

            // If file, read its contents into lines and words
            if (file.isFile()) {
                this.isDir = false;

                BufferedReader br = new BufferedReader(new FileReader(this.path));

                String line;
                while ((line = br.readLine()) != null) {
                    this.lines.add(line);
                    String[] words = line.split(" ");
                    Collections.addAll(this.words, words);
                }
                br.close();

                // If Directory, set isDir to true
            } else if (file.isDirectory()) {
                this.isDir = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFileList(String filesName, String filesExtension) {
        File directory = new File(this.path);
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.list((file, name) -> name.endsWith(filesExtension) && name.toLowerCase().startsWith(filesName)))));
    }

    public static void writeFile(String outputFilePath, String content) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(outputFilePath));
            fileWriter.write(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public String getWordsString(String delimiter) {
        StringBuilder result = new StringBuilder();
        for (String word : this.words) {
            result.append(word + delimiter);
        }
        return result.toString();
    }

    public ArrayList<String> getWords() {
        return this.words;
    }

    public ArrayList<String> getLines() {
        return this.lines;
    }

    public static String toFileContent(ArrayList<String> list) {
        StringBuilder result = new StringBuilder();
        for (String item : list) {
            result.append(item + "\n");
        }
        return result.toString();
    }

}
