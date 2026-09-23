/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 */
package net.unusual.block_factorys_bosses.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfiguration {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Double> SANDWORM_HEALTH;
    public static final ModConfigSpec.ConfigValue<Double> YETI_HEALTH;
    public static final ModConfigSpec.ConfigValue<Double> DRAGON_HEALTH;
    public static final ModConfigSpec.ConfigValue<Double> KNIGHT_HEALTH;
    public static final ModConfigSpec.ConfigValue<Double> KRAKEN_HEALTH;
    public static final ModConfigSpec.ConfigValue<Double> SANDWORM_ARMOR;
    public static final ModConfigSpec.ConfigValue<Double> YETI_ARMOR;
    public static final ModConfigSpec.ConfigValue<Double> DRAGON_ARMOR;
    public static final ModConfigSpec.ConfigValue<Double> KNIGHT_ARMOR;
    public static final ModConfigSpec.ConfigValue<Double> KRAKEN_ARMOR;
    public static final ModConfigSpec.ConfigValue<Double> SANDWORM_ATK;
    public static final ModConfigSpec.ConfigValue<Double> YETI_ATK;
    public static final ModConfigSpec.ConfigValue<Double> DRAGON_ATK;
    public static final ModConfigSpec.ConfigValue<Double> KNIGHT_ATK;
    public static final ModConfigSpec.ConfigValue<Double> KRAKEN_ATK;
    public static final ModConfigSpec.ConfigValue<Boolean> CAN_USE_ROLL;
    public static final ModConfigSpec.ConfigValue<Integer> DEFAULT_ROLL_COUNT;
    public static final ModConfigSpec.ConfigValue<Integer> ROLL_COOLDOWN;
    public static final ModConfigSpec.ConfigValue<Integer> ROLL_COOLDOWN_MORE;

    static {
        BUILDER.push("Boss Health");
        SANDWORM_HEALTH = BUILDER.comment("Max Health : 1024").define("Sandworm Health", 200.0);
        YETI_HEALTH = BUILDER.define("Yeti Health", 250.0);
        DRAGON_HEALTH = BUILDER.define("Dragon Health", 250.0);
        KNIGHT_HEALTH = BUILDER.define("Knight Health", 280.0);
        KRAKEN_HEALTH = BUILDER.define("Kraken Health", 500.0);
        BUILDER.pop();
        BUILDER.push("Boss Armor");
        SANDWORM_ARMOR = BUILDER.comment("Max Armor : 30").define("Sandworm Armor", 20.0);
        YETI_ARMOR = BUILDER.define("Yeti Armor", 5.0);
        DRAGON_ARMOR = BUILDER.define("Dragon Armor", 25.0);
        KNIGHT_ARMOR = BUILDER.define("Knight Armor", 10.0);
        KRAKEN_ARMOR = BUILDER.define("Kraken Armor", 20.0);
        BUILDER.pop();
        BUILDER.push("Base Boss Damages");
        SANDWORM_ATK = BUILDER.comment("Not all damages sources depends on this variable !").define("Sandworm Damage", 10.0);
        YETI_ATK = BUILDER.define("Yeti Damage", 18.0);
        DRAGON_ATK = BUILDER.define("Dragon Damage", 15.0);
        KNIGHT_ATK = BUILDER.define("Knight Damage", 13.0);
        KRAKEN_ATK = BUILDER.define("Kraken Damage", 10.0);
        BUILDER.pop();
        BUILDER.push("Gameplay Changes");
        CAN_USE_ROLL = BUILDER.define("Can players use the Roll ability", true);
        DEFAULT_ROLL_COUNT = BUILDER.define("How many roll charges does the player have by default?", 2);
        ROLL_COOLDOWN = BUILDER.define("How many ticks does it take for a roll to cooldown? (20 ticks is 1 second)", 40);
        ROLL_COOLDOWN_MORE = BUILDER.define("How many additional ticks should each roll above 1 take to cooldown? (20 ticks is 1 second)", 20);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

