package com.cometproject.server.game.navigator.types.featured;

import com.cometproject.server.game.GameEngine;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomWriter;
import com.cometproject.server.network.messages.types.Composer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeaturedRoom {
    private int id;
    private boolean isCategory;
    private BannerType bannerType;
    private String caption;
    private String description;
    private String image;
    private ImageType imageType;
    private int roomId;
    private int categoryId;
    private boolean enabled;
    private boolean recommended;

    private Room room;

    public FeaturedRoom(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.bannerType = BannerType.get(data.getString("banner_type"));
        this.caption = data.getString("caption");
        this.description = data.getString("description");
        this.image = data.getString("image");
        this.imageType = ImageType.get(data.getString("image_type"));
        this.roomId = data.getInt("room_id");
        this.categoryId = data.getInt("category_id");
        this.enabled = Boolean.parseBoolean(data.getString("enabled"));
        this.recommended = data.getString("recommended").equals("1");
        this.isCategory = data.getString("type").equals("category");

        // cache the room data so we dont have to get it every time we load the nav
        if(!isCategory) this.room = GameEngine.getRooms().get(roomId);
    }

    public void compose(Composer msg) {
        boolean isActive = (room != null && room.getEntities() != null);

        msg.writeInt(id);
        msg.writeString(caption);
        msg.writeString(description);
        msg.writeInt(bannerType == BannerType.BIG ? 0 : 1);
        msg.writeString(!isCategory ? caption : "");
        msg.writeString(imageType == ImageType.EXTERNAL ? image : "");
        msg.writeInt(categoryId);
        msg.writeInt(isActive ? room.getEntities().count() : 0);
        msg.writeInt(isCategory ? 4 : 2); // is room

        if(isCategory) {
            msg.writeBoolean(false);
        } else {
            RoomWriter.writeInfo(this.room, msg);
        }
    }

    public int getId() {
        return id;
    }

    public BannerType getBannerType() {
        return bannerType;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public boolean isCategory() {
        return this.isCategory;
    }
}
