package org.peartcraft;

import java.util.Locale;
import java.util.Map;

public class BlockColor {

    /**
     * Represents an RGB color with utility methods for color conversion.
     */
    public static class Color {
        private final int red, green, blue;

        public Color(int red, int green, int blue) {
            this.red = clamp(red, 0, 255);
            this.green = clamp(green, 0, 255);
            this.blue = clamp(blue, 0, 255);
        }

        public Color(String hex) {
            if (hex.startsWith("#")) hex = hex.substring(1);
            if (hex.length() != 6) throw new IllegalArgumentException("Invalid hex color: " + hex);
            
            int rgb = Integer.parseInt(hex, 16);
            this.red = (rgb >> 16) & 0xFF;
            this.green = (rgb >> 8) & 0xFF;
            this.blue = rgb & 0xFF;
        }

        public int getRed() { return red; }
        public int getGreen() { return green; }
        public int getBlue() { return blue; }

        public float[] toNormalizedFloats() {
            return new float[]{red / 255f, green / 255f, blue / 255f};
        }

        public int[] toIntArray() {
            return new int[]{red, green, blue};
        }

        public String toHex() {
            return String.format("#%02X%02X%02X", red, green, blue);
        }

        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }

        @Override
        public String toString() {
            return String.format("Color(r=%d, g=%d, b=%d)", red, green, blue);
        }
    }

    // Color constants grouped by similarity
    public static final Color WOOD_BROWN = new Color("#8F7748");         // 143, 119, 72
    public static final Color DARK_GRAY = new Color("#707070");          // 112, 112, 112
    public static final Color LIGHT_GRAY = new Color("#A7A7A7");         // 167, 167, 167
    public static final Color BRIGHT_YELLOW = new Color("#E5E533");      // 229, 229, 51
    public static final Color VERY_DARK_GRAY = new Color("#191919");     // 25, 25, 25
    public static final Color PURPLE = new Color("#7F3FB2");             // 127, 63, 178
    public static final Color DARK_TEAL = new Color("#167E86");          // 22, 126, 134
    public static final Color CHARCOAL = new Color("#646464");           // 100, 100, 100
    public static final Color ORANGE = new Color("#d87f33");                   // 216, 127, 51

    private static final Map<String, Color> BLOCK_TO_COLOR = Map.ofEntries(
            // Wood-colored blocks (storage, functional)
            Map.entry("barrel", WOOD_BROWN),
            Map.entry("beehive", WOOD_BROWN),
            Map.entry("chest", WOOD_BROWN),
            Map.entry("lectern", WOOD_BROWN),
            Map.entry("trapped_chest", WOOD_BROWN),
            
            // Dark gray blocks (machines, functional)
            Map.entry("blast_furnace", DARK_GRAY),
            Map.entry("dispenser", DARK_GRAY),
            Map.entry("furnace", DARK_GRAY),
            Map.entry("hopper", DARK_GRAY),
            Map.entry("smoker", DARK_GRAY),
            Map.entry("spawner", DARK_GRAY),
            
            // Light gray blocks (tools, utility)
            Map.entry("brewing_stand", LIGHT_GRAY),
            Map.entry("grindstone", LIGHT_GRAY),
            
            // Yellow blocks (organic, special)
            Map.entry("bee_nest", BRIGHT_YELLOW),
            Map.entry("sponge", BRIGHT_YELLOW),
            
            // Dark blocks
            Map.entry("crying_obsidian", VERY_DARK_GRAY),
            Map.entry("reinforced_deepslate", CHARCOAL),
            
            // Special-colored blocks
            Map.entry("obsidian", PURPLE),
            Map.entry("sculk_catalyst", DARK_TEAL),
            Map.entry("sculk_sensor", DARK_TEAL),
            Map.entry("sculk_shrieker", DARK_TEAL),
            Map.entry("trial_spawner", ORANGE),
            Map.entry("vault", ORANGE),
            Map.entry("ender_chest", PURPLE)
    );

    /**
     * Gets the normalized RGB color values (0.0-1.0) for a given block name.
     * Returns white (1.0, 1.0, 1.0) if the block is not found.
     */
    public static float[] getNormalizedColor(String blockName) {
        if (blockName == null) return new float[]{1.0f, 1.0f, 1.0f};
        String key = sanitize(blockName);
        Color color = BLOCK_TO_COLOR.get(key);
        if (color == null) return new float[]{1.0f, 1.0f, 1.0f};
        return color.toNormalizedFloats();
    }

    /**
     * Gets the Color object for a given block name.
     * Returns null if the block is not found.
     */
    public static Color getColor(String blockName) {
        if (blockName == null) return null;
        String key = sanitize(blockName);
        return BLOCK_TO_COLOR.get(key);
    }

    /**
     * Gets the RGB values as an int array for a given block name.
     * Returns white [255, 255, 255] if the block is not found.
     */
    public static int[] getRgbArray(String blockName) {
        if (blockName == null) return new int[]{255, 255, 255};
        String key = sanitize(blockName);
        Color color = BLOCK_TO_COLOR.get(key);
        if (color == null) return new int[]{255, 255, 255};
        return color.toIntArray();
    }

    public static String getBaseName(String blockName) {
        if (blockName == null) return "block";
        return sanitize(blockName);
    }

    private static String sanitize(String blockName) {
        String key = blockName.toLowerCase(Locale.ROOT);
        int idx = key.indexOf('[');
        if (idx >= 0) key = key.substring(0, idx);
        // Strip namespace if present, e.g., minecraft:chest -> chest
        int colon = key.indexOf(':');
        if (colon >= 0 && colon + 1 < key.length()) key = key.substring(colon + 1);
        return key;
    }
}


