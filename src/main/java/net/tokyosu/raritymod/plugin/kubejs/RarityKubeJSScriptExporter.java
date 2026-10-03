package net.tokyosu.raritymod.plugin.kubejs;

import net.tokyosu.apocalypselib.utils.FileUtils;
import net.tokyosu.raritymod.plugin.kubejs.event.RarityStartupRegister;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RarityKubeJSScriptExporter {
    private static final Path KUBEJS_SCRIPTS_DIR = Paths.get("kubejs/startup_scripts");

    public static void exportToJs(@NotNull String fileName) {
        try {
            var jsContent = new StringBuilder();
            jsContent.append("// Auto-generated rarity configuration\n");
            jsContent.append("// Generated at: ").append(java.time.LocalDateTime.now()).append("\n\n");
            jsContent.append("RarityJSEvents.register(event => {\n");

            // Export item rarities
            if (!RarityStartupRegister.RARITY_ITEM_LIST.isEmpty()) {
                jsContent.append("    // ===== Item Rarities =====\n");
                for (var entry : RarityStartupRegister.RARITY_ITEM_LIST.entrySet()) {
                    jsContent.append(String.format("    event.setRarity('%s', '%s');\n",
                            entry.getKey(),
                            entry.getValue()));
                }
                jsContent.append("\n");
            }

            // Export mod rarities
            if (!RarityStartupRegister.RARITY_MOD_LIST.isEmpty()) {
                jsContent.append("    // ===== Mod Rarities =====\n");
                for (var entry : RarityStartupRegister.RARITY_MOD_LIST.entrySet()) {
                    jsContent.append(String.format("    event.setRarityByMod('%s', '%s');\n",
                            entry.getKey(),
                            entry.getValue()));
                }
                jsContent.append("\n");
            }

            // Export tag rarities
            if (!RarityStartupRegister.RARITY_TAG_LIST.isEmpty()) {
                jsContent.append("    // ===== Tag Rarities =====\n");
                for (var entry : RarityStartupRegister.RARITY_TAG_LIST.entrySet()) {
                    jsContent.append(String.format("    event.setRarityByTag('%s', '%s');\n",
                            entry.getKey(),
                            entry.getValue()));
                }
                jsContent.append("\n");
            }

            // Export NBT rarities
            if (!RarityStartupRegister.RARITY_NBT_LIST.isEmpty()) {
                jsContent.append("    // ===== NBT Rarities =====\n");
                for (var entry : RarityStartupRegister.RARITY_NBT_LIST.entrySet()) {
                    String nbtString = entry.getValue().getA().toString();
                    jsContent.append(String.format("    event.setRarityByNBT('%s', '%s', '%s');\n",
                            entry.getKey(),
                            escapeJsString(nbtString),
                            entry.getValue().getB()));
                }
                jsContent.append("\n");
            }

            // Export default rarity
            if (RarityStartupRegister.getDefaultRarityId() != null) {
                jsContent.append("    // ===== Default Rarity =====\n");
                jsContent.append(String.format("    event.setDefaultRarity('%s');\n",
                        RarityStartupRegister.getDefaultRarityId()));
            }

            jsContent.append("});\n");

            // Write to file
            Path filePath = FileUtils.writeUtf8(KUBEJS_SCRIPTS_DIR, fileName + ".js", jsContent.toString());
            System.out.println("Successfully exported rarity config to: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeJsString(String str) {
        return str.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
