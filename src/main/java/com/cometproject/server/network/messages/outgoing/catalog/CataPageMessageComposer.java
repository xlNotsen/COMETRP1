package com.cometproject.server.network.messages.outgoing.catalog;

import com.cometproject.server.config.CometSettings;
import com.cometproject.server.game.GameEngine;
import com.cometproject.server.game.catalog.types.CatalogItem;
import com.cometproject.server.game.catalog.types.CatalogPage;
import com.cometproject.server.game.items.types.ItemDefinition;
import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;

public class CataPageMessageComposer {
    public static Composer compose(CatalogPage page) {
        Composer msg = new Composer(Composers.CataPageMessageComposer);
        msg.writeInt(page.getId());

        // TODO: Make better catalog system!!

        if(page.getTemplate().equals("frontpage")) {
            msg.writeString("frontpage3");
            msg.writeInt(3);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeString("");
            msg.writeInt(11);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageText2());
            msg.writeString("");
            msg.writeString("How to get " + CometSettings.hotelName.split(" ")[0] + " Credits");
            msg.writeString("You can get " + CometSettings.hotelName.split(" ")[0] + " Credits via Prepaid Cards, Home Phone, Credit Card, Mobile, completing offers and more! "
                    + (char)13 + (char)10 + (char)13 + (char)10 + "To redeem your " + CometSettings.hotelName.split(" ")[0] + " Credits, enter your voucher code below.");

            msg.writeString(page.getPageTextDetails());
            msg.writeString("");
            msg.writeString("#FEFEFE");
            msg.writeString("#FEFEFE");
            msg.writeString("Other ways to get credits >");
            msg.writeString("credits");

        } else if(page.getTemplate().equals("spaces_new")) {
            msg.writeString("spaces_new");
            msg.writeInt(1);
            msg.writeString(page.getHeadline());
            msg.writeInt(1);
            msg.writeString(page.getPageText1());

        } else if(page.getTemplate().equals("trophies")) {
            msg.writeString("trophies");
            msg.writeInt(1);
            msg.writeString(page.getHeadline());
            msg.writeInt(2);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageTextDetails());

        } else if(page.getTemplate().equals("pets")) {
            msg.writeString("pets");
            msg.writeInt(2);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeInt(4);
            msg.writeString(page.getPageText1());
            msg.writeString("Give a name:");
            msg.writeString("Pick a colour:");
            msg.writeString("Pick a race:");

        } else if(page.getTemplate().equals("guild_frontpage")) {
            msg.writeString("guild_frontpage");
            msg.writeInt(2);
            msg.writeString("catalog_groups_en");
            msg.writeString("");
            msg.writeInt(3);
            msg.writeString(CometSettings.hotelName + " Groups are a great way to stay in touch with your friends and share your interests with others. Each Group has a homeroom that can be decorated by other Group members, members can also purchase exclusive Group Furni that can be customised with your Group colours!");
            msg.writeString("* Co-op room decorating for group members\n* Show off your group badge!\n* Get some neat Furni in your group's colors!");
            msg.writeString("What's so great about " + CometSettings.hotelName + " Groups?");
        } else if(page.getTemplate().equals("club_buy")) {
            // TODO: buy HC
        } else {
            msg.writeString(page.getTemplate());
            msg.writeInt(3);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeString(page.getSpecial());
            msg.writeInt(3);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageTextDetails());
            msg.writeString(page.getPageText2());
        }

        if(!page.getTemplate().equals("frontpage") && !page.getTemplate().equals("club_buy") && !page.getTemplate().equals("badge_display")) {
            msg.writeInt(page.getItems().size());

            for(CatalogItem item : page.getItems().values()) {
                msg.writeInt(item.getId());
                msg.writeString(item.getDisplayName());
                msg.writeInt(item.getCostCredits());
                msg.writeInt(item.getCostOther());

                if(item.getCostOther() != 0)
                    msg.writeInt(105); // currency type :: diamonds
                else
                    msg.writeInt(0);

                msg.writeBoolean(true);

                if(!item.hasBadge()) {
                    msg.writeInt(item.getItems().size());
                } else {
                    msg.writeInt(item.getItems().size() + 1);
                    msg.writeString("b");
                    msg.writeString(item.getBadgeId());
                }

                for(int i : item.getItems()) {
                    ItemDefinition def = GameEngine.getItems().getDefintion(i);
                    msg.writeString(def.getType());
                    msg.writeInt(def.getSpriteId());

                    //if(page.getTemplate().equals("spaces")) {
                    if(item.getDisplayName().contains("wallpaper_single") || item.getDisplayName().contains("floor_single") || item.getDisplayName().contains("landscape_single")) {
                        msg.writeString(item.getDisplayName().split("_")[2]);
                    } else {
                        //msg.writeString(def.getItemName());
                        msg.writeString(""); // Not sure yet...
                    }

                    msg.writeInt(item.getAmount());

                    if(item.getLimitedTotal() == 0)
                        msg.writeInt(0);
                }

                msg.writeBoolean(item.getLimitedTotal() != 0);

                if(item.getLimitedTotal() > 0) {
                    msg.writeInt(item.getLimitedTotal());
                    msg.writeInt(item.getLimitedTotal() - item.getLimitedSells());
                    msg.writeInt(0);
                }

                msg.writeBoolean(!(item.getLimitedTotal() > 0) && item.allowOffer());
            }
        } else {
            msg.writeInt(0);
        }

        msg.writeInt(-1);
        msg.writeBoolean(false);

        return msg;
    }
}
