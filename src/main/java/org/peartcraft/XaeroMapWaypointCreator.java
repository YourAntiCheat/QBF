package org.peartcraft;

import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;

import java.util.ArrayList;
import java.util.List;

public class XaeroMapWaypointCreator implements WayPointCreator {
    @Override
    public List<String> createWaypoints(List<SchematicBlockPos> positions, List<String> blocks, ConfigData data) {
        int x, y, z;
        x = Math.min(data.x1(), data.x2());
        y = Math.min(data.y1(), data.y2());
        z = Math.min(data.z1(), data.z2());

        List<String> waypoints = new ArrayList<>();

        int i = 0;
        for (SchematicBlockPos pos : positions) {
            waypoints.add(String.format("waypoint:%d:UB:%d:%d:%d:0:false:0:gui.xaero_default:false:0:0:true", 
            i, 
            Math.abs(pos.x) + x, 
            Math.abs(pos.y) + y - 1, 
            Math.abs(pos.z) + z
            ));
            i++;
        }

        return waypoints;
    }
}
