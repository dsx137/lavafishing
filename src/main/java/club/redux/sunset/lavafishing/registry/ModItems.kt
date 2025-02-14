package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.entity.EntityAmphibious
import club.redux.sunset.lavafishing.entity.EntityCommonFish
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.block.BlockItemWithoutLevelRenderer
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.cuisine.ItemSpicyFishFillet
import club.redux.sunset.lavafishing.item.fish.*
import club.redux.sunset.lavafishing.item.slingshot.ItemNeptuniumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem
import com.teammetallurgy.aquaculture.item.FishItem.SMALL_FISH_RAW
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluids
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.registries.DeferredHolder

object ModItems {
    @JvmField val REGISTER = UtilRegister.create(BuiltInRegistries.ITEM, BuildConstants.MOD_ID)

    @JvmField val OBSIDIAN_FISHING_ROD = REGISTER.registerKt("obsidian_fishing_rod") {
        AquaFishingRodItem(ModTiers.OBSIDIAN, Properties().fireResistant().durability(128))
    }
    @JvmField val NETHERITE_FISHING_ROD = REGISTER.registerKt("netherite_fishing_rod") {
        AquaFishingRodItem(Tiers.NETHERITE, Properties().fireResistant().durability(800))
    }

    // Fish
    @JvmField val FLAME_SQUAT_LOBSTER =
        registerFish("flame_squat_lobster", ::EntityAmphibious, LavaFishType.LOBSTER) { ItemFlameSquatLobster() }
    @JvmField val OBSIDIAN_SWORD_FISH =
        registerFish("obsidian_sword_fish", ::EntityCommonFish, LavaFishType.SWORDFISH) { ItemObsidianSwordFish() }
    @JvmField val STEAM_FLYING_FISH =
        registerFish("steam_flying_fish", ::EntityCommonFish, LavaFishType.COMMON) { ItemSteamFlyingFish() }
    @JvmField val AGNI_FISH =
        registerFish("agni_fish", ::EntityCommonFish, LavaFishType.COMMON) { ItemAgniFish() }
    @JvmField val AROWANA_FISH =
        registerFish("arowana_fish", ::EntityCommonFish, LavaFishType.COMMON) { ItemLavaFish() }
    @JvmField val QUARTZ_FISH =
        registerFish("quartz_fish", ::EntityCommonFish, LavaFishType.COMMON) { ItemLavaFish() }
    @JvmField val SCALY_FOOT_SNAIL =
        registerFish("scaly_foot_snail", ::EntityAmphibious, LavaFishType.SNAIL) { ItemLavaFish(SMALL_FISH_RAW) }
    @JvmField val YETI_CRAB =
        registerFish("yeti_crab", ::EntityAmphibious, LavaFishType.CRAB) { ItemLavaFish(SMALL_FISH_RAW) }
    @JvmField val LAVA_LAMPREY =
        registerFish("lava_lamprey", ::EntityCommonFish, LavaFishType.EEL) { ItemLavaFish() }

    // Food
    val SPICY_FISH_FILLET = REGISTER.registerKt("spicy_fish_fillet") { ItemSpicyFishFillet() }

    // Armor
    @JvmField val PROMETHIUM_HELMET = REGISTER.registerKt("promethium_helmet") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_CHESTPLATE = REGISTER.registerKt("promethium_chestplate") {
        ItemPromethiumArmor(
            ModArmorMaterials.PROMETHIUM,
            ArmorItem.Type.CHESTPLATE
        ).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_LEGGINGS = REGISTER.registerKt("promethium_leggings") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2")
    }
    @JvmField val PROMETHIUM_BOOTS = REGISTER.registerKt("promethium_boots") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1")
    }

    // Slingshot
    @JvmField val NEPTUNIUM_SLINGSHOT = REGISTER.registerKt("neptunium_slingshot") {
        ItemNeptuniumSlingshot()
    }
    @JvmField val PROMETHIUM_SLINGSHOT = REGISTER.registerKt("promethium_slingshot") {
        ItemPromethiumSlingshot()
    }
    @JvmField val STONE_BULLET = REGISTER.registerKt("stone_bullet") {
        ItemBullet(Tiers.STONE, Properties()) { ModEntityTypes.STONE_BULLET.get() }
    }
    @JvmField val IRON_BULLET = REGISTER.registerKt("iron_bullet") {
        ItemBullet(Tiers.IRON, Properties()) { ModEntityTypes.IRON_BULLET.get() }
    }
    @JvmField val NEPTUNIUM_BULLET = REGISTER.registerKt("neptunium_bullet") {
        ItemBullet(AquacultureAPI.MATS.NEPTUNIUM, Properties()) { ModEntityTypes.NEPTUNIUM_BULLET.get() }
    }
    @JvmField val PROMETHIUM_BULLET = REGISTER.registerKt("promethium_bullet") {
        ItemBullet(ModTiers.PROMETHIUM, Properties().fireResistant()) { ModEntityTypes.PROMETHIUM_BULLET.get() }
    }

    // other
    @JvmField val PROMETHIUM_INGOT = REGISTER.registerKt("promethium_ingot") {
        Item(Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_NUGGET = REGISTER.registerKt("promethium_nugget") {
        Item(Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_BLOCK = REGISTER.registerKt("promethium_block") {
        BlockItem(ModBlocks.PROMETHIUM_BLOCK.get(), Properties().fireResistant())
    }
    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") {
        BlockItemWithoutLevelRenderer(ModBlocks.PROMETHEUS_BOUNTY.get(), Properties().fireResistant()) {
            BlockEntityPrometheusBounty(BlockPos.ZERO, ModBlocks.PROMETHEUS_BOUNTY.get().defaultBlockState())
        }
    }

    private fun registerFish(
        name: String,
        fishConstructor: (EntityType<EntityLavaFish>, Level, LavaFishType) -> EntityLavaFish,
        fishType: LavaFishType,
        itemSupplier: () -> ItemLavaFish,
    ): DeferredHolder<Item, ItemLavaFish> {
        val fish = ModEntityTypes.register(name) {
            EntityType.Builder.of(
                { f: EntityType<EntityLavaFish>, w: Level -> fishConstructor(f, w, fishType) },
                MobCategory.WATER_AMBIENT
            ).sized(fishType.width, fishType.height).build(BuildConstants.MOD_ID + ":" + name)
        }

        //Registers fish buckets
        REGISTER.registerKt(name + "_bucket") {
            MobBucketItem(
                fish.get(),
                Fluids.LAVA,
                SoundEvents.BUCKET_EMPTY_FISH,
                Properties().stacksTo(1)
            )
        }

        return REGISTER.registerKt(name) { itemSupplier() }
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        REGISTER.entries.map { it.get() }.filterIsInstance<FishingRodItem>().forEach {
            event.enqueueWork { ClientHandler.registerFishingRodModelProperties(it) }
        }
    }
}