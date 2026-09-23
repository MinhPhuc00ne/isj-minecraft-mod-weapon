/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.StringRepresentable
 *  net.neoforged.neoforge.common.ModConfigSpec
 *  net.neoforged.neoforge.common.ModConfigSpec$Builder
 *  net.neoforged.neoforge.common.ModConfigSpec$ConfigValue
 */
package net.unusual.block_factorys_bosses.configuration;

import java.util.Locale;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.unusual.block_factorys_bosses.util.BossHandling;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientConfiguration {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarDefaultStyle> DEFAULT_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> WARDEN_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> ENDER_DRAGON_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> WITHER_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> YETI_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> DRAGON_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> SANDWORM_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> KNIGHT_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<BossHandling.HealthBarStyle> KRAKEN_BOSSBAR;
    public static final ModConfigSpec.ConfigValue<Boolean> FIRST_PERSON_ROLL;
    public static final ModConfigSpec.ConfigValue<Boolean> ROLL_GUI;
    public static final ModConfigSpec.ConfigValue<GUIAnchors> ROLL_GUI_OFFSET_ANCHOR;
    public static final ModConfigSpec.ConfigValue<Integer> ROLL_GUI_OFFSET_X;
    public static final ModConfigSpec.ConfigValue<Integer> ROLL_GUI_OFFSET_Y;

    static {
        BUILDER.push("Boss bars");
        BUILDER.comment("Our mod has the ability to modify how the healthbars of certain bosses appear. Pick the one that suits you most.");
        BUILDER.comment("\"OFF\" : Disable the healthbar from rendering.");
        BUILDER.comment("\"VANILLA\" : All healthbars will appear vanilla.");
        BUILDER.comment("\"SIMPLE\" : Render the healthbar in a simple custom texture.");
        BUILDER.comment("\"FANCY\" : Render the healthbar in a fancy custom texture.");
        BUILDER.comment("Select the \"DEFAULT\" option on a boss to have the setting follow the default one.");
        DEFAULT_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should be used by default?", (Enum)BossHandling.HealthBarDefaultStyle.FANCY);
        WARDEN_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should the Warden use?", (Enum)BossHandling.HealthBarStyle.OFF);
        ENDER_DRAGON_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should the Ender Dragon use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        WITHER_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should the Wither use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        YETI_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should Skor, The Yeti use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        DRAGON_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should Ashlord, The Infernal Dragon use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        SANDWORM_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should Sirok, The Sandworm use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        KNIGHT_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should Helvar, the Underworld Knight use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        KRAKEN_BOSSBAR = BUILDER.defineEnum("What kind of healthbar should Nerakyss, the Kraken use?", (Enum)BossHandling.HealthBarStyle.DEFAULT);
        BUILDER.pop();
        BUILDER.push("Rolling");
        FIRST_PERSON_ROLL = BUILDER.define("Will the camera rotate when rolling while in first-person view", false);
        ROLL_GUI = BUILDER.define("Will there be a GUI element displaying current roll count", true);
        BUILDER.comment("GUI anchors are preset locations for the rolling GUI visual to appear. Pick the one that suits you most.");
        BUILDER.comment("\"HOTBAR_RIGHT\" : At the immediate right of your hotbar.");
        BUILDER.comment("\"HOTBAR_LEFT\" : At the immediate left of your hotbar.");
        BUILDER.comment("\"TOP_LEFT\" : In the top left corner of your screen.");
        BUILDER.comment("\"TOP_RIGHT\" : In the top right corner of your screen.");
        BUILDER.comment("\"BOTTOM_LEFT\" : In the bottom left corner of your screen.");
        BUILDER.comment("\"BOTTOM_RIGHT\" : In the bottom right corner of your screen.");
        BUILDER.comment("\"SCREEN_CENTER\" : At the exact middle of your screen?");
        ROLL_GUI_OFFSET_ANCHOR = BUILDER.defineEnum("The GUI anchor will be", (Enum)GUIAnchors.HOTBAR_RIGHT);
        BUILDER.comment("You can also additionally offset the GUI element with the use of the X and Y offset values.");
        ROLL_GUI_OFFSET_X = BUILDER.define("Roll GUI element X offset (Bigger number goes right, negative goes left)", 0);
        ROLL_GUI_OFFSET_Y = BUILDER.define("Roll GUI element Y offset (Bigger number goes down, negative goes up)", 0);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static enum GUIAnchors implements StringRepresentable
    {
        HOTBAR_RIGHT,
        HOTBAR_LEFT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        SCREEN_CENTER;


        public String getSerializedName() {
            return this.toString().toLowerCase(Locale.ROOT);
        }
    }
}

