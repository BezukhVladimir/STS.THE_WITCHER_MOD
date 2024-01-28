package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.powers.TargetOfTheContract;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class ContractToKill extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(ContractToKill.class.getSimpleName());
    public static final String IMG = makeCardPath("ContractToKill.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int NUMBER_OF_TURNS = 5;
    private static final int UPGRADE_COST = 0;

    public ContractToKill() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        magicNumber = baseMagicNumber = NUMBER_OF_TURNS;

        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(
            m,
            p,
            new TargetOfTheContract(m, magicNumber)
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
