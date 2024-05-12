package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class Roach extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(Roach.class.getSimpleName());
    public static final String IMG = makeCardPath("Roach.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int WHENEVER_YOU_DRAW = 1;
    private static final int DRAW = 2;
    private static final int UPGRADE_PLUS_DRAW = 1;

    public Roach() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        magicNumber = baseMagicNumber = WHENEVER_YOU_DRAW;
        secondMagicNumber = baseSecondMagicNumber = DRAW;
    }

    public void triggerWhenDrawn() {
        this.addToBot(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(AbstractDungeon.player, this.secondMagicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DRAW);
            upgradeSecondMagicNumber(UPGRADE_PLUS_DRAW);
            initializeDescription();
        }
    }
}
