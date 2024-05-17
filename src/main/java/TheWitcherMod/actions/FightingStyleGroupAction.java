package TheWitcherMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class FightingStyleGroupAction extends AbstractGameAction {
    private float delayBetweenBeat = 0.15f;
    private AbstractPlayer p;
    private int[] multiDamage;
    private int magicNumber;
    private DamageInfo.DamageType damageTypeForTurn;
    private boolean freeToPlayOnce;
    private int energyOnUse;

    public FightingStyleGroupAction(
        final AbstractPlayer p,
        final int[] multiDamage,
        final int magicNumber,
        final DamageInfo.DamageType damageTypeForTurn,
        final boolean freeToPlayOnce,
        final int energyOnUse
    ) {

        this.p = p;
        this.multiDamage = multiDamage;
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
            int[] totalMultiDamage = getTotalMultiDamage();

            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(
                        p,
                        totalMultiDamage,
                        damageTypeForTurn,
                        (i % 2 == 0)
                            ? AttackEffect.SLASH_HORIZONTAL
                            : AttackEffect.SLASH_DIAGONAL
                    )
                );

                AbstractDungeon.actionManager.addToBottom(
                    new DelayAction(getDelayBetweenBeat())
                );
            }

            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }

        isDone = true;
    }

    private int[] getTotalMultiDamage() {
        int[] totalMultiDamage = new int[multiDamage.length];
        int groupBonusDamage = getLivingMonsters() * magicNumber;

        for (int j = 0; j < multiDamage.length; ++j) {
            totalMultiDamage[j] = multiDamage[j] + groupBonusDamage;
        }

        return totalMultiDamage;
    }

    private int getLivingMonsters() {
        int livingMonsters = 0;

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                ++livingMonsters;
            }
        }

        return livingMonsters;
    }

    private float getDelayBetweenBeat() {
        if (delayBetweenBeat > 0f) {
            delayBetweenBeat -= 0.05f;
        }

        return delayBetweenBeat;
    }
}
