package TheWitcherMod.characters;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.cards.*;
import TheWitcherMod.relics.CampfireRelic;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static TheWitcherMod.TheWitcherModMain.*;
import static TheWitcherMod.characters.TheWitcher.Enums.COLOR_GRAY;

public class TheWitcher extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 77;
    public static final int MAX_HP = 77;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    public static final int ASCENSION_MAX_HP_LOSS = 5;
    private static final Logger LOGGER = LogManager.getLogger(TheWitcherModMain.class.getName());

    private static final String[] ORB_TEXTURES = {
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer1.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer2.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer3.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer4.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer5.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer6.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer1d.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer2d.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer3d.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer4d.png",
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/layer5d.png",
    };
    private static final String ORB_VFX_PATH =
        "TheWitcherModResources/images/char/TheWitcherCharacter/orb/vfx.png";
    private static final String ANIMATION_PATH =
        "TheWitcherModResources/images/char/TheWitcherCharacter/Spriter/TheWitcherAnimation.scml";
    private static final SpriterAnimation SPRITER_ANIMATION = new SpriterAnimation(ANIMATION_PATH);
    private static final String ID = makeID("TheWitcherCharacter");
    private static final CharacterStrings CHARACTER_STRINGS = CardCrawlGame.languagePack.getCharacterString(ID);

    public TheWitcher(String name, PlayerClass setClass) {
        super(
            name,
            setClass,
            ORB_TEXTURES,
            ORB_VFX_PATH, null,
            SPRITER_ANIMATION
        );

        initializeClass(
            null,
            THE_WITCHER_SHOULDER_2,
            THE_WITCHER_SHOULDER_1,
            THE_WITCHER_CORPSE,
            getLoadout(),
            20.0F, -10.0F, 220.0F, 290.0F,
            new EnergyManager(ENERGY_PER_TURN)
        );

        loadAnimation(
            THE_WITCHER_SKELETON_ATLAS,
            THE_WITCHER_SKELETON_JSON,
            1.0f
        );

        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
            CHARACTER_STRINGS.NAMES[0], CHARACTER_STRINGS.TEXT[0],
            STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW,
            this,
            getStartingRelics(),
            getStartingDeck(),
            false
        );
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        LOGGER.info("Begin loading starter Deck Strings");

        retVal.add(PrecisionStrike.ID);
        retVal.add(PrecisionStrike.ID);
        retVal.add(SweepingStrike.ID);
        retVal.add(SweepingStrike.ID);

        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);

        retVal.add(ContractToKill.ID);
        retVal.add(CollectionOfTrophies.ID);

        retVal.add(Vigilance.ID);
        retVal.add(ArmorUp.ID);
        retVal.add(MakingABomb.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(CampfireRelic.ID);

        UnlockTracker.markRelicAsSeen(CampfireRelic.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    @Override
    public Color getCardTrailColor() {
        return TheWitcherModMain.THE_WITCHER_COLOR;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return CHARACTER_STRINGS.NAMES[0];
    }

    // Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new PrecisionStrike();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return CHARACTER_STRINGS.NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheWitcher(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return TheWitcherModMain.THE_WITCHER_COLOR;
    }

    @Override
    public Color getSlashAttackColor() {
        return TheWitcherModMain.THE_WITCHER_COLOR;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    @Override
    public String getSpireHeartText() {
        return CHARACTER_STRINGS.TEXT[1];
    }

    @Override
    public String getVampireText() {
        return CHARACTER_STRINGS.TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_WITCHER;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
