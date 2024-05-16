package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.actions.FightingStyleFastAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class FightingStyleFast extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(FightingStyleFast.class.getSimpleName());
    public static final String IMG = makeCardPath("FightingStyleFast.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1; // X
    private static final int START_DAMAGE = 2;
    private static final int BONUS_FOR_EACH_DAMAGE_ACTION = 3;
    private static final int UPGRADE_START_DAMAGE = 1;
    private static final int UPGRADE_BONUS_FOR_EACH_DAMAGE_ACTION = 2;

    public FightingStyleFast() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        damage = baseDamage = START_DAMAGE;
        magicNumber = baseMagicNumber = BONUS_FOR_EACH_DAMAGE_ACTION;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new FightingStyleFastAction(
                p, m, damage, magicNumber, damageTypeForTurn, freeToPlayOnce, energyOnUse
            ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_START_DAMAGE);
            upgradeMagicNumber(UPGRADE_BONUS_FOR_EACH_DAMAGE_ACTION);
            initializeDescription();
        }
    }
}
