package net.bolinhu.moddoscrias.custom;

import net.bolinhu.moddoscrias.ModDosCrias;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {

    public static final EntityCapability<IPlayerProperties, Void> PLAYER_DATA =
            EntityCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(ModDosCrias.MOD_ID, "player_data"),
                    IPlayerProperties.class
            );

    public static void register(RegisterCapabilitiesEvent event) {
        event.registerEntity(
                PLAYER_DATA,
                EntityType.PLAYER,
                (player, ctx) -> new PlayerProperties()
        );
    }
}