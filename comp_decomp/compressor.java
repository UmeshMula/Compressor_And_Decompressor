package comp_decomp;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

public class compressor {
    public static void compressFile(File file, int compressionLevel) throws IOException {
        String fileDirectory = file.getParent();
        String compressedFilePath = fileDirectory + "/Compressedfile.gz";

        // Reads the input bytes from the file
        FileInputStream fis = new FileInputStream(file);

        // Writes to the specified file
        FileOutputStream fos = new FileOutputStream(compressedFilePath);

        // Writes compressed file
        Deflater deflater = new Deflater(compressionLevel);
        GZIPOutputStream gzip = new GZIPOutputStream(fos);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = fis.read(buffer)) != -1) {
            deflater.setInput(buffer, 0, len);
            deflater.finish();

            while (!deflater.finished()) {
                int compressedLen = deflater.deflate(buffer);
                gzip.write(buffer, 0, compressedLen);
            }
            deflater.reset();
        }
        gzip.close();
        fos.close();
        fis.close();

        System.out.println("File compressed successfully: " + compressedFilePath);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the path to the file: ");
        String filePath = reader.readLine();
        File file = new File(filePath);

        if (file.exists()) {
            System.out.print("Enter the compression level (1-10): ");
            int compressionLevel = Integer.parseInt(reader.readLine());

            if (compressionLevel >= 1 && compressionLevel <= 10) {
                compressFile(file, compressionLevel);
            } else {
                System.out.println("Invalid compression level. Please enter a value between 1 and 10.");
            }
        } else {
            System.out.println("File not found!");
        }

        reader.close();
    }
}
