package TheWitcherMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class FightingStyleStrongAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private int damage;
    private DamageInfo.DamageType damageTypeForTurn;
    private boolean freeToPlayOnce;
    private int energyOnUse;

    public FightingStyleStrongAction(
        final AbstractPlayer p,
        final AbstractMonster m,
        final int damage,
        final DamageInfo.DamageType damageTypeForTurn,
        final boolean freeToPlayOnce,
        final int energyOnUse
    ) {

        this.p = p;
        this.m = m;
        this.damage = damage;
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
            int totalDamage = damage * effect;
            boolean monsterHasBlock = m.currentBlock > 0;

            if (monsterHasBlock) {
                totalDamage *= 2;
            }

            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                    m,
                    new DamageInfo(p, totalDamage, damageTypeForTurn),
                    AttackEffect.SLASH_HEAVY
                )
            );

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }

        isDone = true;
    }
}
