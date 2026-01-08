package com.mycompany.mavenproject1;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Clock implements Runnable {
    private final JLabel clockLabel;
    private Thread clockThread;
    private volatile boolean running = false;

    // Formatter for 12-hour time with AM/PM
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a");

    public Clock() {
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        clockLabel.setForeground(Color.BLACK);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        startClock();
    }

    private void startClock() {
        running = true;
        clockThread = new Thread(this, "Clock-Thread");
        clockThread.start();
    }

    @Override
    public void run() {
        while (running) {
            // Get current time truncated to seconds
            LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

            // Format to 12-hour with AM/PM
            String formattedTime = time.format(timeFormatter);

            // Update the label on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> clockLabel.setText(formattedTime));

            try {
                Thread.sleep(1000); // 1-second update
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopClock() {
        running = false;
        if (clockThread != null) {
            clockThread.interrupt();
        }
    }

    public JLabel getLabel() {
        return clockLabel;
    }
}
