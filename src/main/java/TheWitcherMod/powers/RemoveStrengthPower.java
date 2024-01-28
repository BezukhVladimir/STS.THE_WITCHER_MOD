//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package TheWitcherMod.powers;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.utils.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;


public class RemoveStrengthPower extends TwoAmountPower implements CloneablePowerInterface, NonStackablePower {
    public static final String POWER_ID = TheWitcherModMain.makeID("RemoveStrengthPower");
    private static final PowerStrings POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    private static final Texture TEXTURE84 = TextureLoader.getTexture("TheWitcherModResources/images/powers/RemoveStrengthPower84.png");
    private static final Texture TEXTURE32 = TextureLoader.getTexture("TheWitcherModResources/images/powers/RemoveStrengthPower32.png");

    public RemoveStrengthPower(int numberOfTurns, int numberOfStrength) {
        name = POWER_STRINGS.NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        amount = numberOfTurns;
        amount2 = numberOfStrength;

        type = PowerType.BUFF;
        isTurnBased = true;

        region128 = new TextureAtlas.AtlasRegion(TEXTURE84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(TEXTURE32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new ReducePowerAction(owner, owner, this, 1));

            if (amount == 1) {
                for (AbstractPower p : owner.powers) {
                    if (Objects.equals(p.ID, "Strength")) {
                        p.reducePower(1);
                        p.updateDescription();
                    }
                }
            }
        }
    }

    public void updateDescription() {
        if (amount == 1) {
            description = POWER_STRINGS.DESCRIPTIONS[0] + amount2 + POWER_STRINGS.DESCRIPTIONS[3];
        } else {
            description = POWER_STRINGS.DESCRIPTIONS[1] + amount + POWER_STRINGS.DESCRIPTIONS[2] + amount2 + POWER_STRINGS.DESCRIPTIONS[3];
        }
    }

    public AbstractPower makeCopy() {
        return new RemoveStrengthPower(amount, amount2);
    }
}
