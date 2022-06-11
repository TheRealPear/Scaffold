package network.warzone.scaffold;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Created by MatrixTunnel on 10/25/18.
 */
public class NullChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biomeGrid) {
        ChunkData data = createChunkData(world);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biomeGrid.setBiome(i, j, Biome.PLAINS);
            }
        }
        return data;
    }

    @Override
    public final Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0.5, 65.0, 0.5);
    }

}
