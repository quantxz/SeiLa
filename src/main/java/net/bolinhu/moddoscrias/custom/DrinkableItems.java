package net.bolinhu.moddoscrias.custom;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Random;


public class DrinkableItems extends Item {
    private int DRINKS_QUANTITY = 0;
    private int DRUNK_TIMES = 0;

    private Random random = new Random();
    public DrinkableItems(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (entity instanceof Player player && !level.isClientSide) {
            IPlayerProperties data = player.getCapability(ModCapabilities.PLAYER_DATA);
            var server = player.getServer();

            if (data == null) return super.finishUsingItem(stack, level, entity);
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0));
            DRINKS_QUANTITY += 1;
            int max = Math.max(1, data.getBeerResistence());
            int min = Math.max(0, max / 4);

            int MAX_BEER = ThreadLocalRandom.current().nextInt(min, max);

            if (player.getFoodData().getFoodLevel() <= 3) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 390, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2));
            } else if (player.getFoodData().getFoodLevel() <= 6){
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 350, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 150, 2));
            }  else if (player.getFoodData().getFoodLevel() <= 10){
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
            }  else if (player.getFoodData().getFoodLevel() <= 15){
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 250, 1));
            }

            if(random.nextInt(5) == 4) {
                DRINKS_QUANTITY += 6;
            };

            if(DRINKS_QUANTITY >= 4 && data.getBeerResistence() <= 20 && DRUNK_TIMES == 0) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 250, 1));
                DRUNK_TIMES++;
            }

            if(DRUNK_TIMES == 1) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2));
                DRUNK_TIMES += 1;
            } else if(DRUNK_TIMES == 2) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 320, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, 2));
                DRUNK_TIMES += 1;
            }  else if(DRUNK_TIMES == 3) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 330, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 130, 2));
                DRUNK_TIMES += 1;
            }

            if (
                    MAX_BEER == data.getBeerResistence() ||
                            (player.getFoodData().getFoodLevel() == 0 && DRINKS_QUANTITY > 0) ||
                            DRUNK_TIMES >= 4
            )
            {
                DRUNK_TIMES = 0;
                player.removeEffect(MobEffects.CONFUSION);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 2));

                DRINKS_QUANTITY = 0;
            }

            data.setBeerResistence(2);
        }

        return super.finishUsingItem(stack, level, entity);
    }

}