package club.redux.sunset.lavafishing.block.blockentity

import club.redux.sunset.lavafishing.registry.RegistryBlockEntityType
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockEntityPrometheusBounty(
    pos: BlockPos,
    state: BlockState,
) : ChestBlockEntity(
    RegistryBlockEntityType.PROMETHEUS_BOUNTY.get(),
    pos,
    state
) {
    public override fun getDefaultName(): Component {
        return Component.translatable(this.blockState.block.descriptionId)
    }
}
