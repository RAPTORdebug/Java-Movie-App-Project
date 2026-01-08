package com.mycompany.mavenproject1;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class RoundedButton extends JButton {

    private int radius = 20; // how round the corners should be

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(52, 152, 219)); // light blue
        setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // background color
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // text color and font
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = getBounds();
        int textX = (r.width - fm.stringWidth(getText())) / 2;
        int textY = (r.height + fm.getAscent()) / 2 - 2;
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // no border by default, but you can customize
    }
}
