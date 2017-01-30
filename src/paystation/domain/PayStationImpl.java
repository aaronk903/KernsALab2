package paystation.domain;

import java.util.*;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private int moneyEarned;
    public static Map<Integer, Integer> coinMap = createCoinMap();
    
    private static Map<Integer, Integer> createCoinMap() {
         Map<Integer,Integer> coinsInserted = new HashMap<>();
         return coinsInserted;
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: 
                if(coinMap.containsKey(5)){
                    coinMap.replace(5, coinMap.get(5) + 1);
                } else {
                    coinMap.put(5, 1);
                }
                break;
            case 10: 
                if(coinMap.containsKey(10)){
                    coinMap.replace(10, coinMap.get(10) + 1);
                } else {
                    coinMap.put(10, 1);
                }
                break;
            case 25: 
                if(coinMap.containsKey(25)){
                    coinMap.replace(25, coinMap.get(25) + 1);
                } else {
                    coinMap.put(25, 1);
                }
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        moneyEarned += insertedSoFar;
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer,Integer> clonedCoinMap 
                = new HashMap<>(coinMap);
        reset();
        return clonedCoinMap;
    }
    
    @Override
    public int empty() {
        int totalSinceEmpty = moneyEarned;
        moneyEarned = 0;
        return totalSinceEmpty;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        resetCoinMap();
    }
    
    private void resetCoinMap() {
        coinMap = createCoinMap();
    }
}
