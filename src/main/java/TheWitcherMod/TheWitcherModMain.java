package TheWitcherMod;

import TheWitcherMod.cards.AbstractDefaultCard;
import TheWitcherMod.characters.TheWitcher;
import TheWitcherMod.icons.TargetOfTheContractIcon;
import TheWitcherMod.patches.intoxication.IntoxicationManager;
import TheWitcherMod.patches.intoxicationMax.IntoxicationMaxManager;
import TheWitcherMod.patches.intoxicationPerTurn.IntoxicationPerTurnChanger;
import TheWitcherMod.patches.intoxicationPerTurn.IntoxicationPerTurnManager;
import TheWitcherMod.patches.numberOfAvailablePreparationCards.NumberOfAvailablePreparationCardsManager;
import TheWitcherMod.patches.reputation.ReputationManager;
import TheWitcherMod.relics.WolfSchoolMedallionRelic;
import TheWitcherMod.utils.IDCheckDontTouchPls;
import TheWitcherMod.utils.TextureLoader;
import TheWitcherMod.variables.ExtraMultiDamage;
import TheWitcherMod.variables.SecondMagicNumber;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

import static TheWitcherMod.powers.TargetOfTheContract.*;

@SpireInitializer
public class TheWitcherModMain implements
    EditStringsSubscriber,
    EditKeywordsSubscriber,
    EditCardsSubscriber,
    EditRelicsSubscriber,
    EditCharactersSubscriber,
    PostInitializeSubscriber,
    PostDungeonInitializeSubscriber,
    OnStartBattleSubscriber,
    OnPlayerTurnStartSubscriber,
    PostBattleSubscriber,
    PostPowerApplySubscriber,
    OnPowersModifiedSubscriber,
    PostDungeonUpdateSubscriber,
    PostPotionUseSubscriber,
    MaxHPChangeSubscriber {
    public static final int THE_CONTRACT_FAIL_LOSE_REP = -5;
    private static final Logger LOGGER = LogManager.getLogger(TheWitcherModMain.class.getName());
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static final Color THE_WITCHER_COLOR = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    public static final String THE_WITCHER_SHOULDER_1 = "TheWitcherModResources/images/char/TheWitcherCharacter/shoulder.png";
    public static final String THE_WITCHER_SHOULDER_2 = "TheWitcherModResources/images/char/TheWitcherCharacter/shoulder2.png";
    public static final String THE_WITCHER_CORPSE = "TheWitcherModResources/images/char/TheWitcherCharacter/corpse.png";
    public static final String BADGE_IMAGE_THE_WITCHER_MOD = "TheWitcherModResources/images/Badge.png";
    public static final String THE_WITCHER_SKELETON_ATLAS = "TheWitcherModResources/images/char/TheWitcherCharacter/skeleton.atlas";
    public static final String THE_WITCHER_SKELETON_JSON = "TheWitcherModResources/images/char/TheWitcherCharacter/skeleton.json";
    private static final String MODNAME = "The Witcher Mod";
    private static final String AUTHOR = "Bezukh Vladimir";
    private static final String DESCRIPTION = "The Witcher Mod created by Bezukh Vladimir";
    private static final String ATTACK_THE_WITCHER = "TheWitcherModResources/images/512/bg_attack_TheWitcher.png";
    private static final String SKILL_THE_WITCHER = "TheWitcherModResources/images/512/bg_skill_TheWitcher.png";
    private static final String POWER_THE_WITCHER = "TheWitcherModResources/images/512/bg_power_TheWitcher.png";
    private static final String ENERGY_ORB_THE_WITCHER = "TheWitcherModResources/images/512/card_orb_TheWitcher.png";
    private static final String CARD_ENERGY_ORB_THE_WITCHER = "TheWitcherModResources/images/512/card_small_orb_TheWitcher.png";
    private static final String ATTACK_THE_WITCHER_PORTRAIT = "TheWitcherModResources/images/1024/bg_attack_TheWitcher.png";
    private static final String SKILL_THE_WITCHER_PORTRAIT = "TheWitcherModResources/images/1024/bg_skill_TheWitcher.png";
    private static final String POWER_THE_WITCHER_PORTRAIT = "TheWitcherModResources/images/1024/bg_power_TheWitcher.png";
    private static final String ENERGY_ORB_THE_WITCHER_PORTRAIT = "TheWitcherModResources/images/1024/card_orb_TheWitcher.png";
    private static final String THE_WITCHER_BUTTON = "TheWitcherModResources/images/charSelect/TheWitcherCharacterButton.png";
    private static final String THE_WITCHER_PORTRAIT = "TheWitcherModResources/images/charSelect/TheWitcherCharacterPortraitBG.png";
    public static TheWitcherModMain engine;
    @SpireEnum
    public static AbstractCard.CardTags THE_PREPARATION_CARD;
    @SpireEnum
    public static AbstractCard.CardTags THE_WITCHER_BOMB_CARD;
    @SpireEnum
    public static AbstractCard.CardTags THE_WITCHER_POTION_CARD;
    public static Properties theWitcherModDefaultSettings = new Properties();
    public static Properties reputationBackup = new Properties();
    public static Properties intoxicationBackup = new Properties();
    public static Properties intoxicationMaxBackup = new Properties();
    public static Properties intoxicationPerTurnBackup = new Properties();
    public static Properties numberOfAvailablePreparationCardsBackup = new Properties();
    public static boolean enablePlaceholder = true;
    private static String modID;
    private final ArrayList<AbstractCard> preparationCards = new ArrayList<>();
    private final ArrayList<AbstractMonster> targetOfTheContractMonsters = new ArrayList<>();
    private boolean isPrepared = false;

    public TheWitcherModMain() {
        LOGGER.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        setModID("TheWitcherMod");

        LOGGER.info("Done subscribing");

        LOGGER.info("Creating the color " + TheWitcher.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheWitcher.Enums.COLOR_GRAY, THE_WITCHER_COLOR, THE_WITCHER_COLOR, THE_WITCHER_COLOR,
            THE_WITCHER_COLOR, THE_WITCHER_COLOR, THE_WITCHER_COLOR, THE_WITCHER_COLOR,
            ATTACK_THE_WITCHER, SKILL_THE_WITCHER, POWER_THE_WITCHER, ENERGY_ORB_THE_WITCHER,
            ATTACK_THE_WITCHER_PORTRAIT, SKILL_THE_WITCHER_PORTRAIT, POWER_THE_WITCHER_PORTRAIT,
            ENERGY_ORB_THE_WITCHER_PORTRAIT, CARD_ENERGY_ORB_THE_WITCHER);

        LOGGER.info("Done creating the color");


        LOGGER.info("Adding mod settings");

        theWitcherModDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig(
                "theWitcherMod",
                "theWitcherModConfig",
                theWitcherModDefaultSettings
            );

            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("Done adding mod settings");
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = TheWitcherModMain.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls exceptionStrings = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        LOGGER.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(exceptionStrings.DEFAULTID)) {
            throw new RuntimeException(exceptionStrings.EXCEPTION);
        } else if (ID.equals(exceptionStrings.DEVID)) {
            modID = exceptionStrings.DEFAULTID;
        } else {
            modID = ID;
        }

        LOGGER.info("Success! ID is " + modID);
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = TheWitcherModMain.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls exceptionStrings = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = TheWitcherModMain.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(exceptionStrings.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(exceptionStrings.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(exceptionStrings.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }

    public static void initialize() {
        LOGGER.info("= Initializing The Witcher Mod =");

        if (engine == null) {
            engine = new TheWitcherModMain();
        }

        LOGGER.info("= The Witcher Mod initialized =");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static String getPreparationPhaseText() {
        int count = NumberOfAvailablePreparationCardsManager.get(AbstractDungeon.player);

        if (count == 1) {
            return "The Preparation Phase (select 1 card)";
        } else {
            return "The Preparation Phase (select " + count + " cards)";
        }
    }

    @Override
    public void receiveEditStrings() {
        LOGGER.info("You seeing this?");
        LOGGER.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Character-Strings.json");

        BaseMod.loadCustomStringsFile(OrbStrings.class,
            getModID() + "Resources/localization/eng/TheWitcherMod-Orb-Strings.json");

        LOGGER.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TheWitcherMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }

        LOGGER.info("Done editing keywords");
    }

    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(TargetOfTheContractIcon.get());

        pathCheck();

        BaseMod.addDynamicVariable(new ExtraMultiDamage());
        BaseMod.addDynamicVariable(new SecondMagicNumber());

        LOGGER.info("Adding cards");

        new AutoAdd("TheWitcherMod")
            .packageFilter(AbstractDefaultCard.class)
            .setDefaultSeen(true)
            .cards();

        LOGGER.info("Done adding cards!");
    }

    @Override
    public void receiveEditRelics() {
        LOGGER.info("Adding relics");

        BaseMod.addRelicToCustomPool(new WolfSchoolMedallionRelic(), TheWitcher.Enums.COLOR_GRAY);

        LOGGER.info("Done adding relics!");
    }

    @Override
    public void receiveEditCharacters() {
        LOGGER.info("Beginning to edit characters. " + "Add " + TheWitcher.Enums.THE_WITCHER.toString());

        BaseMod.addCharacter(new TheWitcher("the Witcher", TheWitcher.Enums.THE_WITCHER),
            THE_WITCHER_BUTTON, THE_WITCHER_PORTRAIT, TheWitcher.Enums.THE_WITCHER);

        LOGGER.info("Added " + TheWitcher.Enums.THE_WITCHER.toString());
    }

    @Override
    public void receivePostInitialize() {
        LOGGER.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE_THE_WITCHER_MOD);

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
            350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
            enablePlaceholder,
            settingsPanel,
            (label) -> {
            },
            (button) -> {
                enablePlaceholder = button.enabled;
                try {
                    SpireConfig config = new SpireConfig("theWitcherMod", "theWitcherModConfig", theWitcherModDefaultSettings);
                    config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                    config.save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        LOGGER.info("Done loading badge Image and mod options");
    }

    @Override
    public void receivePostDungeonInitialize() {
        AbstractPlayer p = AbstractDungeon.player;

        contractSystemReceivePostDungeonInitialize(p);
        intoxicationSystemReceivePostDungeonInitialize(p);
    }

    private static void contractSystemReceivePostDungeonInitialize(AbstractPlayer p) {
        ReputationManager.reset(p);
    }

    private static void intoxicationSystemReceivePostDungeonInitialize(AbstractPlayer p) {
        IntoxicationManager.reset(p);
        IntoxicationMaxManager.reset(p);
        IntoxicationPerTurnManager.reset(p);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        preparationPhaseReceiveOnBattleStart();
        contractSystemReceiveOnBattleStart();
    }

    private void preparationPhaseReceiveOnBattleStart() {
        isPrepared = false;
        preparationCards.clear();

        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.hasTag(THE_PREPARATION_CARD)) {
                preparationCards.add(card);
                AbstractDungeon.player.drawPile.removeCard(card.cardID);
            }
        }
    }

    private void contractSystemReceiveOnBattleStart() {
        targetOfTheContractMonsters.clear();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        preparationPhaseReceiveOnPlayerTurnStart();
        intoxicationSystemReceiveOnPlayerTurnStart();
    }

    private void preparationPhaseReceiveOnPlayerTurnStart() {
        if (!isPrepared) {
            SelectCardsAction preparation = new SelectCardsAction(
                preparationCards, NumberOfAvailablePreparationCardsManager.get(AbstractDungeon.player), getPreparationPhaseText(), true, card -> card.hasTag(THE_PREPARATION_CARD),
                abstractCards -> {
                    for (AbstractCard card : abstractCards) {
                        card.use(AbstractDungeon.player, null);
                        card.stopGlowing();
                    }
                }
            );

            AbstractDungeon.actionManager.addToBottom(preparation);
            isPrepared = true;
        }
    }

    private void intoxicationSystemReceiveOnPlayerTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;

        intoxicationSystemLoseHP(p);
        intoxicationSystemChangeIntoxication(p);
    }

    private static void intoxicationSystemLoseHP(AbstractPlayer p) {
        int intoxication = IntoxicationManager.get(p);
        int intoxicationDamage = Math.max(
            intoxication - IntoxicationMaxManager.get(p), 0
        );

        if (intoxicationDamage > 0) {
            AbstractDungeon.actionManager.addToTop(
                new LoseHPAction(p, p, intoxicationDamage)
            );
        }
    }

    private static void intoxicationSystemChangeIntoxication(AbstractPlayer p) {
        IntoxicationManager.changeValue(
            p,
            IntoxicationPerTurnManager.get(p)
        );
    }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        preparationPhaseReceivePostBattle();
    }

    private void preparationPhaseReceivePostBattle() {
        isPrepared = false;
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        contractSystemReceivePostPowerApplySubscriber(power, target);
    }

    private void contractSystemReceivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target) {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom() != null) {
            if (Objects.equals(power.ID, "TheWitcherMod:TargetOfTheContract")) {
                if (!targetOfTheContractMonsters.contains((AbstractMonster) target)) {
                    targetOfTheContractMonsters.add((AbstractMonster) target);
                }
            }
        }
    }

    @Override
    public void receivePowersModified() {
        contractSystemReceivePowersModified();
    }

    private void contractSystemReceivePowersModified() {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom() != null && !targetOfTheContractMonsters.isEmpty()) {
            ArrayList<Integer> ids = new ArrayList<>();

            for (int id = 0; id < targetOfTheContractMonsters.size(); ++id) {
                if (!targetOfTheContractMonsters.get(id).hasPower("TheWitcherMod:TargetOfTheContract")) {
                    ids.add(id);
                }
            }

            for (int id : ids) {
                if (!isMinion(targetOfTheContractMonsters.get(id))) {
                    ReputationManager.changeValue(AbstractDungeon.player, THE_CONTRACT_FAIL_LOSE_REP);
                }

                targetOfTheContractMonsters.remove(id);
            }
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        contractSystemReceivePostDungeonUpdate();
    }

    private void contractSystemReceivePostDungeonUpdate() {
        if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.getCurrRoom() != null && !targetOfTheContractMonsters.isEmpty()) {
            ArrayList<Integer> ids = new ArrayList<>();

            for (int id = 0; id < targetOfTheContractMonsters.size(); ++id) {
                if (targetOfTheContractMonsters.get(id).isDying) {
                    ids.add(id);
                }
            }

            for (int id : ids) {
                gainGoldReward(id);

                if (!isMinion(targetOfTheContractMonsters.get(id))) {
                    ReputationManager.changeValue(AbstractDungeon.player, getRepBonus(targetOfTheContractMonsters.get(id)));
                }

                targetOfTheContractMonsters.remove(id);
            }

            ids.clear();

            if (!targetOfTheContractMonsters.isEmpty()) {
                for (int id = 0; id < targetOfTheContractMonsters.size(); ++id) {
                    if (!isMinion(targetOfTheContractMonsters.get(id))) {
                        if (targetOfTheContractMonsters.get(id).isEscaping) {
                            ids.add(id);
                        }
                    }
                }
            }

            for (int id : ids) {
                ReputationManager.changeValue(AbstractDungeon.player, THE_CONTRACT_FAIL_LOSE_REP);
                targetOfTheContractMonsters.remove(id);
            }
        }
    }

    private void gainGoldReward(int id) {
        AbstractMonster monster = targetOfTheContractMonsters.get(id);
        int goldReward = getGoldReward(monster);

        AbstractDungeon.player.gainGold(goldReward);
        for (int i = 0; i < goldReward; ++i) {
            AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, monster.hb.cX, monster.hb.cY, monster.hb.cX, monster.hb.cY, true));
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
    }

    @Override
    public void receivePostPotionUse(AbstractPotion potion) {
        contractSystemReceivePostPotionUse(potion);
    }

    private void contractSystemReceivePostPotionUse(AbstractPotion potion) {
        if (Objects.equals(potion.ID, "SmokeBomb") && AbstractDungeon.player.isEscaping) {
            for (AbstractMonster monster : targetOfTheContractMonsters) {
                if (!isMinion(monster)) {
                    ReputationManager.changeValue(AbstractDungeon.player, THE_CONTRACT_FAIL_LOSE_REP);
                }
            }

            targetOfTheContractMonsters.clear();
        }
    }

    @Override
    public int receiveMaxHPChange(int amount) {
        IntoxicationMaxManager.reset(AbstractDungeon.player, amount);
        return amount;
    }
}
