package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class SweepingStrike extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(SweepingStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("SweepingStrike.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int BONUS_DAMAGE = 2;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int UPGRADE_PLUS_BONUS_DAMAGE = 1;

    public SweepingStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS_DAMAGE;

        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.currentBlock == 0) {
            addToBot(new DamageAction(
                m,
                new DamageInfo(p, damage + magicNumber, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            ));
        } else {
            addToBot(new DamageAction(
                m,
                new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            ));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeMagicNumber(UPGRADE_PLUS_BONUS_DAMAGE);
            initializeDescription();
        }
    }
}
