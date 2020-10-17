package carpettisaddition.logging.loggers.microtick.events;

import carpet.utils.Messenger;
import carpettisaddition.logging.loggers.microtick.MicroTickLoggerManager;
import carpettisaddition.logging.loggers.microtick.enums.EventType;
import carpettisaddition.utils.Util;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.text.BaseText;
import net.minecraft.world.ScheduledTick;

import java.util.List;
import java.util.Objects;

public class ExecuteTileTickEvent extends BaseEvent
{
	private final ScheduledTick<Block> tileTickEntry;
	public ExecuteTileTickEvent(EventType eventType, ScheduledTick<Block> tileTickEntry)
	{
		super(eventType, "execute_tile_tick");
		this.tileTickEntry = tileTickEntry;
	}

	@Override
	public BaseText toText()
	{
		List<Object> list = Lists.newArrayList();
		list.add(this.getEnclosedTranslatedBlockNameHeaderText(this.tileTickEntry.getObject()));
		list.add(COLOR_ACTION + this.tr("Execute"));
		list.add(Util.getSpaceText());
		list.add(COLOR_TARGET + this.tr("TileTick Event"));
		if (this.getEventType() == EventType.ACTION_END)
		{
			list.add(Util.getSpaceText());
			list.add(COLOR_RESULT + MicroTickLoggerManager.tr("ended"));
		}
		list.add(String.format("^w %s: %d (%s)", MicroTickLoggerManager.tr("Priority"), this.tileTickEntry.priority.getIndex(), this.tileTickEntry.priority));
		return Messenger.c(list.toArray(new Object[0]));
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof ExecuteTileTickEvent)) return false;
		if (!super.equals(o)) return false;
		ExecuteTileTickEvent that = (ExecuteTileTickEvent) o;
		return Objects.equals(tileTickEntry, that.tileTickEntry);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), tileTickEntry);
	}
}