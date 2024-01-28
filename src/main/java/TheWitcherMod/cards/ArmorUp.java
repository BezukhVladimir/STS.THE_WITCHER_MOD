package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.powers.RemoveDexterityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static TheWitcherMod.TheWitcherModMain.THE_PREPARATION_CARD;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class ArmorUp extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(ArmorUp.class.getSimpleName());
    public static final String IMG = makeCardPath("ArmorUp.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final int DEXTERITY_POWER_BONUS = 1;
    private static final int TURN_COUNTER = 4;
    private static final int UPGRADE_PLUS_TURN_COUNTER = 3;

    public ArmorUp() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        magicNumber = baseMagicNumber = TURN_COUNTER;

        tags.add(THE_PREPARATION_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(
            AbstractDungeon.player,
            AbstractDungeon.player,
            new DexterityPower(AbstractDungeon.player, DEXTERITY_POWER_BONUS),
            1
        ));

        addToBot(new ApplyPowerAction(
            AbstractDungeon.player,
            AbstractDungeon.player,
            new RemoveDexterityPower(magicNumber, DEXTERITY_POWER_BONUS),
            1
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_TURN_COUNTER);
            initializeDescription();
        }
    }
}
