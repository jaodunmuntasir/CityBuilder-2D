package ui;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum TileType {
    EMPTY(Color.LIGHT_GRAY, "assets/empty.jpg"),
    ROAD(Color.DARK_GRAY, "assets/road.png"),
    RESIDENTIAL(Color.GREEN, "assets/residential.png"),
    COMMERCIAL(Color.BLUE, "assets/commercial.png"),
    INDUSTRIAL(Color.ORANGE, "assets/industrial.png"),
    POLICE_STATION(Color.BLUE, "assets/police_station.png"),
    FIRE_STATION(Color.RED, "assets/fire_station.png"),
    HOSPITAL(Color.WHITE, "assets/hospital.jpg"),
    SCHOOL(Color.YELLOW, "assets/school.jpg"),
    POWER_PLANT(Color.BLACK, "assets/power_plant.png"),
    WATER_TOWER(Color.CYAN, "assets/water_tower.png"),
    DEMOLISH(Color.GRAY, "assets/empty.jpg"),
    FIRE(Color.RED, "assets/fire.png"),
    METEOR(Color.RED, "assets/meteor.png"),
    CHEMICAL(Color.RED, "assets/chemical.jpg"),
    GODZILLA(Color.RED, "assets/godzilla.png"),
    FOREST(Color.GREEN, "assets/forest.png");

    private final Color drawColor;
    private final String imagePath;
    private Image image;

    TileType(Color drawColor, String imagePath) {
        this.drawColor = drawColor;
        this.imagePath = imagePath;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
            if (image == null) {
                throw new IllegalArgumentException("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getDrawColor() {
        return drawColor;
    }

    public Image getImage() {
        return image;
    }
}