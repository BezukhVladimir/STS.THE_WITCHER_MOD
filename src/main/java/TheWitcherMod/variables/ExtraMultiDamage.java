package TheWitcherMod.variables;

import TheWitcherMod.cards.AbstractDefaultCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static TheWitcherMod.TheWitcherModMain.makeID;

public class ExtraMultiDamage extends DynamicVariable {

    private static boolean cardInHand(AbstractCard card) {
        return AbstractDungeon.player != null
            && AbstractDungeon.player.hand.contains(card)
            && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.NONE;
    }

    @Override
    public String key() {
        return makeID("MD");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return cardInHand(card)
            && AbstractDungeon.player.hasPower("Strength")
            && value(card) != ((AbstractDefaultCard) card).baseExtraMultiDamage;
    }

    @Override
    public int value(AbstractCard card) {
        int extraMultiDamage =
            ((AbstractDefaultCard) card).baseExtraMultiDamage
            + AbstractDungeon.player.getPower("Strength").amount;

        return AbstractDungeon.player.hasPower("Weakened")
            ? (int) Math.floor(extraMultiDamage * 0.75F)
            : extraMultiDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseExtraMultiDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedExtraMultiDamage;
    }
}
