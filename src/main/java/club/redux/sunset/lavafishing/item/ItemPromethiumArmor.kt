package club.redux.sunset.lavafishing.item

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.registry.ModMobEffects
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent

class ItemPromethiumArmor(
    armorMaterial: ArmorMaterial,
    type: Type,
) : ArmorItem(armorMaterial, type, Properties().fireResistant()) {

    private var texture: String? = null

    fun setArmorTexture(filename: String?): Item {
        this.texture = filename
        return this
    }

    // 这玩意居然是实时的
    override fun getArmorTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, type: String?): String {
        return BuildConstants.MOD_ID + ":textures/armor/" + this.texture + ".png"
    }

    override fun getXpRepairRatio(stack: ItemStack?): Float {
        return super.getXpRepairRatio(stack)
    }

    companion object {
        fun onLivingTick(event: LivingTickEvent) {
            val level = event.entity.level()
            for (itemStack in event.entity.armorSlots) {
                val item = itemStack.item
                if (item is ItemPromethiumArmor) {
                    val applyEffect = { effect: MobEffect ->
                        event.entity.addEffect(MobEffectInstance(effect, 20, 0, false, false, false))
                    }
                    
                    if (event.entity.isInLava || level.getBlockState(event.entity.onPos).`is`(Blocks.LAVA)) {
                        if (item.type == Type.LEGGINGS) {
                            applyEffect(MobEffects.MOVEMENT_SPEED)
                        } else if (item.type == Type.BOOTS) {
                            applyEffect(ModMobEffects.LAVA_WALKER.get())
                        }
                    }
                }
            }
        }

        fun onEntityDamage(event: LivingDamageEvent) {
            val damage = event.amount
            if (
                event.source.`is`(DamageTypes.LAVA) ||
                event.source.`is`(DamageTypes.IN_FIRE) ||
                event.source.`is`(DamageTypes.ON_FIRE)
            ) {
                var promethiumArmorCount = 0
                for (itemStack in event.entity.armorSlots) {
                    val item = itemStack.item
                    if (item is ItemPromethiumArmor) {
                        event.amount -= 1f / 4 * damage
                        promethiumArmorCount++
                    }
                }
            }
        }

        fun onEntityAttack(event: LivingAttackEvent) {
            val armorItems = event.entity.armorSlots.map { it.item }.filterIsInstance<ItemPromethiumArmor>()

            if (
                event.source.`is`(DamageTypes.LAVA) ||
                event.source.`is`(DamageTypes.IN_FIRE) ||
                event.source.`is`(DamageTypes.ON_FIRE)
            ) {
                if (armorItems.any { it.type == Type.CHESTPLATE }) {
                    event.entity.heal(0.04f)
                }
                if (armorItems.count() == 4) {
                    event.isCanceled = true
                }
            }

            if (event.source.`is`(DamageTypes.HOT_FLOOR) && armorItems.any { it.type == Type.BOOTS }) {
                if (armorItems.any { it.type == Type.CHESTPLATE }) {
                    event.entity.heal(0.04f)
                }
                event.isCanceled = true
            }
        }
    }
}