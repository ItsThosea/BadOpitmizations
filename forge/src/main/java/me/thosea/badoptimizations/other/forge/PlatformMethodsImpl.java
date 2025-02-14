package me.thosea.badoptimizations.other.forge;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;

import java.io.File;

public final class PlatformMethodsImpl {
	private PlatformMethodsImpl() {}

	public static String getVersion() {
		return ModList.get().getModContainerById("badoptimizations")
				.map(mod -> mod.getModInfo().getVersion().toString())
				.orElse("[unknown version]");
	}

	public static File getConfigFolder() {
		return FMLPaths.CONFIGDIR.get().toFile();
	}

	public static boolean isModLoaded(String id) {
		return LoadingModList.get().getModFileById(id) != null;
	}

	public static boolean isOnServer() {
		return FMLEnvironment.dist.isDedicatedServer();
	}
}