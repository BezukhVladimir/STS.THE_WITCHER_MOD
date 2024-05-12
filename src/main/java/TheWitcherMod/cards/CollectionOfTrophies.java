package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.actions.CollectionOfTrophiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
