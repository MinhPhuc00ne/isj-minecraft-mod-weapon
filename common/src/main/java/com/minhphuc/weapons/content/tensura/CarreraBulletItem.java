package com.minhphuc.weapons.content.tensura;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Ma Đạn Hoàng Kim của Hoàng Sắc Thủy Tổ Carrera (Tensura Light Novel).
 * Gồm 4 chủng loại ma đạn tối thượng.
 */
public class CarreraBulletItem extends Item {

    public enum BulletType {
        JUDGEMENT(0, "§e§lĐạn Thần Tốc Phán Quyết", "§6Judgement Bullet", Rarity.EPIC, 0xFFFFDD00,
                "§fPhán quyết thần thánh. §c§lBẮN PHÁT CHẾT LUÔN! §fPhá hủy hoàn toàn hạch tâm linh hồn."),
        ABYSS_CORE(1, "§d§lĐạn Hạch Thâm Uyên", "§5Abyss Core Bullet", Rarity.EPIC, 0xFFCC00FF,
                "§fMa pháp hạt nhân Abaddon. Tạo hố đen trọng lực hút kẻ địch và phát nổ cực đại."),
        GRAVITY(2, "§9§lĐạn Trọng Lực Sụp Đổ", "§1Gravity Collapse Bullet", Rarity.RARE, 0xFF8800FF,
                "§fĐè bẹp không gian trong 12 blocks, khóa cứng di chuyển và gây sát thương nghiền nát."),
        RAPID(3, "§6§lĐạn Hoàng Kim Xạ Kích", "§eGolden Rapid Bullet", Rarity.UNCOMMON, 0xFFFFAA00,
                "§fMa đạn tốc độ ánh sáng, xuyên giáp cực mạnh, thích hợp xả đạn liên thanh.");

        public final int id;
        public final String vietName;
        public final String engName;
        public final Rarity rarity;
        public final int color;
        public final String description;

        BulletType(int id, String vietName, String engName, Rarity rarity, int color, String description) {
            this.id = id;
            this.vietName = vietName;
            this.engName = engName;
            this.rarity = rarity;
            this.color = color;
            this.description = description;
        }

        public static BulletType fromId(int id) {
            for (BulletType type : values()) {
                if (type.id == id) return type;
            }
            return RAPID;
        }
    }

    private final BulletType bulletType;

    public CarreraBulletItem(BulletType bulletType) {
        super(new Properties().stacksTo(64).rarity(bulletType.rarity));
        this.bulletType = bulletType;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§6§l[HOÀNG SẮC THỦY TỔ - MA ĐẠO KHÍ]"));
        tooltip.add(Component.literal(bulletType.vietName + " §7(" + bulletType.engName + "§7)"));
        tooltip.add(Component.literal("§7Loại đạn: " + bulletType.description));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§e👉 Cầm Súng Hoàng Kim và nhấn §b[PgUp] §eđể nạp và chuyển sang loại đạn này!"));
    }
}
