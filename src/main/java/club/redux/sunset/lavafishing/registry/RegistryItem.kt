package club.redux.sunset.lavafishing.registry

import club.asynclab.web.BuildConstants
import club.redux.sunset.lavafishing.item.BlockItemWithoutLevelRenderer
import club.redux.sunset.lavafishing.item.ItemObsidianFishingRod
import club.redux.sunset.lavafishing.item.PromethiumArmor
import club.redux.sunset.lavafishing.item.fishes.*
import club.redux.sunset.lavafishing.misc.LavaArmorMaterials
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.item.SimpleItem
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

object RegistryItem {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ITEMS, BuildConstants.MOD_ID)

    @JvmField val OBSIDIAN_FISHING_ROD = REGISTER.registerKt("obsidian_fishing_rod") { ItemObsidianFishingRod() }

    // Fish
    @JvmField val QUARTZ_FISH = REGISTER.registerKt("quartz_fish") { ItemQuartzFish() }
    @JvmField val FLAME_SQUAT_LOBSTER = REGISTER.registerKt("flame_squat_lobster") { ItemFlameSquatLobster() }
    @JvmField val OBSIDIAN_SWORD_FISH = REGISTER.registerKt("obsidian_sword_fish") { ItemObsidianSwordFish() }
    @JvmField val STEAM_FLYING_FISH = REGISTER.registerKt("steam_flying_fish") { ItemSteamFlyingFish() }
    @JvmField val AROWANA_FISH = REGISTER.registerKt("arowana_fish") { ItemArowanaFish() }
    @JvmField val AGNI_FISH = REGISTER.registerKt("agni_fish") { ItemAgniFish() }

    // FISH_DATA
    // TODO
    fun registerFishData() {
        AquacultureAPI.FISH_DATA.add(QUARTZ_FISH.get(), 100.0, 200.0, 6)
        AquacultureAPI.FISH_DATA.add(FLAME_SQUAT_LOBSTER.get(), 100.0, 200.0, 6)
        AquacultureAPI.FISH_DATA.add(OBSIDIAN_SWORD_FISH.get(), 100.0, 200.0, 6)
        AquacultureAPI.FISH_DATA.add(STEAM_FLYING_FISH.get(), 100.0, 200.0, 6)
        AquacultureAPI.FISH_DATA.add(AROWANA_FISH.get(), 100.0, 200.0, 6)
        AquacultureAPI.FISH_DATA.add(AGNI_FISH.get(), 100.0, 200.0, 6)
    }

    // Armor
    @JvmField val PROMETHIUM_INGOT = REGISTER.registerKt("promethium_ingot") {
        SimpleItem(Item.Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_NUGGET = REGISTER.registerKt("promethium_nugget") {
        SimpleItem(Item.Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_HELMET = REGISTER.registerKt("promethium_helmet") {
        PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_CHESTPLATE = REGISTER.registerKt("promethium_chestplate") {
        PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.CHESTPLATE).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_LEGGINGS = REGISTER.registerKt("promethium_leggings") {
        PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2")
    }
    @JvmField val PROMETHIUM_BOOTS = REGISTER.registerKt("promethium_boots") {
        PromethiumArmor(LavaArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1")
    }

    // other
    @JvmField val BLOCK_PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") {
        BlockItemWithoutLevelRenderer(
            RegistryBlock.PROMETHEUS_BOUNTY.get(),
            Item.Properties().fireResistant()
        )
    }
}