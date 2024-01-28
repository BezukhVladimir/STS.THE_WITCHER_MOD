package TheWitcherMod.powers;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.patches.reputation.ReputationManager;
import TheWitcherMod.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TargetOfTheContract extends TwoAmountPower {
    public static final String POWER_ID = TheWitcherModMain.makeID("TargetOfTheContract");
    public static final int REWARD_COEFFICIENT = 10;
    public static final int THE_CONTRACT_COMPLETE_GAIN_REP_FOR_NORMAL = 1;
    public static final int THE_CONTRACT_COMPLETE_GAIN_REP_FOR_ELITE = 3;
    public static final int THE_CONTRACT_COMPLETE_GAIN_REP_FOR_BOSS = 5;

    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture TEXTURE84 = TextureLoader.getTexture("TheWitcherModResources/images/powers/TargetOfTheContract84.png");
    private static final Texture TEXTURE32 = TextureLoader.getTexture("TheWitcherModResources/images/powers/TargetOfTheContract32.png");

    public TargetOfTheContract(final AbstractCreature monster, final int numberOfTurns) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        owner = monster;
        amount = numberOfTurns;

        if (isMinion(owner)) {
            amount2 = 0;
        } else {
            amount2 = getGoldReward(owner);
        }

        type = PowerType.DEBUFF;
        isTurnBased = true;

        region128 = new TextureAtlas.AtlasRegion(TEXTURE84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(TEXTURE32, 0, 0, 32, 32);

        updateDescription();
    }

    public static int getGoldReward(AbstractCreature monster) {
        return monster.maxHealth / REWARD_COEFFICIENT
            + ReputationManager.getPositive(AbstractDungeon.player) / REWARD_COEFFICIENT;
    }

    public void stackPower(int stackAmount) {
        amount += stackAmount;

        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    public void updateDescription() {
        if (isMinion(owner)) {
            description = POWER_STRINGS.DESCRIPTIONS[3];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount2 + POWER_STRINGS.DESCRIPTIONS[1] + getRepBonus((AbstractMonster) owner) + POWER_STRINGS.DESCRIPTIONS[2];
        }
    }

    public static boolean isMinion(AbstractCreature monster) {
        return monster.hasPower("Minion");
    }

    public static int getRepBonus(AbstractMonster monster) {
        switch (monster.type) {
            case NORMAL:
                return THE_CONTRACT_COMPLETE_GAIN_REP_FOR_NORMAL;
            case ELITE:
                return THE_CONTRACT_COMPLETE_GAIN_REP_FOR_ELITE;
            case BOSS:
                return THE_CONTRACT_COMPLETE_GAIN_REP_FOR_BOSS;
            default:
                return 0;
        }
    }

    public AbstractPower makeCopy() {
        return new TargetOfTheContract(owner, amount);
    }
}
