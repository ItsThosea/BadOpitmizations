package me.thosea.badoptimizations.mixin;

import me.thosea.badoptimizations.DummyLock;
import me.thosea.badoptimizations.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.DataTracker.Entry;
import net.minecraft.entity.data.DataTracker.SerializedEntry;
import net.minecraft.entity.data.TrackedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.locks.ReadWriteLock;

@Mixin(DataTracker.class)
public abstract class MixinDataTracker {
	@Shadow @Final @Mutable private ReadWriteLock lock;
	@Unique private EntityAccessor entityAccessor;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void replaceLock(Entity trackedEntity, CallbackInfo ci) {
		this.lock = DummyLock.INSTANCE;
		this.entityAccessor = (EntityAccessor) trackedEntity;
	}

	@Inject(method = "set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;Z)V", at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/entity/data/DataTracker$Entry;set(Ljava/lang/Object;)V"))
	private void onDataSet(TrackedData<?> key, Object value, boolean force, CallbackInfo ci) {
		entityAccessor.badoptimizations$refreshEntityData(key);
	}

	@Inject(method = "copyToFrom", at = @At("TAIL"))
	private void onCopy(Entry<?> to, SerializedEntry<?> from, CallbackInfo ci) {
		entityAccessor.badoptimizations$refreshEntityData(to.getData());
	}
}
