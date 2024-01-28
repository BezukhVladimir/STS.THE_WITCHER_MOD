package TheWitcherMod.cardmods;

import TheWitcherMod.TheWitcherModMain;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BombMod extends AbstractCardModifier {
    public static final String ID = TheWitcherModMain.makeID("BombMod");

    public BombMod() {
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.player.masterDeck.removeCard(
            AbstractDungeon.player.masterDeck.findCardById(card.cardID)
        );

        AbstractDungeon.player.masterDeck.refreshHandLayout();
        AbstractDungeon.player.masterDeck.stopGlowing();
    }

    public AbstractCardModifier makeCopy() {
        return new BombMod();
    }
}
