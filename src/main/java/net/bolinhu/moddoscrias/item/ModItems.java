package net.bolinhu.moddoscrias.item;

import net.bolinhu.moddoscrias.ModDosCrias;
import net.bolinhu.moddoscrias.custom.DrinkableItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModDosCrias.MOD_ID);

    public static final DeferredItem<Item> badger_essence = ITEMS.register("badger_essence",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> badger_milk = ITEMS.register("badger_milk",
            () -> new DrinkableItems(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
                    .alwaysEdible()
                    .build()
            )
            ));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
