package net.unusual.block_factorys_bosses.init;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.kraken.KrakenTentacleEntity;

public class BossesRiseEntityDataSerializers {
    public static final EntityDataSerializer<Optional<Direction>> OPTIONAL_DIRECTION_RAW = EntityDataSerializer.forValueType((StreamCodec)Direction.STREAM_CODEC.apply(ByteBufCodecs::optional));
    public static final EntityDataSerializer<KrakenTentacleEntity.TentacleType> TENTACLE_TYPE_RAW = EntityDataSerializer.forValueType(KrakenTentacleEntity.TentacleType.STREAM_CODEC);
    public static final StreamCodec<ByteBuf, Vec3> COMPOSITE = StreamCodec.composite((StreamCodec)ByteBufCodecs.DOUBLE, Vec3::x, (StreamCodec)ByteBufCodecs.DOUBLE, Vec3::y, (StreamCodec)ByteBufCodecs.DOUBLE, Vec3::z, Vec3::new);
    public static final EntityDataSerializer<Vec3> VEC_3_RAW = EntityDataSerializer.forValueType(COMPOSITE);

    public static final Supplier<EntityDataSerializer<Optional<Direction>>> OPTIONAL_DIRECTION = () -> OPTIONAL_DIRECTION_RAW;
    public static final Supplier<EntityDataSerializer<KrakenTentacleEntity.TentacleType>> TENTACLE_TYPE = () -> TENTACLE_TYPE_RAW;
    public static final Supplier<EntityDataSerializer<Vec3>> VEC_3 = () -> VEC_3_RAW;

    public static final Object REGISTRY = new Object() {
        public void register(Object bus) {
            // Registered in static block
        }
    };

    static {
        EntityDataSerializers.registerSerializer(OPTIONAL_DIRECTION_RAW);
        EntityDataSerializers.registerSerializer(TENTACLE_TYPE_RAW);
        EntityDataSerializers.registerSerializer(VEC_3_RAW);
    }

    public static void init() {}
}
