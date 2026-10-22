/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Graphics;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Clock_Time
extends JPanel
implements Runnable {
    JFrame frame = new JFrame();
    GregorianCalendar time;
    private int width = 330;
    int hour;
    int min;
    int sec;
    Date all_time;
    int i;
    int num;

    Clock_Time() {
        this.frame.setTitle("\uc790\ubc14\ub85c \uc2dc\uacc4 \ub9cc\ub4e4\uae30");
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(this.width, this.width);
        this.frame.setLocation(100, 100);
        this.frame.setVisible(true);
        this.frame.add(this);
        this.all_time = new Date();
    }

    @Override
    public void paint(Graphics graphics) {
        this.time = new GregorianCalendar();
        this.hour = this.time.get(10);
        this.min = this.time.get(12);
        this.sec = this.time.get(13);
        this.all_time = this.time.getTime();
        if (this.sec == 60) {
            this.sec = 0;
            ++this.min;
        }
        if (this.min == 60) {
            this.min = 0;
            ++this.hour;
        }
        if (this.hour == 12 && this.min == 60) {
            this.hour = 0;
        }
        graphics.clearRect(0, 0, this.width, this.width);
        this.rec_draw(graphics);
        this.draw(150, 150, 150, 50, 100, this.sec * 6, graphics);
        this.draw(150, 150, 150, 50, 70, this.min * 6, graphics);
        this.draw(150, 150, 150, 50, 50, this.hour * 30 + this.min / 2, graphics);
    }

    public void rec_draw(Graphics graphics) {
        int n = 1;
        for (int i = 1; i <= 60; ++i) {
            if (i % 5 == 0) {
                this.draw_t(150, 50, i * 6, 3, graphics);
                this.draw_time(150, 50, i * 6, n + "", graphics);
                ++n;
                continue;
            }
            this.draw_t(150, 50, i * 6, 1, graphics);
        }
    }

    public void draw_t(int n, int n2, int n3, int n4, Graphics graphics) {
        n = 150 + (int)(120.0 * Math.sin((double)n3 * Math.PI / 180.0));
        n2 = 150 - (int)(120.0 * Math.cos((double)n3 * Math.PI / 180.0));
        graphics.fillRect(n, n2, n4, n4);
    }

    public void draw_time(int n, int n2, int n3, String string, Graphics graphics) {
        n = 147 + (int)(105.0 * Math.sin((double)n3 * Math.PI / 180.0));
        n2 = 155 - (int)(100.0 * Math.cos((double)n3 * Math.PI / 180.0));
        graphics.drawString(string, n, n2);
    }

    public void draw(int n, int n2, int n3, int n4, int n5, int n6, Graphics graphics) {
        n3 = n + (int)((double)n5 * Math.sin((double)n6 * Math.PI / 180.0));
        n4 = n2 - (int)((double)n5 * Math.cos((double)n6 * Math.PI / 180.0));
        graphics.drawLine(n, n2, n3, n4);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            this.repaint();
        }
    }

    public static void main(String[] stringArray) {
        Clock_Time clock_Time = new Clock_Time();
        Thread thread = new Thread(clock_Time);
        thread.start();
    }
}
