/*
 * Decompiled with CFR 0.152.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.benf.cfr.reader.Main;

public class MirrorDecompiler {
    JTextPane textPane = new JTextPane();

    public static void main(String[] stringArray) {
        SwingUtilities.invokeLater(MirrorDecompiler::new);
    }

    public MirrorDecompiler() {
        JFrame jFrame = new JFrame("\ubbf8\ub7ec\ub514\ucef4\ud30c\uc77c\ub7ec");
        jFrame.setDefaultCloseOperation(3);
        jFrame.setSize(600, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(0));
        JButton jButton = new JButton("\uc5f4\uae30");
        JButton jButton2 = new JButton("\uc815\ubcf4");
        JButton jButton3 = new JButton("\uc885\ub8cc");
        jButton.addActionListener(actionEvent -> {
            try {
                String string;
                File file = new File("path.txt");
                if (file.exists()) {
                    string = new String(Files.readAllBytes(file.toPath())).trim();
                    if (string.isEmpty()) {
                        string = System.getProperty("user.dir");
                    }
                } else {
                    string = System.getProperty("user.dir");
                    Files.write(file.toPath(), string.getBytes(), new OpenOption[0]);
                }
                JFileChooser jFileChooser = new JFileChooser(string);
                jFileChooser.setFileSelectionMode(0);
                int n = jFileChooser.showOpenDialog(jFrame);
                if (n == 0) {
                    File file2 = jFileChooser.getSelectedFile();
                    String string2 = file2.getParent();
                    Files.write(file.toPath(), string2.getBytes(), new OpenOption[0]);
                    String string3 = this.decompileWithCFR(file2.getAbsolutePath());
                    this.textPane.setText(string3);
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        jButton2.addActionListener(actionEvent2 -> {
            JDialog jDialog = new JDialog(jFrame, "\uc815\ubcf4", true);
            jDialog.setSize(300, 300);
            jDialog.setLayout(new BorderLayout());
            JTextArea jTextArea = new JTextArea();
            jTextArea.setFont(new Font("Serif", 0, 14));
            jTextArea.append("\ud14c\uc2a4\ud2b8 1\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 2\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 3\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 4\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 5\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 6\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 7\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 8\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 9\n");
            jTextArea.append("\ud14c\uc2a4\ud2b8 10\n");
            JScrollPane jScrollPane = new JScrollPane(jTextArea);
            JButton jButton = new JButton("\ud655\uc778");
            jButton.addActionListener(actionEvent -> jDialog.dispose());
            jDialog.add((Component)jScrollPane, "Center");
            jDialog.add((Component)jButton, "South");
            jDialog.setLocationRelativeTo(jFrame);
            jDialog.setVisible(true);
        });
        jButton3.addActionListener(actionEvent -> System.exit(0));
        jPanel.add(jButton);
        jPanel.add(jButton2);
        jPanel.add(jButton3);
        JScrollPane jScrollPane = new JScrollPane(this.textPane, 20, 30);
        final JPanel jPanel2 = new JPanel(){

            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.setColor(Color.GRAY);
                int n = graphics.getFontMetrics().getHeight();
                boolean bl = false;
                int n2 = MirrorDecompiler.this.textPane.getDocument().getDefaultRootElement().getElementCount();
                for (int i = 0; i < n2; ++i) {
                    int n3 = (i + 1) * n;
                    graphics.drawString(String.valueOf(i + 1), 5, n3);
                }
            }
        };
        jPanel2.setPreferredSize(new Dimension(40, 0));
        jScrollPane.setRowHeaderView(jPanel2);
        this.textPane.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                this.onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                this.onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                this.onTextChanged();
            }

            private void onTextChanged() {
                int n = jPanel2.getFontMetrics(jPanel2.getFont()).getHeight();
                int n2 = MirrorDecompiler.this.textPane.getDocument().getDefaultRootElement().getElementCount();
                jPanel2.setPreferredSize(new Dimension(40, n2 * n));
                jPanel2.revalidate();
                jPanel2.repaint();
            }
        });
        JPanel jPanel3 = new JPanel(new BorderLayout());
        jPanel3.add((Component)jScrollPane, "Center");
        jFrame.add((Component)jPanel, "North");
        jFrame.add((Component)jPanel3, "Center");
        jFrame.setVisible(true);
    }

    public String decompileWithCFR(String string) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            PrintStream printStream2 = System.out;
            System.setOut(printStream);
            Main.main(new String[]{string, "--outputdir", "tmp_decompile"});
            System.setOut(printStream2);
            return byteArrayOutputStream.toString("UTF-8");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "\ub514\ucef4\ud30c\uc77c \uc624\ub958 \ubc1c\uc0dd";
        }
    }
}
