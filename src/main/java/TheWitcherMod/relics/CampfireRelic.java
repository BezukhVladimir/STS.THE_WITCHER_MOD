package TheWitcherMod.relics;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.patches.numberOfAvailablePreparationCards.NumberOfAvailablePreparationCardsChanger;
import TheWitcherMod.patches.numberOfAvailablePreparationCards.NumberOfAvailablePreparationCardsManager;
import TheWitcherMod.patches.reputation.ReputationManager;
import TheWitcherMod.utils.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static TheWitcherMod.TheWitcherModMain.makeRelicOutlinePath;
import static TheWitcherMod.TheWitcherModMain.makeRelicPath;

public class CampfireRelic extends CustomRelic implements NumberOfAvailablePreparationCardsChanger {

    public static final String ID = TheWitcherModMain.makeID("CampfireRelic");
    public static final int BONUS = 1;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Campfire.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Campfire.png"));

    public CampfireRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onEquip() {
        NumberOfAvailablePreparationCardsManager.plus(AbstractDungeon.player, BONUS);
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void onUnequip() {
        NumberOfAvailablePreparationCardsManager.minus(AbstractDungeon.player, BONUS);
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (AbstractDungeon.player != null) {
            return DESCRIPTIONS[0] + DESCRIPTIONS[1] + ReputationManager.get(AbstractDungeon.player) + DESCRIPTIONS[2];
        }

        return DESCRIPTIONS[0];
    }

    @Override
    public void change(int value) {
    }
}
