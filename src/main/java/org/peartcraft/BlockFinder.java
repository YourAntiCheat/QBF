package org.peartcraft;

import net.sandrohc.schematic4j.schematic.LitematicaSchematic;
import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;

import java.util.*;
import java.util.concurrent.*;

/**
 * Finds selected blocks in every Litematica region and returns local
 * coordinates relative to the minimum corner of the complete schematic.
 */
public class BlockFinder {
    public record BlockSearchResult(List<SchematicBlockPos> positions, List<String> blockStates) {}

    public static BlockSearchResult findBlockPositions(ConfigData data)
            throws InterruptedException, ExecutionException {
        LitematicaSchematic.Region[] regions = data.litematic().regions();
        if (regions.length == 0) {
            return new BlockSearchResult(List.of(), List.of());
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        for (LitematicaSchematic.Region region : regions) {
            minX = Math.min(minX, LitematicaCoordinates.regionMin(region.position.x, region.size.x));
            minY = Math.min(minY, LitematicaCoordinates.regionMin(region.position.y, region.size.y));
            minZ = Math.min(minZ, LitematicaCoordinates.regionMin(region.position.z, region.size.z));
        }
        final int originX = minX, originY = minY, originZ = minZ;

        List<SchematicBlockPos> blockPositions = new ArrayList<>();
        List<String> allBlockStates = new ArrayList<>();
        for (LitematicaSchematic.Region region : regions) {
            Set<Integer> selectedIds = new HashSet<>();
            for (int p = 0; p < region.blockStatePalette.length; p++) {
                if (data.blockSet().contains(region.blockStatePalette[p].block())) {
                    selectedIds.add(p);
                }
            }
            if (selectedIds.isEmpty()) continue;

            int regionX = LitematicaCoordinates.regionMin(region.position.x, region.size.x);
            int regionY = LitematicaCoordinates.regionMin(region.position.y, region.size.y);
            int regionZ = LitematicaCoordinates.regionMin(region.position.z, region.size.z);

            for (int i = 0; i < region.blockStates.length; i++) {
                int paletteId = region.blockStates[i];
                if (!selectedIds.contains(paletteId)) continue;
                int[] local = LitematicaCoordinates.indexToLocal(i,
                        region.size.x, region.size.y, region.size.z);
                blockPositions.add(new SchematicBlockPos(
                        regionX - originX + local[0],
                        regionY - originY + local[1],
                        regionZ - originZ + local[2]));
                allBlockStates.add(region.blockStatePalette[paletteId].block());
            }
        }
        return new BlockSearchResult(blockPositions, allBlockStates);
    }
}
