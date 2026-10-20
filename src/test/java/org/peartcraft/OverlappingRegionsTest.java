package org.peartcraft;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import net.sandrohc.schematic4j.schematic.LitematicaSchematic;
import net.sandrohc.schematic4j.schematic.types.SchematicBlock;
import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;
import org.junit.jupiter.api.Test;

class OverlappingRegionsTest {
    private static LitematicaSchematic.Region region(int x, String block) {
        LitematicaSchematic.Region region = new LitematicaSchematic.Region();
        region.position = new SchematicBlockPos(x, 0, 0);
        region.size = new SchematicBlockPos(1, 1, 1);
        region.blockStatePalette = new SchematicBlock[]{new SchematicBlock(block)};
        region.blockStates = new int[]{0};
        return region;
    }

    @Test void overlappingRegionsProduceOneWaypoint() throws Exception {
        LitematicaSchematic schematic = new LitematicaSchematic();
        schematic.regions = new LitematicaSchematic.Region[]{
                region(-2, "minecraft:chest"),
                region(0, "minecraft:chest"),
                region(0, "minecraft:barrel")
        };
        ConfigData data = new ConfigData(100, 20, 200, 102, 20, 200,
                "overworld", "XAERO",
                Set.of("minecraft:chest", "minecraft:barrel"), schematic);

        BlockFinder.BlockSearchResult found = BlockFinder.findBlockPositions(data);
        assertEquals(List.of(new SchematicBlockPos(0, 0, 0),
                             new SchematicBlockPos(2, 0, 0)), found.positions());
        assertEquals(List.of("minecraft:chest", "minecraft:chest"), found.blockStates());
        assertEquals(2, new XaeroMapWaypointCreator().createWaypoints(
                found.positions(), found.blockStates(), data).size());
        assertEquals(2, new VoxelMapWaypointCreator().createWaypoints(
                found.positions(), found.blockStates(), data).size());
    }
}
