/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class MirrorDecompiler.1
extends JPanel {
    MirrorDecompiler.1() {
    }

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
}
