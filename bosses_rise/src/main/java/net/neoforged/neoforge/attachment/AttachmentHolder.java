package net.neoforged.neoforge.attachment;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class AttachmentHolder {
    private static final Map<Object, Map<Object, Object>> DATA_MAP = new WeakHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getData(Object entity, Supplier<?> type) {
        synchronized (DATA_MAP) {
            Map<Object, Object> entityData = DATA_MAP.computeIfAbsent(entity, k -> new ConcurrentHashMap<>());
            Object attachmentType = type.get();
            return (T) entityData.computeIfAbsent(attachmentType, k -> {
                if (attachmentType instanceof AttachmentType<?> at) {
                    return at.createDefault();
                }
                return null;
            });
        }
    }

    public static <T> void setData(Object entity, Supplier<?> type, T data) {
        synchronized (DATA_MAP) {
            Map<Object, Object> entityData = DATA_MAP.computeIfAbsent(entity, k -> new ConcurrentHashMap<>());
            entityData.put(type.get(), data);
        }
    }
}
