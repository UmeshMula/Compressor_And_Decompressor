package GUI;

import comp_decomp.Decompressor;
import comp_decomp.compressor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class App_Frame extends JFrame implements ActionListener
{
    JButton CompressButton;
    JButton DecompressButton;
    JPanel headerPanel;

    JLabel headingLabel;

    // Custom RGB foreground colors
    Color compressButtonTextColor = new Color(95, 48, 134); // Red
    Color decompressButtonTextColor = new Color(74, 32, 115); // Blue

    App_Frame(String name)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.getContentPane().setBackground(new Color(177, 150, 185));
        this.setLayout(null);
        this.setTitle(name);

        headerPanel = new JPanel();
        headerPanel.setBounds(13, 18, 760, 75);
        headerPanel.setBackground(new Color(211, 194, 194));
        Border headerBorder = BorderFactory.createLineBorder(new Color(107, 16, 134), 5);
        headerPanel.setBorder(headerBorder);

        headingLabel = new JLabel("<html><span style='font-weight: plain; font-size: 32px;'>Compress & DeCompress</span></html>");
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Palatino Linotype", Font.PLAIN | Font.ITALIC, 40));
        headingLabel.setForeground(new Color(59, 33, 100));
        headerPanel.add(headingLabel);

        CompressButton = new JButton("Select File To Compress");
        CompressButton.setBounds(200, 190, 375, 40);
        CompressButton.addActionListener(this);
        CompressButton.setBackground(new Color(179, 196, 164));
        Border compressBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 32, 91), 4, true),
                BorderFactory.createLineBorder(new Color(245, 218, 223), 3, true)
        );
        CompressButton.setBorder(compressBorder);
        CompressButton.setForeground(compressButtonTextColor); // Set the foreground color
        CompressButton.setPressedIcon(new ImageIcon(getPressedButtonBackground()));

        DecompressButton = new JButton("Select File To Decompress");
        DecompressButton.setBounds(200, 310, 375, 45);
        DecompressButton.addActionListener(this);
        DecompressButton.setBackground(new Color(161, 176, 147));
        Border decompressBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 32, 91), 4, true),
                BorderFactory.createLineBorder(new Color(245, 218, 223), 3, true)
        );
        DecompressButton.setBorder(decompressBorder);
        DecompressButton.setForeground(decompressButtonTextColor); // Set the foreground color
        DecompressButton.setPressedIcon(new ImageIcon(getPressedButtonBackground()));

        this.add(headerPanel);
        this.add(CompressButton);
        this.add(DecompressButton);

        CompressButton.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 25));
        DecompressButton.setFont(new Font("Bodoni MT Condensed", Font.BOLD | Font.ITALIC, 25));

        this.setVisible(true);
    }

    private Image getPressedButtonBackground()
    {
        int buttonWidth = CompressButton.getWidth();
        int buttonHeight = CompressButton.getHeight();
        BufferedImage image = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(122, 211, 168));
        g2d.fillRect(0, 0, buttonWidth, buttonHeight);
        g2d.dispose();
        return image;
    }

    private String showFileTypeSelectionDialog()
    {
        Object[] options = {"JPG", "JPEG", "PNG", "ZIP", "DOC", "WORD", "PDF"};
        int selection = JOptionPane.showOptionDialog(this, "Select file type to compress:", "File Type Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selection == -1)
        {
            return null;
        }
        return options[selection].toString().toLowerCase();
    }

    private int showQualitySelectionDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter quality range (1-10):");
        int quality = -1;
        try {
            quality = Integer.parseInt(input);
            int percentage = quality * 10;
            JOptionPane.showMessageDialog(this, "Compression level selected: " + percentage + "%");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Quality range should be an integer between 1 and 10.");
        }
        return quality;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == CompressButton)
        {
            String fileType = showFileTypeSelectionDialog();
            if (fileType != null)
            {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(fileType.toUpperCase() + " Files", fileType);
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setFileFilter(fileFilter);

                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    System.out.print(file);

                    int quality = showQualitySelectionDialog();
                    if (quality >= 1 && quality <= 10)
                    {
                        try
                        {
                            int compressionLevel = quality;
                            compressor.compressFile(file, compressionLevel);
                            JOptionPane.showMessageDialog(null, "Successfully Compressed!");
                        }
                        catch (Exception ee)
                        {
                            JOptionPane.showMessageDialog(null, ee.toString());
                        }
                    }
                }
            }
        }
        if (e.getSource() == DecompressButton)
        {
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Files", "png");
            FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("JPG Files", "jpg");
            FileNameExtensionFilter zipFilter = new FileNameExtensionFilter("ZIP Files", "zip");
            FileNameExtensionFilter docFilter = new FileNameExtensionFilter("DOC Files", "doc");
            FileNameExtensionFilter wordFilter = new FileNameExtensionFilter("WORD Files", "word");
            FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");
            FileNameExtensionFilter gzFilter = new FileNameExtensionFilter(".GZ Files", "zip");

            fileChooser.addChoosableFileFilter(pngFilter);
            fileChooser.addChoosableFileFilter(jpgFilter);
            fileChooser.addChoosableFileFilter(zipFilter);
            fileChooser.addChoosableFileFilter(docFilter);
            fileChooser.addChoosableFileFilter(wordFilter);
            fileChooser.addChoosableFileFilter(pdfFilter);
            fileChooser.addChoosableFileFilter(gzFilter);

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                System.out.print(file);

                try
                {
                    Decompressor.decompressFile(file);
                    JOptionPane.showMessageDialog(null, "Successfully Decompressed and Saved In Your Device.");
                }
                catch (Exception ee)
                {
                    JOptionPane.showMessageDialog(null, ee.toString());
                }
            }
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            new App_Frame("Compression & Decompression");
        });
    }
}

