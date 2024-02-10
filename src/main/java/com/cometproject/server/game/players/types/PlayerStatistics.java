package com.cometproject.server.game.players.types;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.GameEngine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatistics {
    private int userId;
    private int achievementPoints;
    private int dailyRespects;
    private int respectPoints;
    private int health;
    private int energy;
    private int xp;
    private int jailTimeExpires;
    private int level;

    public PlayerStatistics(ResultSet data) throws SQLException {
        this.userId = data.getInt("player_id");
        this.achievementPoints = data.getInt("achievement_score");
        this.dailyRespects = data.getInt("daily_respects");
        this.respectPoints = data.getInt("total_respect_points");
        this.health = data.getInt("health");
        this.energy = data.getInt("energy");
        this.xp = data.getInt("xp");
        this.jailTimeExpires = data.getInt("jail_time_expires");
        this.level = data.getInt("level");
    }

    public PlayerStatistics(int userId) {
        this.userId = userId;
        this.achievementPoints = 0;
        this.respectPoints = 0;
        this.dailyRespects = 3;
        this.health = 100;
        this.energy = 100;
        this.xp = 0;
        this.jailTimeExpires = 0;
    }

    public void save() {
        try {
            PreparedStatement statement = Comet.getServer().getStorage().prepare("UPDATE player_stats SET achievement_score = ?, total_respect_points = ?, daily_respects = ?, health = ?, energy = ?, xp = ?, jail_time_expires = ?, level = ? WHERE player_id = " + this.userId);

            statement.setInt(1, achievementPoints);
            statement.setInt(2, respectPoints);
            statement.setInt(3, dailyRespects);
            statement.setInt(4, health);
            statement.setInt(5, energy);
            statement.setInt(6, xp);
            statement.setInt(7, jailTimeExpires);
            statement.setInt(8, level);

            statement.execute();
        } catch(SQLException e) {
            GameEngine.getLogger().error("Error while saving player statistics", e);
        }
     }

    public void incrementAchievementPoints(int amount) {
        this.achievementPoints += amount;
        this.save();
    }

    public void incrementRespectPoints(int amount) {
        this.respectPoints += amount;
        this.save();
    }

    public void decrementDailyRespects(int amount) {
        this.dailyRespects -= amount;
        this.save();
    }

    public int getDailyRespects() {
        return this.dailyRespects;
    }

    public int getRespectPoints() {
        return this.dailyRespects;
    }

    public int getAchievementPoints() {
        return this.achievementPoints;
    }

    public int getHealth() {
        return health;
    }

    public void increaseHealth(int amount) {
        this.health += amount;
        this.save();
    }

    public void decreaseHealth(int amount) {
        this.health -= amount;
        this.save();
    }

    public int getEnergy() {
        return energy;
    }

    public void increaseEnergy(int amount) {
        this.energy += amount;
        this.save();
    }

    public void decreaseEnergy(int amount) {
        this.energy -= amount;
        this.save();
    }

    public int getXp() {
        return xp;
    }

    public void increaseXp(int amount) {
        this.xp += amount;
        this.save();
    }

    public void decreaseXp(int amount) {
        this.xp -= amount;
        this.save();
    }

    public int getJailTimeExpires() {
        return jailTimeExpires;
    }

    public void setJailTimeExpires(int jailTimeExpires) {
        this.jailTimeExpires = jailTimeExpires;
        this.save();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.save();
    }
}
