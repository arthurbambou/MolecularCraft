package io.github.vampirestudios.molecularcraft;

import com.swordglowsblue.artifice.api.Artifice;
import io.github.vampirestudios.molecularcraft.container.DisassemblerContainer;
import io.github.vampirestudios.molecularcraft.container.DisassemblerScreen;
import io.github.vampirestudios.molecularcraft.enums.Atoms;
import io.github.vampirestudios.molecularcraft.enums.Molecules;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static io.github.vampirestudios.molecularcraft.MolecularCraft.MODID;

public class MolecularCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("molecularcraft:disassembler"),
                (syncId, id, player, buf) -> {
                    BlockPos pos = buf.readBlockPos();
                    return new DisassemblerScreen(new DisassemblerContainer(syncId, player.inventory, pos), player, pos);
        });
//        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("molecularcraft:assembler"),
//                (syncId, id, player, buf) -> new AssemblerScreen(new AssemblerContainer(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),player));

        Artifice.registerAssets(id("assets").toString(), clientResourcePackBuilder -> {
            for (Atoms atom : Atoms.values()) {
                clientResourcePackBuilder.addItemModel(id(atom.getSymbol().toLowerCase()), modelBuilder -> {
                    modelBuilder.parent(id("minecraft", "item/generated"));
                    modelBuilder.texture("layer0", id("items/atom"));
                });
            }
            for (String identifier : Molecules.identifiers) {
                clientResourcePackBuilder.addItemModel(id(identifier), modelBuilder -> {
                    modelBuilder.parent(id("minecraft", "item/generated"));
                    modelBuilder.texture("layer0", id("items/molecule"));
                });
            }
        });
    }

    public static Identifier id(String path) {
        return id(MODID, path);
    }

    public static Identifier id(String modID, String path) {
        return new Identifier(modID, path);
    }
}
