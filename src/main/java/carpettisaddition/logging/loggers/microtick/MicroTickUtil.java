package carpettisaddition.logging.loggers.microtick;

import carpet.utils.WoolTool;
import carpettisaddition.logging.loggers.microtick.types.PistonBlockEventType;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.server.world.BlockAction;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;

public class MicroTickUtil
{
	private static final Map<DyeColor, String> COLOR_STYLE = Maps.newHashMap();
	static
	{
		COLOR_STYLE.put(DyeColor.WHITE, "w");
		COLOR_STYLE.put(DyeColor.ORANGE, "d");
		COLOR_STYLE.put(DyeColor.MAGENTA, "m");
		COLOR_STYLE.put(DyeColor.LIGHT_BLUE, "c");
		COLOR_STYLE.put(DyeColor.YELLOW, "y");
		COLOR_STYLE.put(DyeColor.LIME, "l");
		COLOR_STYLE.put(DyeColor.PINK, "r");
		COLOR_STYLE.put(DyeColor.GRAY, "f");
		COLOR_STYLE.put(DyeColor.LIGHT_GRAY, "g");
		COLOR_STYLE.put(DyeColor.CYAN, "q");
		COLOR_STYLE.put(DyeColor.PURPLE, "p");
		COLOR_STYLE.put(DyeColor.BLUE, "v");
		COLOR_STYLE.put(DyeColor.BROWN, "n");
		COLOR_STYLE.put(DyeColor.GREEN, "e");
		COLOR_STYLE.put(DyeColor.RED, "r");
		COLOR_STYLE.put(DyeColor.BLACK, "k");
	}

	static String getColorStyle(DyeColor color)
	{
		return COLOR_STYLE.getOrDefault(color, "w");
	}
	static String getBooleanColor(boolean bool)
	{
		return bool ? "e" : "r";
	}

	static DyeColor getWoolColor(World world, BlockPos pos)
	{
		if (!MicroTickLoggerManager.isLoggerActivated())
		{
			return null;
		}
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		BlockPos woolPos = pos;

		if (block == Blocks.OBSERVER || block == Blocks.END_ROD ||
				block instanceof PistonBlock || block instanceof PistonExtensionBlock)
		{
			woolPos = pos.offset(state.get(FacingBlock.FACING).getOpposite());
		}
		else if (block instanceof AbstractButtonBlock || block instanceof LeverBlock)
		{
			Direction facing;
			if (state.get(WallMountedBlock.FACE) == WallMountLocation.FLOOR)
			{
				facing = Direction.UP;
			}
			else if (state.get(WallMountedBlock.FACE) == WallMountLocation.CEILING)
			{
				facing = Direction.DOWN;
			}
			else
			{
				facing = state.get(Properties.HORIZONTAL_FACING);
			}
			woolPos = pos.offset(facing.getOpposite());
		}
		else if (block == Blocks.REDSTONE_WALL_TORCH || block == Blocks.TRIPWIRE_HOOK)
		{
			woolPos = pos.offset(state.get(HorizontalFacingBlock.FACING).getOpposite());
		}
		else if (block instanceof PoweredRailBlock ||
				block == Blocks.REPEATER || block == Blocks.COMPARATOR || block == Blocks.REDSTONE_TORCH ||
				block instanceof AbstractPressurePlateBlock)  // on block
		{
			woolPos = pos.down();
		}
		else
		{
			return null;
		}

		return WoolTool.getWoolColorAtPosition(world.getWorld(), woolPos);
	}

	public static Text getTranslatedName(Block block)
	{
		Text name = new TranslatableText(block.getTranslationKey());
		name.getStyle().setColor(Formatting.WHITE);
		return name;
	}

	static String getBlockEventMessageExtra(BlockAction blockAction)
	{
		int eventID = blockAction.getType();
		int eventParam = blockAction.getData();
		return String.format("^w eventID: %d (%s)\neventParam: %d (%s)",
				eventID, PistonBlockEventType.getById(eventID), eventParam, Direction.byId(eventParam));
	}
}