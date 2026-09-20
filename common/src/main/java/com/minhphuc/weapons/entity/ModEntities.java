package com.minhphuc.weapons.entity;

import com.minhphuc.weapons.WeaponsMod;
import com.minhphuc.weapons.entity.tensura.PrimordialDemonEntity;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(WeaponsMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<PrimordialDemonEntity>> PRIMORDIAL_DEMON =
            ENTITY_TYPES.register("primordial_demon", () ->
                    EntityType.Builder.of(PrimordialDemonEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .clientTrackingRange(10)
                            .build("primordial_demon")
            );

    public static void register() {
        ENTITY_TYPES.register();
        EntityAttributeRegistry.register(PRIMORDIAL_DEMON, PrimordialDemonEntity::createAttributes);

        BiomeModifications.addProperties(
                b -> b.hasTag(BiomeTags.IS_OVERWORLD),
                (ctx, mutable) -> mutable.getSpawnProperties().addSpawn(
                        MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(PRIMORDIAL_DEMON.get(), 5, 1, 1)
                )
        );
    }
}
