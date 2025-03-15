import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {

    private TextPanel textPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, helpMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem;
    private JMenuItem undoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, findMenuItem, findNextMenuItem, replaceMenuItem, goToMenuItem, selectAllMenuItem, timeDateMenuItem;
    private JMenuItem wordWrapMenuItem, fontMenuItem;
    private JMenuItem viewHelpMenuItem, aboutMenuItem;
    private JFileChooser fileChooser;
    private String currentFilePath;
    private boolean isModified;
    private UndoManager undoManager = new UndoManager();

    public Notepad() {
        setTitle("Untitled - Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        textPanel = new TextPanel();
        textPanel.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                updateUndoState();
            }
        });
        add(new JScrollPane(textPanel), BorderLayout.CENTER);

        createMenuBar();
        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        formatMenu = new JMenu("Format");
        helpMenu = new JMenu("Help");

        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open...");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As...");
        exitMenuItem = new JMenuItem("Exit");

        undoMenuItem = new JMenuItem("Undo");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        deleteMenuItem = new JMenuItem("Delete");
        findMenuItem = new JMenuItem("Find...");
        findNextMenuItem = new JMenuItem("Find Next");
        replaceMenuItem = new JMenuItem("Replace...");
        goToMenuItem = new JMenuItem("Go To...");
        selectAllMenuItem = new JMenuItem("Select All");
        timeDateMenuItem = new JMenuItem("Time/Date");

        wordWrapMenuItem = new JMenuItem("Word Wrap");
        fontMenuItem = new JMenuItem("Font...");

        viewHelpMenuItem = new JMenuItem("View Help");
        aboutMenuItem = new JMenuItem("About Notepad");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(undoMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(deleteMenuItem);
        editMenu.addSeparator();
        editMenu.add(findMenuItem);
        editMenu.add(findNextMenuItem);
        editMenu.add(replaceMenuItem);
        editMenu.add(goToMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);
        editMenu.add(timeDateMenuItem);

        formatMenu.add(wordWrapMenuItem);
        formatMenu.add(fontMenuItem);

        helpMenu.add(viewHelpMenuItem);
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        undoMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        deleteMenuItem.addActionListener(this);
        findMenuItem.addActionListener(this);
        findNextMenuItem.addActionListener(this);
        replaceMenuItem.addActionListener(this);
        goToMenuItem.addActionListener(this);
        selectAllMenuItem.addActionListener(this);
        timeDateMenuItem.addActionListener(this);

        wordWrapMenuItem.addActionListener(this);
        fontMenuItem.addActionListener(this);

        viewHelpMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);

        //Keyboard Shortcuts
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
        findMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        wordWrapMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
        timeDateMenuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
        findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke("F3"));

        updateUndoState();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                newFile();
                break;
            case "Open...":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save As...":
                saveFileAs();
                break;
            case "Exit":
                exitApp();
                break;
            case "Undo":
                undo();
                break;
            case "Cut":
                textPanel.cut();
                break;
            case "Copy":
                textPanel.copy();
                break;
            case "Paste":
                textPanel.paste();
                break;
            case "Delete":
                textPanel.delete();
                break;
            case "Find...":
                find();
                break;
            case "Find Next":
                findNext();
                break;
            case "Replace...":
                replace();
                break;
            case "Go To...":
                goTo();
                break;
            case "Select All":
                textPanel.selectAll();
                break;
            case "Time/Date":
                insertTimeDate();
                break;
            case "Word Wrap":
                toggleWordWrap();
                break;
            case "Font...":
                chooseFont();
                break;
            case "View Help":
                viewHelp();
                break;
            case "About Notepad":
                showAbout();
                break;
        }
    }

    private void newFile() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to save changes to " + getTitle() + "?",
                    "Notepad", JOptionPane.YES_NO_CANCEL_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        textPanel.setText("");
        setTitle("Untitled - Notepad");
        currentFilePath = null;
        isModified = false;
    }

    private void openFile() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to save changes to " + getTitle() + "?",
                    "Notepad", JOptionPane.YES_NO_CANCEL_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textPanel.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textPanel.append(line + "\n");
                }
                currentFilePath = file.getAbsolutePath();
                setTitle(file.getName() + " - Notepad");
                isModified = false;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFilePath == null) {
            saveFileAs();
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath))) {
                writer.write(textPanel.getText());
                isModified = false;
                setTitle(new File(currentFilePath).getName() + " - Notepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        int returnVal = fileChooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            currentFilePath = file.getAbsolutePath();
            saveFile();
        }
    }

    private void exitApp() {
        if (isModified) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you want to save changes to " + getTitle() + "?",
                    "Notepad", JOptionPane.YES_NO_CANCEL_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }

    private void undo() {
        try {
            undoManager.undo();
        } catch (Exception ex) {
            // Ignore, no more undos
        }
        updateUndoState();
    }

    private void updateUndoState() {
        undoMenuItem.setEnabled(undoManager.canUndo());
    }

    private void find() {
        String textToFind = JOptionPane.showInputDialog(this, "Enter text to find:", "Find", JOptionPane.PLAIN_MESSAGE);
        if (textToFind != null && !textToFind.isEmpty()) {
            // Basic find - could be improved with highlighting, etc.
            String text = textPanel.getText();
            int index = text.indexOf(textToFind);
            if (index != -1) {
                textPanel.setCaretPosition(index);
                textPanel.select(index, index + textToFind.length());
            } else {
                JOptionPane.showMessageDialog(this, "Text not found.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void findNext() {
        //Basic Implementation, need to store last search text and start index
        JOptionPane.showMessageDialog(this, "Find Next functionality not fully implemented.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void replace() {
        JOptionPane.showMessageDialog(this, "Replace functionality not fully implemented.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void goTo() {
        JOptionPane.showMessageDialog(this, "Go To functionality not fully implemented.", "Notepad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void insertTimeDate() {
        textPanel.insert(java.time.LocalDateTime.now().toString(), textPanel.getCaretPosition());
    }

    private void toggleWordWrap() {
        boolean wordWrap = !textPanel.getLineWrap();
        textPanel.setLineWrap(wordWrap);
        textPanel.setWrapStyleWord(wordWrap);
    }

    private void chooseFont() {
        Font currentFont = textPanel.getFont();
        FontChooser fontChooser = new FontChooser(this, currentFont);
        Font newFont = fontChooser.showDialog();
        if (newFont != null) {
            textPanel.setFont(newFont);
        }
    }

    private void viewHelp() {
        JOptionPane.showMessageDialog(this, "Help is not available at this time.", "Notepad Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this, "Simple Notepad in Java", "About Notepad", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public static void main(String[] args){
        new Notepad();
    }

}