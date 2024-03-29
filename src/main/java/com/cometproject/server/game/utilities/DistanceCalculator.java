package com.cometproject.server.game.utilities;

public class DistanceCalculator {
    public static int calculate(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        return Math.abs(pos1X - pos2X) + Math.abs(pos1Y - pos2Y);
    }

    public static boolean tilesTouching(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        if (!(Math.abs(pos1X - pos2X) > 1 || Math.abs(pos1Y - pos2Y) > 1)) {
            return true;
        }

        if (pos1X == pos2X && pos1Y == pos2Y) {
            return true;
        }

        return false;
    }
}
