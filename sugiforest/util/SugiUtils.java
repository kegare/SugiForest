package sugiforest.util;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import sugiforest.core.SugiForest;

public class SugiUtils
{
	public static ModContainer getModContainer()
	{
		ModContainer mod = Loader.instance().getIndexedModList().get(SugiForest.MODID);

		if (mod == null)
		{
			mod = Loader.instance().activeModContainer();

			if (mod == null || mod.getModId() != SugiForest.MODID)
			{
				return new DummyModContainer(SugiForest.metadata);
			}
		}

		return mod;
	}
}