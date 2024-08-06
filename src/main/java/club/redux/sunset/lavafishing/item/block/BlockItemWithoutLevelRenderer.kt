package club.redux.sunset.lavafishing.item.block

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer

class BlockItemWithoutLevelRenderer(
    pBlock: Block,
    pProperties: Properties,
    val blockEntityProvider: () -> BlockEntity,
) : BlockItem(pBlock, pProperties) {
    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return object : BlockEntityWithoutLevelRenderer(
                    Minecraft.getInstance().blockEntityRenderDispatcher,
                    Minecraft.getInstance().entityModels
                ) {
                    override fun renderByItem(
                        stack: ItemStack,
                        itemDisplayContext: ItemDisplayContext,
                        matrixStack: PoseStack,
                        buffer: MultiBufferSource,
                        i: Int,
                        i1: Int,
                    ) {
                        Minecraft.getInstance().blockEntityRenderDispatcher.renderItem(
                            blockEntityProvider(), matrixStack, buffer, i, i1
                        )
                    }
                }
            }
        })
    }
}