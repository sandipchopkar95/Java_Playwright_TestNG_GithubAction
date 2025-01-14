package testutils;

import org.apache.commons.io.FileUtils;
import java.io.*;

public class MergeReport {

    // Method to merge HTML files by concatenating their content
    public static void mergeHTMLFiles(String reportsFolderPath, File outputFile) throws IOException {
        File reportsFolder = new File(reportsFolderPath);
        File[] htmlFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".html"));

        if (htmlFiles == null || htmlFiles.length == 0) {
            System.out.println("No HTML files found in the Reports folder.");
            return;
        }

        // Concatenate the content of all HTML files
        StringBuilder mergedContent = new StringBuilder();
        for (File htmlFile : htmlFiles) {
            String content = FileUtils.readFileToString(htmlFile, "UTF-8");
            mergedContent.append(content).append("\n");
        }

        // Write the merged HTML content to the output file
        FileUtils.writeStringToFile(outputFile, mergedContent.toString(), "UTF-8");
        System.out.println("HTML files merged successfully into: " + outputFile.getPath());
    }

    public static void main(String[] args) {
        try {
            // Define path for HTML merging
            String htmlFolderPath = "./Reports";  // Path where .html files are located
            File mergedHTMLFile = new File("./Reports/merged_report.html");  // Output file for merged content

            // Merge HTML Files
            mergeHTMLFiles(htmlFolderPath, mergedHTMLFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
