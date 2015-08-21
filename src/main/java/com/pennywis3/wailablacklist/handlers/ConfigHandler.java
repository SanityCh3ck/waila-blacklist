package com.pennywis3.wailablacklist.handlers;

import com.pennywis3.wailablacklist.utils.StringHelper;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by Pennywis3 on 19.08.2015.
 */
public class ConfigHandler {
    private static String initialState = "";

    public static TreeSet<String> blockSet = new TreeSet<String>();
    public static boolean blockWhitelist = false;

    public static int[] dimList = new int[0];
    public static boolean dimWhitelist = false;

    public static TreeSet<String> entitySet = new TreeSet<String>();
    public static boolean entityWhitelist = false;
    public static boolean entityHideInvisible = true;

    public static void initConfig(File configFile){
        String blockListComment = "List of block names";
        String blockWhitelistComment = "Is block list a whitelist?";

        String dimListComment = "List of dimension IDs";
        String dimWhitelistComment = "Is dimension list a whitelist?";

        String entityListComment = "List of entity names";
        String entityHideInvisibleComment = "Hide entities hidden by the 'invisible' potion effect?";
        String entityWhitelistComment = "Is entity list a whitelist?";

        String[] blockListDefault = {"minecraft:portal", "minecraft:end_portal"};
        int[] dimListDefault = new int[0];
        String[] entityListDefault = new String[0];

        Configuration config = new Configuration(configFile);
        config.load();

        //Blocks
        blockSet.clear();
        Collections.addAll(blockSet, config.getStringList("blockList", "blocks", blockListDefault, blockListComment));
        blockWhitelist = config.getBoolean("isWhitelist", "blocks", false, blockWhitelistComment);

        //Dimensions
        dimList = config.get("dimensions", "dimensionList", dimListDefault, dimListComment).getIntList();
        dimWhitelist = config.getBoolean("isWhitelist", "dimensions", false, dimWhitelistComment);


        //Entities
        entitySet.clear();
        Collections.addAll(entitySet, config.getStringList("entityList", "entities", entityListDefault, entityListComment));
        entityHideInvisible = config.getBoolean("hideInvisible", "entities", true, entityHideInvisibleComment);
        entityWhitelist = config.getBoolean("isWhitelist", "entities", false, entityWhitelistComment);

        config.save();

        initialState = getString();
    }

    public static String getString() {
        String[] temp = new String[4];
        temp[0] = StringHelper.build(blockSet, ',');
        temp[1] = StringHelper.build(dimList, ',');
        temp[2] = StringHelper.build(entitySet, ',');

        temp[3] = "";
        temp[3] = temp[3] + (blockWhitelist ? 1 : 0) + ",";
        temp[3] = temp[3] + (dimWhitelist ? 1 : 0) + ",";
        temp[3] = temp[3] + (entityWhitelist ? 1 : 0) + ",";
        temp[3] = temp[3] + (entityHideInvisible ? 1 : 0);

        return StringHelper.build(temp, '#');
    }

    public static void reset() {
        fromString(initialState);
    }

    public static void fromString(String text) {
        String[] strings = StringHelper.dismantle(text, '#');
        String[] blocks = StringHelper.dismantle(strings[0], ',');
        String[] dims = StringHelper.dismantle(strings[1], ',');
        String[] entities = StringHelper.dismantle(strings[2], ',');
        String[] bools = StringHelper.dismantle(strings[3], ',');

        blockSet.clear();
        entitySet.clear();
        Collections.addAll(blockSet, blocks);
        Collections.addAll(entitySet, entities);

        int[] _dims = new int[dims.length];
        for(int i = 0; i < _dims.length; i++) {
            _dims[i] = Integer.valueOf(dims[i]);
        }
        dimList = _dims;

        blockWhitelist = bools[0].equals("1");
        dimWhitelist = bools[1].equals("1");
        entityWhitelist = bools[2].equals("1");
        entityHideInvisible = bools[3].equals("1");
    }
}
