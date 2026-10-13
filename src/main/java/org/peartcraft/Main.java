package org.peartcraft;

import javafx.application.Application;
import javafx.application.Platform;
import net.sandrohc.schematic4j.schematic.types.SchematicBlockPos;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Main {
    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        Platform.startup(() -> {});

        ConfigGui gui = new ConfigGui();
        CompletableFuture<ConfigData> configFuture = gui.showAndWait();
        ConfigData data;

        try {
            data = configFuture.get();
        } catch (Exception ignored) {
            return;
        }

        List<SchematicBlockPos> positions;
        List<String> blocks;
        try {
            BlockFinder.BlockSearchResult result = BlockFinder.findBlockPositions(data);
            positions = result.positions();
            blocks = result.blockStates();

            for (int i = 0; i < positions.size(); i++) {
                System.out.println(positions.get(i) + " " + blocks.get(i));
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        WayPointCreator creator = WaypointCreatorFactory.create(data.creator());

        SaveGui.linesToSave = creator.createWaypoints(positions, blocks, data);
        new Thread(() -> Application.launch(SaveGui.class)).start();

        try {
            SaveGui.finished.get();
        } catch (Exception ignored) {}
        finally {
            Platform.exit();
        }
    }
}

