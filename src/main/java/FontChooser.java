import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FontChooser extends JDialog implements ActionListener {

    private JLabel fontLabel, sizeLabel, styleLabel, previewLabel;
    private JComboBox<String> fontComboBox, sizeComboBox, styleComboBox;
    private JButton okButton, cancelButton;
    private Font selectedFont;

    public FontChooser(Frame parent, Font currentFont) {
        super(parent, "Font", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 5, 5));

        fontLabel = new JLabel("Font:");
        sizeLabel = new JLabel("Size:");
        styleLabel = new JLabel("Style:");
        previewLabel = new JLabel("AaBbYyZz", SwingConstants.CENTER);
        previewLabel.setPreferredSize(new Dimension(100, 50));  // Adjust size as needed
        previewLabel.setBorder(BorderFactory.createEtchedBorder());

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fontFamilies);

        String[] sizes = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"};
        sizeComboBox = new JComboBox<>(sizes);


        String[] styles = {"Plain", "Bold", "Italic", "Bold Italic"};
        styleComboBox = new JComboBox<>(styles);


        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        if (currentFont != null) {
            fontComboBox.setSelectedItem(currentFont.getFamily());
            sizeComboBox.setSelectedItem(String.valueOf(currentFont.getSize()));
            int style = currentFont.getStyle();
            if (style == Font.PLAIN) {
                styleComboBox.setSelectedIndex(0);
            } else if (style == Font.BOLD) {
                styleComboBox.setSelectedIndex(1);
            } else if (style == Font.ITALIC) {
                styleComboBox.setSelectedIndex(2);
            } else if (style == Font.BOLD + Font.ITALIC) {
                styleComboBox.setSelectedIndex(3);
            }
            updatePreview();
        }



        add(fontLabel);
        add(fontComboBox);
        add(sizeLabel);
        add(sizeComboBox);
        add(styleLabel);
        add(styleComboBox);
        add(new JLabel()); // Filler
        add(previewLabel);
        add(okButton);
        add(cancelButton);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        fontComboBox.addActionListener(this);
        sizeComboBox.addActionListener(this);
        styleComboBox.addActionListener(this);


        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            updatePreview(); // Ensure preview is updated before closing
            selectedFont = previewLabel.getFont();
            dispose();
        } else if (e.getSource() == cancelButton) {
            selectedFont = null;
            dispose();
        } else if(e.getSource() == fontComboBox || e.getSource() == sizeComboBox || e.getSource() == styleComboBox){
            updatePreview();
        }
    }
    private void updatePreview(){
        String fontFamily = (String) fontComboBox.getSelectedItem();
        String sizeString = (String) sizeComboBox.getSelectedItem();
        int fontSize = Integer.parseInt(sizeString);
        int fontStyle = getFontStyle();
        Font newFont = new Font(fontFamily, fontStyle, fontSize);
        previewLabel.setFont(newFont);

    }
    private int getFontStyle() {
        String style = (String)styleComboBox.getSelectedItem();

        switch (style) {
            case "Plain":
                return Font.PLAIN;

            case "Bold":
                return Font.BOLD;
            case "Italic":
                return Font.ITALIC;

            case "Bold Italic":
                return Font.BOLD | Font.ITALIC;
            default: return Font.PLAIN;
        }
    }



    public Font showDialog() {
        setVisible(true); // Modal dialog
        return selectedFont;
    }
}