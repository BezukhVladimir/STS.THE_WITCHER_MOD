package TheWitcherMod.relics;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.patches.intoxication.IntoxicationManager;
import TheWitcherMod.patches.intoxicationMax.IntoxicationMaxManager;
import TheWitcherMod.patches.intoxicationPerTurn.IntoxicationPerTurnManager;
import TheWitcherMod.patches.numberOfAvailablePreparationCards.NumberOfAvailablePreparationCardsManager;
import TheWitcherMod.patches.reputation.ReputationManager;
import TheWitcherMod.utils.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static TheWitcherMod.TheWitcherModMain.makeRelicOutlinePath;
import static TheWitcherMod.TheWitcherModMain.makeRelicPath;

public class WolfSchoolMedallionRelic extends CustomRelic {

    public static final String ID = TheWitcherModMain.makeID("WolfSchoolMedallionRelic");
    public static final int BONUS = 1;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WolfSchoolMedallion.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WolfSchoolMedallion.png"));

    public WolfSchoolMedallionRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);

        tips.add(new PowerTip(
            "Preparation Phase",
            GameDictionary.keywords.get("thewitchermod:preparation_phase")
        ));
        tips.add(new PowerTip(
            "Reputation",
            GameDictionary.keywords.get("thewitchermod:reputation")
        ));
        tips.add(new PowerTip(
            "Intoxication",
            GameDictionary.keywords.get("thewitchermod:intoxication")
        ));
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void onEquip() {
        NumberOfAvailablePreparationCardsManager.changeValue(AbstractDungeon.player, BONUS);
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void onUnequip() {
        NumberOfAvailablePreparationCardsManager.changeValue(AbstractDungeon.player, -BONUS);
        updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        description = getUpdatedDescription();
        tips.set(0, new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p != null) {
            return DESCRIPTIONS[0] +
                DESCRIPTIONS[1] + ReputationManager.getString(p) +
                DESCRIPTIONS[2] + IntoxicationManager.getString(p) + " / " + IntoxicationMaxManager.get(p) + " " + IntoxicationPerTurnManager.getString(p) +
                DESCRIPTIONS[3];
        }

        return DESCRIPTIONS[0];
    }
}
