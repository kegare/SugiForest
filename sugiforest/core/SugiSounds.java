package sugiforest.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SugiSounds
{
	public static final SugiSoundEvent sugichest_open = new SugiSoundEvent("sugichest.open");
	public static final SugiSoundEvent sugichest_close = new SugiSoundEvent("sugichest.close");

	public static void registerSounds()
	{
		GameRegistry.register(sugichest_open);
		GameRegistry.register(sugichest_close);
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