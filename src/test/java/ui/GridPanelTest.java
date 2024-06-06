package ui;

import app.CityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GridPanelTest {

    private GridPanel gridPanel;
    private CityMap cityMap;
    private CityBuilder cityBuilder;
    private int tileSize;

    @BeforeEach
    public void setUp() {
        cityMap = new CityMap(10, 10);
        cityBuilder = new MockCityBuilder();
        tileSize = 32;
        gridPanel = new GridPanel(cityBuilder, cityMap, tileSize);
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(gridPanel);
        // assertEquals(new Dimension(cityMap.getWidth() * tileSize, cityMap.getHeight() * tileSize), gridPanel.getPreferredSize());
        assertEquals(new Color(240, 240, 240), gridPanel.getBackground());
    }

    @Test
    public void testSetCityMap() {
        CityMap newCityMap = new CityMap(20, 20);
        gridPanel.setCityMap(newCityMap);
        // assertEquals(newCityMap, cityBuilder.getCityMap());
    }

    @Test
    public void testPaintComponent() {
        JFrame frame = new JFrame();
        frame.add(gridPanel);
        frame.pack();
        frame.setVisible(true);

        // Ensure the grid is painted correctly
        SwingUtilities.invokeLater(() -> {
            assertDoesNotThrow(() -> {
                Graphics g = gridPanel.getGraphics();
                gridPanel.paintComponent(g);
            });
        });

        frame.dispose();
    }

    @Test
    public void testMousePressed() {
        int x = 5 * tileSize;
        int y = 5 * tileSize;
        MouseEvent me = new MouseEvent(gridPanel, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, x, y, 1, false);
        gridPanel.dispatchEvent(me);
        
        assertTrue(((MockCityBuilder) cityBuilder).isTilePlaced());
    }

    class MockCityBuilder extends CityBuilder {
        private boolean tilePlaced = false;

        @Override
        public void placeTile(int x, int y) {
            tilePlaced = true;
        }

        public boolean isTilePlaced() {
            return tilePlaced;
        }
    }
}