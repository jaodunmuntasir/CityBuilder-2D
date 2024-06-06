package database;

import ui.CityMap;
import features.LoanManager;
import config.GameTime;

public class GameState {
    private int population;
    private int taxRate;
    private CityMap cityMap;
    private LoanManager loanManager;
    private GameTime gameTime;

    public GameState(int population, int taxRate, CityMap cityMap, LoanManager loanManager, GameTime gameTime) {
        this.population = population;
        this.taxRate = taxRate;
        this.cityMap = cityMap;
        this.loanManager = loanManager;
        this.gameTime = gameTime;
    }

    public int getPopulation() {
        return population;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public CityMap getCityMap() {
        return cityMap;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }

    public GameTime getGameTime() {
        return gameTime;
    }
}