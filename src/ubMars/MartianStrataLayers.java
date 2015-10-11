
package ubMars;

import exterminatorJeff.undergroundBiomes.api.NamedBlock;
import exterminatorJeff.undergroundBiomes.api.StrataLayer;
import exterminatorJeff.undergroundBiomes.api.UBIDs;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Zeno410
 */
public class MartianStrataLayers {

    private final long originalSeed;

    private NamedBlock igneous = UBIDs.igneousStoneName;
    private NamedBlock sedimentary = UBIDs.sedimentaryStoneName;

    private int redGranite = 0;
    private int blackGranite = 1;
    private int rhyolite = 2;
    private int andesite = 3;
    private int gabbro = 5;
    private int komantite = 6;
    private int dacite = 7;

    //sedimentary metadatas
    private int shale = 2;
    private int siltstone = 3;
    private int greywacke = 6;

    private int cratonCount = 5;
    private int setsOfLayers = 2;

    public final Commonality common = new Commonality(2,2,6);
    public final Commonality uncommon = new Commonality(1,1,3);
    private Random random;

    public MartianStrataLayers(long originalSeed) {
        this.originalSeed = originalSeed;
    }

    private ArrayList<ArrayList<StrataLayer>> underConstruction;

    public StrataLayer [] [] strataLayer() {
        underConstruction = new ArrayList<ArrayList<StrataLayer>> ();
        for (int i = 0; i<cratonCount; i++) {
            underConstruction.add (new ArrayList<StrataLayer>());
        }
        random = new Random(originalSeed);
        
        for (int set = 0; set<setsOfLayers; set++) {
            add(igneous,redGranite,common);
            add(igneous,dacite,common);
            add(sedimentary,shale,common);
            add(sedimentary,siltstone,common);
            add(sedimentary,greywacke,common);


            add(igneous,blackGranite,uncommon);
            add(igneous,rhyolite,uncommon);
            add(igneous,andesite,uncommon);
            add(igneous,gabbro,uncommon);
            add(igneous,komantite,uncommon);
        }

        StrataLayer [] [] result = new StrataLayer [cratonCount] [];
        for (int i = 0; i < cratonCount; i++) {
            StrataLayer [] column = new StrataLayer [underConstruction.get(i).size()];
            result [i] = underConstruction.get(i).toArray(column);
        }

        return result;
    }

    public static class Commonality {
        private final int occurances;
        private final int minimumThickness;
        private final int maximumThickness;

        public Commonality(int occurances, int minimumThickness, int maximumThickness) {
            this.occurances = occurances;
            this.minimumThickness = minimumThickness;
            this.maximumThickness = maximumThickness;
        }
    }

    private class CantPlaceException extends Exception {}

    private void add(NamedBlock block, int metadata, Commonality commonality) {
        int failures = 0;
        for (int occurance = 0; occurance < commonality.occurances; occurance++) {
            try {
                int layerStart = random.nextInt(96);
                add (new StrataLayer(block,metadata,layerStart,layerStart + commonality.minimumThickness +
                        random.nextInt(commonality.maximumThickness-commonality.minimumThickness)));
            } catch (CantPlaceException e) {
                // redo
                occurance -= 1;
                // and throw a fit if too many
                if (failures++ > 1000) throw new RuntimeException("Cannot place all the strata layers");
            }
        }
    }

    private void add(StrataLayer layer) throws CantPlaceException{
        int target = random.nextInt(cratonCount);
        for (StrataLayer existingLayer: this.underConstruction.get(target)) {
            for (int level = layer.layerMin; level <= layer.layerMax; level++) {
                if (existingLayer.valueIsInLayer(level)) throw new CantPlaceException();
            }
        }
        underConstruction.get(target).add(layer);
    }

}
