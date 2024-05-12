package TheWitcherMod.actions;

import TheWitcherMod.patches.reputation.ReputationManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class CollectionOfTrophiesAction extends AbstractGameAction {
    private final DamageInfo info;
    private static final float DURATION = 0.1F;

    public CollectionOfTrophiesAction(final AbstractCreature target, final DamageInfo damageInfo) {
        info = damageInfo;
        setValues(target, info);
        actionType = ActionType.DAMAGE;
        duration = DURATION;
    }

    @Override
    public void update() {
        if (duration == 0.1F && target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.BLUNT_HEAVY));
            target.damage(info);

            if (isFatal(target)) {
                ReputationManager.plus(AbstractDungeon.player, 1);
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }

    public static boolean isFatal(AbstractCreature m) {
        return (m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion");
    }
}
