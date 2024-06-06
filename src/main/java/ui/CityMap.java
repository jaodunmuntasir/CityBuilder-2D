package ui;

public class CityMap {
    private Tile[][] grid;
    private int width;
    private int height;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Tile[height][width];

        // Initialize the grid with empty tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Tile(TileType.EMPTY);
            }
        }
    }

    public Tile getTile(int x, int y) {
        return grid[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTile(int x, int y, TileType type) {
        grid[y][x].setType(type);
    }

    public void clearTile(int x, int y) {
        grid[y][x].setType(TileType.EMPTY);
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x].setType(TileType.EMPTY);
            }
        }
    }

    // Method to check if coordinates are within the map bounds
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
