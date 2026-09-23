package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent {
    private final Entity entity;
    private final Projectile projectile;
    private final HitResult rayTraceResult;
    private boolean canceled;

    public ProjectileImpactEvent(Entity entity, Projectile projectile, HitResult rayTraceResult) {
        this.entity = entity;
        this.projectile = projectile;
        this.rayTraceResult = rayTraceResult;
    }

    public Entity getEntity() { return entity; }
    public Projectile getProjectile() { return projectile; }
    public HitResult getRayTraceResult() { return rayTraceResult; }
    public void setCanceled(boolean canceled) { this.canceled = canceled; }
    public boolean isCanceled() { return canceled; }
}
