package sugiforest.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class SugiSounds
{
	public static final SugiSoundEvent SUGI_CHEST_OPEN = new SugiSoundEvent("sugichest.open");
	public static final SugiSoundEvent SUGI_CHEST_CLOSE = new SugiSoundEvent("sugichest.close");

	public static void registerSounds(IForgeRegistry<SoundEvent> registry)
	{
		registry.register(SUGI_CHEST_OPEN);
		registry.register(SUGI_CHEST_CLOSE);
	}

	public static class SugiSoundEvent extends SoundEvent
	{
		public SugiSoundEvent(ResourceLocation soundName)
		{
			super(soundName);
			this.setRegistryName(soundName);
		}

		public SugiSoundEvent(String name)
		{
			this(new ResourceLocation(SugiForest.MODID, name));
		}
	}
}