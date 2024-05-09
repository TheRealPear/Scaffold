package network.warzone.scaffold;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.querz.mca.Chunk;
import net.querz.mca.MCAFile;
import net.querz.mca.MCAUtil;
import net.querz.mca.Section;

import java.io.File;
import java.io.IOException;

public class Zip {
    public static void create(File folder, final File zipFile) throws IOException {
        pruneWorld(folder);
        ZipFile zip = new ZipFile(zipFile);
        ZipParameters params = new ZipParameters();
        File[] contents = folder.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory())
                    zip.addFolder(file, params);
                else
                    zip.addFile(file, params);
            }
        }
    }

    private static void pruneWorld(File worldDirectory) throws IOException {
        File regionDir = new File(worldDirectory, "region");
        File[] regionFiles = regionDir.listFiles((dir, name) -> name.endsWith(".mca"));
        if (regionFiles != null) {
            for (File regionFile : regionFiles) {
                pruneRegionFile(regionFile);
            }
        }
    }

    private static void pruneRegionFile(File regionFile) throws IOException {
        MCAFile mcaFile = MCAUtil.read(regionFile);
        boolean changesMade = false;

        for (int i = 0; i < 1024; i++) {
            Chunk chunk = mcaFile.getChunk(i);
            if (chunk == null || shouldPruneChunk(chunk)) {
                mcaFile.setChunk(i, null);  // Removes the chunk
                changesMade = true;
            }
        }

        if (changesMade) {
            MCAUtil.write(mcaFile, regionFile.getPath());
        }
    }

    private static boolean shouldPruneChunk(Chunk chunk) {
        if (chunk.getEntities().size() > 0 || chunk.getTileEntities().size() > 0) {
            return false; // Don't prune chunks with entities or tile entities
        }

        for (int y = 0; y < 16; y++) {
            Section section = chunk.getSection(y);
            if (section != null && !section.isEmpty()) {
                return false; // Don't prune if any section is not empty
            }
        }

        return !chunk.hasSpecialBiomes(); // Also check for special biomes
    }

    public static void extract(File zipFile, File directory) throws IOException, ZipException {
        ZipFile zip = new ZipFile(zipFile);
        zip.extractAll(directory.getAbsolutePath());
    }
}
