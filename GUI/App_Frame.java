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

public class App_Frame extends JFrame implements ActionListener {
    JButton CompressButton;
    JButton DecompressButton;

    JLabel headingLabel;

    App_Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.getContentPane().setBackground(new Color(185, 158, 193)); // RGB(86, 125, 148) - AuroMetalSaurus
        this.setLayout(null);
        this.setTitle("Compress-DeCompress");

        // Create and format the heading label
        headingLabel = new JLabel("<html><u style='text-decoration: underline; text-decoration-style: Dotted ; text-decoration-color: rgb(79, 33, 100); font-weight: bold; font-size: 40px;'>Compress & DeCompress</u></html>");
        headingLabel.setBounds(0, 0, this.getWidth(), 100);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setFont(new Font("Footlight MT Light", Font.PLAIN | Font.ITALIC, 40));
        headingLabel.setForeground(new Color(75, 28, 113)); // RGB(79, 33, 100) - Custom Color

        CompressButton = new JButton("Select File To Compress");
        CompressButton.setBounds(200, 160, 375, 40);
        CompressButton.addActionListener(this);
        CompressButton.setBackground(new Color(179, 196, 164)); // RGB(229, 221, 215) - Light Grayish Magenta
        Border compressBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(113, 49, 141), 4, true), // Outer border - RGB(113, 49, 141) - Vivid Tangerine
                BorderFactory.createLineBorder(new Color(245, 218, 223), 3, true) // Inner border - RGB(113, 49, 141) - Vivid Tangerine
        );
        CompressButton.setBorder(compressBorder);
        CompressButton.setPressedIcon(new ImageIcon(getPressedButtonBackground()));

        DecompressButton = new JButton("Select File To Decompress");
        DecompressButton.setBounds(200, 300, 375, 45);
        DecompressButton.addActionListener(this);
        DecompressButton.setBackground(new Color(161, 176, 147)); // RGB(229, 221, 215) - Light Grayish Magenta
        Border decompressBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(113, 49, 141), 4, true), // Outer border - RGB(113, 49, 141) - Vivid Tangerine
                BorderFactory.createLineBorder(new Color(245, 218, 223), 3, true) // Inner border - RGB(113, 49, 141) - Vivid Tangerine
        );
        DecompressButton.setBorder(decompressBorder);
        DecompressButton.setPressedIcon(new ImageIcon(getPressedButtonBackground()));

        this.add(headingLabel);
        this.add(CompressButton);
        this.add(DecompressButton);

        CompressButton.setFont(new Font("Monotype Corsiva", Font.PLAIN | Font.ITALIC, 25));  // Font for CompressButton
        DecompressButton.setFont(new Font("Script MT Bold", Font.ITALIC, 25));  // Font for DecompressButton

        this.setVisible(true);
    }

    private Image getPressedButtonBackground() {
        int buttonWidth = CompressButton.getWidth();
        int buttonHeight = CompressButton.getHeight();
        BufferedImage image = new BufferedImage(buttonWidth, buttonHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(122, 211, 168));
        g2d.fillRect(0, 0, buttonWidth, buttonHeight);
        g2d.dispose();
        return image;
    }

    private String showFileTypeSelectionDialog() {
        Object[] options = {"PNG", "ZIP", "DOC", "WORD", "PDF"};
        int selection = JOptionPane.showOptionDialog(this, "Select file type to compress:", "File Type Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selection == -1) {
            return null;
        }
        return options[selection].toString().toLowerCase();
    }

    private int showQualitySelectionDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter quality range (1-10):");
        int quality = -1;
        try {
            quality = Integer.parseInt(input);
            int percentage = quality * 10; // Calculate the percentage
            JOptionPane.showMessageDialog(this, "Compression level selected: " + percentage + "%");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Quality range should be an integer between 1 and 10.");
        }
        return quality;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CompressButton) {
            String fileType = showFileTypeSelectionDialog();
            if (fileType != null) {
                JFileChooser fileChooser = new JFileChooser();

                // Add file filter
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(fileType.toUpperCase() + " Files", fileType);
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setFileFilter(fileFilter); // Set the default filter

                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    System.out.print(file);

                    int quality = showQualitySelectionDialog();
                    if (quality >= 1 && quality <= 10) {
                        try {
                            compressor.compressFile(file, quality);
                        } catch (Exception ee) {
                            JOptionPane.showMessageDialog(null, ee.toString());
                        }
                    }
                }
            }
        }
        if (e.getSource() == DecompressButton) {
            JFileChooser fileChooser = new JFileChooser();

            // Add file filters
            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG Files", "png");
            FileNameExtensionFilter zipFilter = new FileNameExtensionFilter("ZIP Files", "zip");
            FileNameExtensionFilter docFilter = new FileNameExtensionFilter("DOC Files", "doc");
            FileNameExtensionFilter wordFilter = new FileNameExtensionFilter("WORD Files", "word");
            FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");

            // Set the file filters
            fileChooser.addChoosableFileFilter(pngFilter);
            fileChooser.addChoosableFileFilter(zipFilter);
            fileChooser.addChoosableFileFilter(docFilter);
            fileChooser.addChoosableFileFilter(wordFilter);
            fileChooser.addChoosableFileFilter(pdfFilter);
            fileChooser.setFileFilter(pngFilter); // Set the default filter

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.print(file);

                try {
                    Decompressor.method(file);
                    JOptionPane.showMessageDialog(null, "Successfully Decompressed The File and Saved In You're Device.");
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, ee.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App_Frame();
            }
        });
    }
}
