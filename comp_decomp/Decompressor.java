package comp_decomp;
import java.io.*;
import java.util.zip.GZIPInputStream;

public class Decompressor {
    public static void decompressFile(File file) throws IOException {

        String fileDirectory = file.getParent();

        FileInputStream fis = new FileInputStream(file);
        GZIPInputStream gzip  = new GZIPInputStream(fis);
        FileOutputStream fos = new FileOutputStream(fileDirectory+"/decompressedFile");

        byte[] buffer = new byte[1024];
        int len;

        while((len = gzip.read(buffer))!=-1){
            fos.write(buffer, 0, len);
        }
        gzip.close();
        fos.close();
        fis.close();


    }

    public static void main(String[] args) throws IOException{
        File path = new File("");
        decompressFile(path);
    }
}