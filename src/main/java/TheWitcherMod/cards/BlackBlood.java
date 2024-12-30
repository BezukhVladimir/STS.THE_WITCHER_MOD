package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.patches.intoxication.IntoxicationManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static TheWitcherMod.TheWitcherModMain.THE_WITCHER_POTION_CARD;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public class BlackBlood extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(BlackBlood.class.getSimpleName());
    public static final String IMG = makeCardPath("BlackBlood.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int THORNS_POWER_BONUS = 5;
    private static final int UPGRADE_PLUS_THORNS_POWER = 3;
    private static final int INTOXICATION = 25;
    private static final int UPGRADE_PLUS_INTOXICATION = 15;

    public BlackBlood() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        magicNumber = baseMagicNumber = THORNS_POWER_BONUS;
        secondMagicNumber = baseSecondMagicNumber = INTOXICATION;

        exhaust = true;
        purgeOnUse = true;

        tags.add(CardTags.HEALING);
        tags.add(THE_WITCHER_POTION_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        IntoxicationManager.changeValue(p, INTOXICATION);
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
            upgradeSecondMagicNumber(UPGRADE_PLUS_INTOXICATION);
            initializeDescription();
        }
    }
}
