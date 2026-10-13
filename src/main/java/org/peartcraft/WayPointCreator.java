package org.peartcraft;

import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;

import java.util.List;

public interface WayPointCreator {
    List<String> createWaypoints(List<SchematicBlockPos> positions, List<String> blocks, ConfigData data);
}
