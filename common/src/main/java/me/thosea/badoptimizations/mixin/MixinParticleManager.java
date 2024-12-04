package me.thosea.badoptimizations.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleManager.class)
public class MixinParticleManager {
	@Shadow @Final private Map<ParticleTextureSheet, Queue<Particle>> particles;

	// implicit, 1.21.4 adds another method with a similar signature
	// we can target both with this while maintaining 1.21.3 compatibility
	@Inject(method = "renderParticles*", at = @At("HEAD"), cancellable = true)
	private void onRender(CallbackInfo ci) {
		if(particles.isEmpty()) {
			ci.cancel();
		}
	}
}