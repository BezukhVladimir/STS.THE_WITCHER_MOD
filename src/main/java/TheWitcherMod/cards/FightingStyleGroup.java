package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.actions.FightingStyleGroupAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class FightingStyleGroup extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(FightingStyleGroup.class.getSimpleName());
    public static final String IMG = makeCardPath("FightingStyleGroup.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1; // X
    private static final int BASE_DAMAGE = 3;
    private static final int BASE_DAMAGE_GROUP_BONUS = 1;
    private static final int UPGRADE_BASE_DAMAGE = 1;
    private static final int UPGRADE_BASE_DAMAGE_GROUP_BONUS = 1;


    public FightingStyleGroup() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        isMultiDamage = true;
        damage = baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_DAMAGE_GROUP_BONUS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new FightingStyleGroupAction(
                p, multiDamage, magicNumber, damageTypeForTurn, freeToPlayOnce, energyOnUse
            ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BASE_DAMAGE);
            upgradeMagicNumber(UPGRADE_BASE_DAMAGE_GROUP_BONUS);
            initializeDescription();
        }
    }
}
