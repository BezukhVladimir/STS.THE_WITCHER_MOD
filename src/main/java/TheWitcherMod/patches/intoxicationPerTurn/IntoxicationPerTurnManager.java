package TheWitcherMod.patches.intoxicationPerTurn;

import TheWitcherMod.patches.intoxication.IntoxicationManager;
import TheWitcherMod.patches.intoxicationMax.IntoxicationMaxManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import static TheWitcherMod.TheWitcherModMain.intoxicationPerTurnBackup;

public final class IntoxicationPerTurnManager {
    private IntoxicationPerTurnManager() {
    }

    public static void reset(AbstractPlayer p) {
        Var.intoxicationPerTurn.set(p, -1);
        p.getRelic("TheWitcherMod:WolfSchoolMedallionRelic").updateDescription(p.chosenClass);
    }

    public static int get(AbstractPlayer p) {
        return Var.intoxicationPerTurn.get(p);
    }

    public static String getString(AbstractPlayer p) {
        int intoxicationPerTurn = Var.intoxicationPerTurn.get(p);

        if (intoxicationPerTurn < 0) {
            return "#b" + intoxicationPerTurn;
        } else if (intoxicationPerTurn > 0) {
            return "#r+" + intoxicationPerTurn;
        } else {
            return "#y" + intoxicationPerTurn;
        }
    }

    public static void changeValue(AbstractPlayer p, int value) {
        if (value >= 0) {
            plus(p, value);
        } else {
            minus(p, -value);
        }

        p.getRelic("TheWitcherMod:WolfSchoolMedallionRelic").updateDescription(p.chosenClass);
    }

    private static void plus(AbstractPlayer p, int value) {
        Var.intoxicationPerTurn.set(p, Math.min(get(p) + value, 999));
    }

    private static void minus(AbstractPlayer p, int value) {
        Var.intoxicationPerTurn.set(p, Math.max(get(p) - value, -999));
    }

    private static void save(AbstractPlayer p) {
        try {
            SpireConfig config = new SpireConfig(
                "theWitcherMod",
                "theWitcherModConfig",
                intoxicationPerTurnBackup
            );

            config.setInt(
                "intoxicationPerTurn",
                Var.intoxicationPerTurn.get(p)
            );
            config.save();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    private static void load(AbstractPlayer p) {
        try {
            SpireConfig config = new SpireConfig(
                "theWitcherMod",
                "theWitcherModConfig",
                intoxicationPerTurnBackup
            );

            config.load();
            Var.intoxicationPerTurn.set(
                p,
                config.getInt("intoxicationPerTurn")
            );
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        // relics
        // powers
    }

    @SpirePatch(
        clz = SaveAndContinue.class,
        method = "loadSaveFile",
        paramtypez = {AbstractPlayer.PlayerClass.class}
    )
    public static class OnLoadPrefixPatch {
        public OnLoadPrefixPatch() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> loadReader(AbstractPlayer.PlayerClass c) {
            load(AbstractDungeon.player);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = SaveAndContinue.class,
        method = "save"
    )
    public static class OnSavePrefixPatch {
        public OnSavePrefixPatch() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> saveReader(SaveFile save) {
            save(AbstractDungeon.player);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = SaveAndContinue.class,
        method = "deleteSave"
    )
    public static class OnDeleteSavePrefixPatch {
        public OnDeleteSavePrefixPatch() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> deleteSaveReader(AbstractPlayer p) {
            reset(p);
            save(p);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfCombatPreDrawLogic"
    )
    public static class PreBattlePrepPrefixPatch {
        public PreBattlePrepPrefixPatch() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> preBattlePrepReader(AbstractPlayer __instance) {
            //

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = AbstractPlayer.class,
        method = "useCard"
    )
    public static class UseCardPrefixPatch {
        public UseCardPrefixPatch() {
        }

        @SpirePrefixPatch
        public static SpireReturn<?> useCardReader(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            //

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
        clz = AbstractPlayer.class,
        method = "<class>"
    )
    private static class Var {
        public static SpireField<Integer> intoxicationPerTurn = new SpireField<>(() -> {
            return 0;
        });

        private Var() {
        }
    }
}
