package TheWitcherMod.variables;

import TheWitcherMod.cards.AbstractDefaultCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static TheWitcherMod.TheWitcherModMain.makeID;

public class SecondMagicNumber extends DynamicVariable {

    private static boolean cardInHand(AbstractCard card) {
        return AbstractDungeon.player != null
            && AbstractDungeon.player.hand.contains(card)
            && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE;
    }

    @Override
    public String key() {
        return makeID("SM");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return cardInHand(card)
            && value(card) != ((AbstractDefaultCard) card).secondMagicNumber;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseSecondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedSecondMagicNumber;
    }
}
