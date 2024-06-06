package ui;

public class Tile {
    private TileType type;
    private int age;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public void age() {
        if (this.type == TileType.FOREST) {
            this.age++;
            if (this.age > 3650) { // Assuming 10 years at a day per update cycle
                this.type = TileType.EMPTY; // Forest dies and becomes empty
            }
        }
    }
}
