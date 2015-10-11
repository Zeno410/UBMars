
package ubMars;
import exterminatorJeff.undergroundBiomes.api.BiomeGenUndergroundBase;
import exterminatorJeff.undergroundBiomes.api.StrataLayer;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomeSet;
import exterminatorJeff.undergroundBiomes.api.UndergroundBiomeSetProvider;

/**
 *
 * @author Zeno410
 */
public class MartianUBSet extends UndergroundBiomeSet {

    public MartianUBSet(long seed) {
        super(new MartianStrataLayers(seed).strataLayer());
    }
    private BiomeGenUndergroundBase[] allowedBiomes;
    private boolean biomesSet = false;
    @Override
    public BiomeGenUndergroundBase[] allowedBiomes() {

        if (biomesSet) return allowedBiomes;
        allowedBiomes = new BiomeGenUndergroundBase[strataLayers.length];
        for (int i = 0 ; i < strataLayers.length; i ++) {
            biomeList[i] = new BiomeGenUndergroundBase(i,UBIDs.igneousStoneName,5,this.biomeList,strataLayers[i]);
            allowedBiomes[i] = biomeList[i];
        }


        biomesSet = true;
        return allowedBiomes;
    }

    public static class Provider implements UndergroundBiomeSetProvider {
        final int martianDimension;

        public Provider(int martianDimension) {
            this.martianDimension = martianDimension;
        }

        public UndergroundBiomeSet modifiedBiomeSet(int arg0, long arg1, UndergroundBiomeSet arg2) {
            if (arg0== martianDimension) return new MartianUBSet(arg1);
            return null;
        }

    }
}
