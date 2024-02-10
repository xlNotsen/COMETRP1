package com.cometproject.server.game.players.data;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.game.players.types.PlayerStatistics;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerLoader {
    private static Logger log = Logger.getLogger(PlayerLoader.class.getName());

    public static Player loadPlayerBySSo(String ticket) {
        try {
            PreparedStatement statement = Comet.getServer().getStorage().prepare("SELECT * FROM players WHERE auth_ticket = ?");

            statement.setString(1, ticket);
            ResultSet data = statement.executeQuery();

            return PlayerLoader.resultToPlayer(data);
        } catch (SQLException e) {
            log.error("Error while loading player data", e);
        }

        return null;
    }

    public static PlayerSettings loadSettings(int id) {
        try {
            ResultSet result = Comet.getServer().getStorage().getRow("SELECT * FROM player_settings WHERE player_id = " + id);
            return new PlayerSettings(result);
        } catch(Exception e) {
            if(e instanceof NullPointerException) {
                Comet.getServer().getStorage().execute("INSERT into player_settings (`player_id`) VALUES(" + id + ")");
            }
            else {
                log.error("Error while loading player settings", e);
            }
        }

        return new PlayerSettings();
    }

    public static PlayerStatistics loadStatistics(int id) {
        try {
            ResultSet result = Comet.getServer().getStorage().getRow("SELECT * FROM player_stats WHERE player_id = " + id);
            return new PlayerStatistics(result);
        } catch(Exception e) {
            if(e instanceof NullPointerException) {
                Comet.getServer().getStorage().execute("INSERT into player_stats (`player_id`) VALUES(" + id + ")");
            }
            else {
                log.error("Error while loading player statistics", e);
            }
        }

        return new PlayerStatistics(id);
    }


    public static Player resultToPlayer(ResultSet result) throws SQLException {
        if(result == null) {
            return null;
        }

        if(!result.next()) {
            return null;
        }

        return new Player(result.getInt("id"));
    }

    public static PlayerData loadDataById(int id) {
        try {
            ResultSet result = Comet.getServer().getStorage().getRow("SELECT * FROM players WHERE id = " + id);
            return new PlayerData(result.getInt("id"), result.getString("username"), result.getString("motto"), result.getString("figure"), result.getString("gender"), result.getInt("rank"), result.getInt("credits"), result.getInt("vip_points"), result.getString("reg_date"), result.getInt("last_online"), result.getString("vip").equals("1"));
        } catch (SQLException e) {
            log.error("Error while loading player data", e);
        }

        return null;
    }
}