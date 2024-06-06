package ui;

import javax.swing.*;

import app.CityBuilder;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {
    private CityMap cityMap;
    // private int tileSize;

    public GridPanel(CityBuilder cityBuilder, CityMap cityMap, int tileSize) {
        this.cityMap = cityMap;
        // this.tileSize = tileSize;
        setPreferredSize(new Dimension(cityMap.getWidth() * tileSize, cityMap.getHeight() * tileSize));
        setBackground(new Color(240, 240, 240)); // Light grey background

        // Add mouse listener for tile placement
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cityBuilder.placeTile(e.getX(), e.getY());
            }
        });

        // Add component listener to handle resize events
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrid(g);
    }

    private void paintGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tileSize = calculateTileSize();
        int numCols = getWidth() / tileSize;
        int numRows = getHeight() / tileSize;

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (x < cityMap.getWidth() && y < cityMap.getHeight()) {
                    Tile tile = cityMap.getTile(x, y);
                    TileType type = tile.getType();

                    // Draw tile background with image or color
                    if (type.getImage() != null) {
                        g2d.drawImage(type.getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null);
                    } else {
                        g2d.setColor(type.getDrawColor());
                        g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }

                    // Add a subtle shadow effect
                    g2d.setColor(new Color(0, 0, 0, 30));
                    g2d.fillRect(x * tileSize + 3, y * tileSize + 3, tileSize - 6, tileSize - 6);
                } else {
                    // Draw empty tiles
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
    }

    private int calculateTileSize() {
        int width = getWidth();
        int height = getHeight();
        int cols = cityMap.getWidth();
        int rows = cityMap.getHeight();
        return Math.min(width / cols, height / rows);
    }

    @Override
    public Dimension getPreferredSize() {
        // Make the panel resize with the window
        return new Dimension(getWidth(), getHeight());
    }

    public void setCityMap(CityMap cityMap) {
        this.cityMap = cityMap;
        revalidate(); // Ensure the panel is validated
        repaint(); // Repaint the grid to reflect the new city map
    }
}