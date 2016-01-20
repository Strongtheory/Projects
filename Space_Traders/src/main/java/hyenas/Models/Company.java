package hyenas.Models;

import java.util.Random;

/**
 *
 * @author JR
 */


public class Company {
    /**
     * The company name.
     */
    private String name;
    /**
     * The company's stock price.
     */
    private int price;
    /**
     * The total number of stocks the company has.
     */
    private int totalStocks;
    /**
     * The maximum number of stocks a company can have.
     */
    private static final int MAX_STOCK = 10000000;
    /**
     * The minimum number of stocks a company can have.
     */
    private static final int MIN_STOCK = 10;
    /**
     * The minimum price of stocks.
     */
    private static final int MIN_STOCK_PRICE = 1;
    /**
     * The minimum value a company needs to be to be listed on the stock market.
     */
    private static final int MIN_CAP = 1000000;
    /**
     * Amount of stocks of this company that the player owns. Awful OO design.
     */
    private int playerAmount;
    /**
     * Amount of available stocks of this company.
     */
    private int available;
    /**
     * Constructor for initializing company.
     * @param name this name of the planet
     * @param price the price per share of the company
     * @param totalStocks the number of total stocks of the company
     */
    public Company(String name, int price, int totalStocks) {
        this.name = name;
        this.price = price;
        this.totalStocks = totalStocks;
    }

    /**
     * Constructor for initializing company.
     * @param name this name of the planet
     */
    public Company(String name) {
        Random rand = new Random();
        int price = Math.abs(rand.nextInt(50)) + 1;
        int numStocks = rand.nextInt((MAX_STOCK - MIN_STOCK) + 1) + MIN_STOCK;
        
        while (numStocks * price < MIN_CAP) {
            price = Math.abs(rand.nextInt(50)) + 1;
            numStocks = rand.nextInt((MAX_STOCK - MIN_STOCK) + 1) + MIN_STOCK;
        }
        
        this.name = name;
        this.price = price;
        this.totalStocks = numStocks;
        this.playerAmount = 0;
        this.available = this.totalStocks;
    }
    
    /**
     * Get the company's name.
     * @return the name of the company
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the price for one share of the company.
     * @return the price for one share of the company
     */
    public int getPrice() {
        return this.price;
    }
    
    /**
     * Get the total amount stocks of the company.
     * @return the total amount stocks of the company.
     */
    public int getTotalStocks() {
        return this.totalStocks;
    }
    
    /**
     * Set the total amount stocks of the company that the player owns
     * @param amount the new amount
     */
    public void setPlayerAmount(int amount) {
        this.playerAmount = amount;
    }
    
    /**
     * Get the total amount stocks of the company that the player owns.
     * @return the amount that the player owns
     */
    public int getPlayerAmount() {
        return this.playerAmount;
    }
    
    /**
     * Set the available amount of stocks of the company.
     * @param amount the new amount
     */
    public void setAvailable(int amount) {
        this.available = amount;
    }
    
    /**
     * Get the available amount stocks of the company.
     * @return the number of available stocks of this company
     */
    public int getAvailable() {
        return this.available;
    }
    
    /**
     * Set the price for one share of the company.
     * @param newPrice the new price for one share of the company
     */
    public void setPrice(int newPrice) {
        this.price = newPrice;
    }
    
    /**
     * Calculate a random new price for one share of the company.
     * @return the new price per share
     */
    public int calculateNewPrice() {
        Random random = new Random();
        boolean goUp = random.nextBoolean();
        
        int newPrice = this.getPrice();
        int change;
        
        change = random.nextInt((int) Math.ceil((0.1 * this.getPrice())) + 1);
        change = goUp ? change : -1 * change;
        
        newPrice += change;
        
        if (newPrice < MIN_STOCK_PRICE) {
            newPrice = MIN_STOCK_PRICE;
        }
        
        return newPrice;
    }
}
