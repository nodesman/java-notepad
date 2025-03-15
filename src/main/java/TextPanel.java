import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextPanel extends JTextArea {

    private Notepad notepad;

    public TextPanel() {
        super();
        // Enable word wrap by default for a Notepad-like experience
        setLineWrap(true);
        setWrapStyleWord(true);

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified();
            }

            private void setModified() {
                // Find the Notepad instance by traversing up the parent hierarchy
                if (notepad == null) {
                    findNotepadInstance();
                }

                if (notepad != null) {
                    notepad.setModified(true);
                    if (notepad.getTitle().indexOf("*") == -1) {
                         notepad.setTitle("*" + notepad.getTitle());
                    }
                }
            }

            private void findNotepadInstance() {
                java.awt.Container parent = getParent();
                while (parent != null) {
                    if (parent instanceof Notepad) {
                        notepad = (Notepad) parent;
                        break;
                    }
                    parent = parent.getParent();
                }
            }
        });
    }
    public void delete() {
        replaceSelection("");
    }
}