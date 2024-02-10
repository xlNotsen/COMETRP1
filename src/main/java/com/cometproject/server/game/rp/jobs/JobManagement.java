package com.cometproject.server.game.rp.jobs;

import com.cometproject.server.boot.Comet;

import java.sql.ResultSet;

/**
 * Created by Matty on 05/04/2014.
 */
public class JobManagement {

    public void load() {
        /*try {
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

        log.info("Loaded " + this.taxiRooms.size() + " taxi rooms");*/
    }
}
