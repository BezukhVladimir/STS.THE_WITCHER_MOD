package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class QuenDischarge extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(QuenDischarge.class.getSimpleName());
    public static final String IMG = makeCardPath("QuenDischarge.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int THORNS_POWER_BONUS = 3;
    private static final int UPGRADE_PLUS_THORNS_POWER = 1;

    public QuenDischarge() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = THORNS_POWER_BONUS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(
            AbstractDungeon.player,
            AbstractDungeon.player,
            new ThornsPower(AbstractDungeon.player, magicNumber),
            magicNumber
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_THORNS_POWER);
            initializeDescription();
        }
    }
}
