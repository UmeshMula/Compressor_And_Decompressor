package GUI;

import comp_decomp.Decompressor;
import comp_decomp.compressor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class App_Frame extends JFrame implements ActionListener {
    JButton CompressButton;
    JButton DecompressButton;

    App_Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 500);
        this.getContentPane().setBackground(new Color(185, 158, 193)); // RGB(86, 125, 148) - AuroMetalSaurus
        this.setLayout(null);

        // Create and format the heading label
        JLabel headingLabel = new JLabel("<html><u style='text-decoration: underline; text-decoration-style: Dotted ; text-decoration-color: rgb(79, 33, 100); font-weight: bold;'>Compress & DeCompress</u></html>");
        headingLabel.setBounds(0, 20, this.getWidth(), 100);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font headingFont = new Font("Footlight MT Light", Font.PLAIN | Font.ITALIC , 40);
        headingLabel.setFont(headingFont);
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

        Font buttonFont1 = new Font("Monotype Corsiva", Font.PLAIN |  Font.ITALIC, 25);  // Font for CompressButton
        Font buttonFont2 = new Font("Script MT Bold", Font.ITALIC, 25);  // Font for DecompressButton
        CompressButton.setFont(buttonFont1);
        DecompressButton.setFont(buttonFont2);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CompressButton) {
            JFileChooser filechooser = new JFileChooser();
            int response = filechooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                System.out.print(file);

                try {
                    compressor.method(file);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, ee.toString());
                }
            }
        }
        if (e.getSource() == DecompressButton) {
            JFileChooser filechooser = new JFileChooser();
            int response = filechooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                System.out.print(file);
                try {
                    Decompressor.method(file);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, ee.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App_Frame());
    }
}

