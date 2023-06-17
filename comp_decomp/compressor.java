package comp_decomp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class compressor {
    private static final int[] COMPRESSION_PERCENTAGES = {
            10, 20, 30, 40, 50, 60, 70, 80, 90, 100
    };

    public static void compressFile(File file, int compressionPercentage) throws IOException {
        String fileDirectory = file.getParent();
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(fileDirectory + "/CompressedFile.gz");

        int compressionLevel = getCompressionLevel(compressionPercentage);
        Deflater deflater = new Deflater(compressionLevel);
        GZIPOutputStream gzip = new GZIPOutputStream(fos) {
            {
                def.setLevel(compressionLevel);
            }
        };

        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            gzip.write(buffer, 0, len);
        }

        gzip.close();
        fos.close();
        fis.close();
    }

    private static int getCompressionLevel(int compressionPercentage) {
        int index = (compressionPercentage - 1) / 10;  // Mapping percentage to array index
        index = Math.max(0, Math.min(index, COMPRESSION_PERCENTAGES.length - 1));  // Clamp index within array bounds
        return index;
    }

    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // File type filter selection
        FileNameExtensionFilter filter = null;
        String fileTypeSelection = JOptionPane.showInputDialog("Select the file type to compress (jpg, pdf, word, zip, document):");
        switch (fileTypeSelection.toLowerCase()) {
            case "jpg":
                filter = new FileNameExtensionFilter("JPEG Images", "jpg", "jpeg");
                break;
            case "pdf":
                filter = new FileNameExtensionFilter("PDF Documents", "pdf");
                break;
            case "word":
                filter = new FileNameExtensionFilter("Microsoft Word Documents", "doc", "docx");
                break;
            case "zip":
                filter = new FileNameExtensionFilter("ZIP Archives", "zip");
                break;
            case "png":
                filter = new FileNameExtensionFilter("PNG Images", "png");
                break;
            case "document":
                filter = new FileNameExtensionFilter("All Documents", "jpg", "jpeg", "pdf", "doc", "docx", "zip", "png");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid file type selection!");
                System.exit(1);
        }

        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter); // Set the selected file filter as the default filter

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String compressionPercentageInput = JOptionPane.showInputDialog("Select the compression percentage (10-100):");
            int compressionPercentage = Integer.parseInt(compressionPercentageInput);

            compressFile(selectedFile, compressionPercentage);
        }
    }
}
