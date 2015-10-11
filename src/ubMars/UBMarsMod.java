
package ubMars;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
//import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
    @Mod(modid = "ubMars", name = "UBMars", version = "0.1", dependencies = "required-after:UndergroundBiomes")
/**
 *
 * @author Zeno410
 */
public class UBMarsMod{
    /*@SidedProxy(clientSide = "ubMars.ClientProxy",
                serverSide = "ubMars.CommonProxy")
    public static CommonProxy proxy;*/

    public int martianDimension = 0;
    //private MartianUBSet.Provider provider;

    @EventHandler
    public void load(FMLInitializationEvent event){
        try {
            UBAPIHook.ubAPIHook.ubSetProviderRegistry.register(new MartianUBSet.Provider(martianDimension));
        } catch (java.lang.NoClassDefFoundError e) {
            // no UB; can't do anything
            // throw an RuntimeException if you're worried about users not having UB installed
        }

    }


}