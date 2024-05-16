package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.actions.FightingStyleStrongAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class FightingStyleStrong extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(FightingStyleStrong.class.getSimpleName());
    public static final String IMG = makeCardPath("FightingStyleStrong.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1; // X
    private static final int BASE_DAMAGE = 5;
    private static final int UPGRADE_BASE_DAMAGE = 2;

    public FightingStyleStrong() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        damage = baseDamage = BASE_DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new FightingStyleStrongAction(
                p, m, damage, damageTypeForTurn, freeToPlayOnce, energyOnUse
            ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BASE_DAMAGE);
            initializeDescription();
        }
    }
}
