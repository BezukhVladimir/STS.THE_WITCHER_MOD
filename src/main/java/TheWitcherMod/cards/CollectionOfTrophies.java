package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.actions.CollectionOfTrophiesAction;
import TheWitcherMod.patches.reputation.ReputationManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class CollectionOfTrophies extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(CollectionOfTrophies.class.getSimpleName());
    public static final String IMG = makeCardPath("CollectionOfTrophies.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DAMAGE = 3;

    public CollectionOfTrophies() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        damage = baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo damageInfo;

        if (m.hasPower("TheWitcherMod:TargetOfTheContract")) {
            damageInfo = new DamageInfo(p, damage * 2, damageTypeForTurn);
        } else {
            damageInfo = new DamageInfo(p, damage, damageTypeForTurn);
        }

        this.addToBot(new CollectionOfTrophiesAction(
            m, damageInfo
        ));
    }

    public static boolean isFatal(AbstractMonster m) {
        return (m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion");
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
