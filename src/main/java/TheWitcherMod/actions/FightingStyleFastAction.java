package TheWitcherMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class FightingStyleFastAction extends AbstractGameAction {
    private float delayBetweenBeat = 0.25f;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int damage;
    private int magicNumber;
    private DamageInfo.DamageType damageTypeForTurn;
    private boolean freeToPlayOnce;
    private int energyOnUse;

    public FightingStyleFastAction(
        final AbstractPlayer p,
        final AbstractMonster m,
        final int damage,
        final int magicNumber,
        final DamageInfo.DamageType damageTypeForTurn,
        final boolean freeToPlayOnce,
        final int energyOnUse
    ) {

        this.p = p;
        this.m = m;
        this.damage = damage;
        this.magicNumber = magicNumber;
        this.damageTypeForTurn = damageTypeForTurn;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;

        actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;

        if (energyOnUse != -1) {
            effect = energyOnUse;
        }

        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(
                        m,
                        new DamageInfo(p, damage, damageTypeForTurn),
                        (i % 2 == 0)
                            ? AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                            : AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                    )
                );

                AbstractDungeon.actionManager.addToBottom(
                    new DelayAction(getDelayBetweenBeat())
                );

                damage += magicNumber;
            }

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }

        isDone = true;
    }

    private float getDelayBetweenBeat() {
        if (delayBetweenBeat > 0f) {
            delayBetweenBeat -= 0.05f;
        }

        return delayBetweenBeat;
    }
}
