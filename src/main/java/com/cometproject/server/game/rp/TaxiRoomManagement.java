package com.cometproject.server.game.rp;

import com.cometproject.server.boot.Comet;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by Matty on 04/04/2014.
 */
public class TaxiRoomManagement {
    Logger log = Logger.getLogger(TaxiRoomManagement.class.getName());

    private Map<Integer, Integer> taxiRooms = new FastMap<>();

    public TaxiRoomManagement() {

    }

    public void load() {
        try {
            if(this.taxiRooms.size() != 0) {
                this.taxiRooms.clear();
            }

            ResultSet result = Comet.getServer().getStorage().getTable("SELECT * FROM taxi_rooms WHERE enabled = '1'");

            if(result == null) {
                return;
            }

            while(result.next()) {
                this.taxiRooms.put(result.getInt("taxi_id"), result.getInt("room_id"));
            }
        } catch(Exception e) {
            log.error("Error while loading taxi rooms", e);
        }

        log.info("Loaded " + this.taxiRooms.size() + " taxi rooms");
    }

    public Integer get(int taxiId) {
        return this.taxiRooms.get(taxiId);
    }

    public boolean exists(int taxiId) {
        return this.taxiRooms.containsKey(taxiId);
    }
}
