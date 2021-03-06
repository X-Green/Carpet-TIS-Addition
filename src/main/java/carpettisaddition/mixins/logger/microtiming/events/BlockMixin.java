package carpettisaddition.mixins.logger.microtiming.events;

import carpettisaddition.logging.loggers.microtiming.MicroTimingLoggerManager;
import carpettisaddition.logging.loggers.microtiming.enums.BlockUpdateType;
import carpettisaddition.logging.loggers.microtiming.enums.EventType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin
{
	@Inject(method = "updateNeighborStates", at = @At("HEAD"))
	private void startStateUpdate(BlockState state, IWorld world, BlockPos pos, int flags, CallbackInfo ci)
	{
		MicroTimingLoggerManager.onBlockUpdate(world.getWorld(), pos, world.getBlockState(pos).getBlock(), BlockUpdateType.STATE_UPDATE, null, EventType.ACTION_START);
	}

	@Inject(method = "updateNeighborStates", at = @At("RETURN"))
	private void endStateUpdate(BlockState state, IWorld world, BlockPos pos, int flags, CallbackInfo ci)
	{
		MicroTimingLoggerManager.onBlockUpdate(world.getWorld(), pos, world.getBlockState(pos).getBlock(), BlockUpdateType.STATE_UPDATE, null, EventType.ACTION_END);
	}
}
