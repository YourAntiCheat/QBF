package org.peartcraft;

import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;

import java.util.ArrayList;
import java.util.List;

public class VoxelMapWaypointCreator implements WayPointCreator {
    @Override
    public List<String> createWaypoints(List<SchematicBlockPos> positions, List<String> blocks, ConfigData data) {
        int x, y, z;
        x = Math.min(data.x1(), data.x2());
        y = Math.min(data.y1(), data.y2());
        z = Math.min(data.z1(), data.z2());

        List<String> waypoints = new ArrayList<>();
        Dimension dimension = Dimension.fromDisplayName(data.dimension());

        for (int i = 0; i < positions.size(); i++) {
            SchematicBlockPos pos = positions.get(i);
            String block = i < blocks.size() ? blocks.get(i) : null;
            float[] rgb = BlockColor.getNormalizedColor(block);
            // Beautify waypoint name with spacing and capital letter
            String blockName = BlockColor.getBaseName(block).replace("_", " ");
            String waypointName = (i + 1) + " " + blockName.substring(0, 1).toUpperCase() + blockName.substring(1);

            waypoints.add(String.format("name:%s,x:%d,z:%d,y:%d,enabled:true,red:%.3f,green:%.3f,blue:%.3f,suffix:,world:,dimensions:%s#",
                    waypointName,
                    (Math.abs(pos.x) + x) * dimension.getMultiplier(),
                    (Math.abs(pos.z) + z) * dimension.getMultiplier(),
                    (Math.abs(pos.y) + y),
                    rgb[0], rgb[1], rgb[2],
                    dimension.getId()));
        }
        return waypoints;
    }
}
