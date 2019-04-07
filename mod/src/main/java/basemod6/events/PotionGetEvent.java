package basemod6.events;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public class PotionGetEvent extends Event
{
	private AbstractPotion potion;

	public PotionGetEvent(AbstractPotion potion)
	{
		this.potion = potion;
	}

	public AbstractPotion getPotion()
	{
		return potion;
	}
}
