package studentcomplaintmanagementsystem.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomTitleBar extends JPanel {
    private Point initialClick;
    private final JFrame parent;

    public CustomTitleBar(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(0, 30));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);

        Color hoverColor = new Color(75, 85, 99, 150);
        Color closeHoverColor = new Color(220, 38, 38, 220);

        JButton btnMinimize = createTitleButton("_", hoverColor);
        btnMinimize.addActionListener(e -> parent.setState(Frame.ICONIFIED));

        JButton btnMaximize = createTitleButton("[]", hoverColor);
        btnMaximize.addActionListener(e -> {
            if (parent.getExtendedState() == Frame.MAXIMIZED_BOTH) {
                parent.setExtendedState(Frame.NORMAL);
            } else {
                parent.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });

        JButton btnClose = createTitleButton("X", closeHoverColor);
        btnClose.addActionListener(e -> parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING)));

        buttonPanel.add(btnMinimize);
        if (parent.isResizable()) {
            buttonPanel.add(btnMaximize);
        }
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.EAST);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        };
        addMouseListener(mouseAdapter);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (parent.getExtendedState() == Frame.MAXIMIZED_BOTH) {
                    return; // Don't drag when maximized
                }
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }

    private JButton createTitleButton(String text, Color hoverBg) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(45, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setOpaque(false);
                button.setContentAreaFilled(false);
            }
        });
        return button;
    }
}