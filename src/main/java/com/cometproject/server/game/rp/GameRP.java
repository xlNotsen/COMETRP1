package com.cometproject.server.game.rp;

/**
 * Created by Matty on 04/04/2014.
 */
public class GameRP {
    private GameRPConfig config;
    private TaxiRoomManagement taxiRoomManagement;
    private ClothesStoreManagement clothesStoreManagement;


    public GameRP() {
        this.config = new GameRPConfig();
        this.taxiRoomManagement = new TaxiRoomManagement();
        this.clothesStoreManagement = new ClothesStoreManagement();

        this.init();
    }

    public void init() {
        this.taxiRoomManagement.load();
        this.clothesStoreManagement.load();
    }

    public GameRPConfig getConfig() {
        return this.config;
    }

    public TaxiRoomManagement getTaxiRoomManagement() {
        return this.taxiRoomManagement;
    }

    public ClothesStoreManagement getClothesStoreManagement() {
        return this.clothesStoreManagement;
    }
}
