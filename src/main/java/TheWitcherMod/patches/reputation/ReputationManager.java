package TheWitcherMod.patches.reputation;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import static TheWitcherMod.TheWitcherModMain.reputationBackup;

public final class ReputationManager {
    private ReputationManager() {
    }

    public static void reset(AbstractPlayer p) {
        Var.reputation.set(p, 0);
    }

    public static int get(AbstractPlayer p) {
        return Var.reputation.get(p);
    }

    public static int getPositive(AbstractPlayer p) {
        int value = Var.reputation.get(p);

        return Math.max(value, 0);
    }

    public static void plus(AbstractPlayer p, int value) {
        Var.reputation.set(p, Math.min(get(p) + value, 100));
        AbstractDungeon.player.getRelic("TheWitcherMod:CampfireRelic").updateDescription(AbstractDungeon.player.chosenClass);
    }

    public static void minus(AbstractPlayer p, int value) {
        Var.reputation.set(p, Math.max(get(p) - value, -100));
        AbstractDungeon.player.getRelic("TheWitcherMod:CampfireRelic").updateDescription(AbstractDungeon.player.chosenClass);
    }

    private static void save(AbstractPlayer p) {
        try {
            SpireConfig config = new SpireConfig(
                "theWitcherMod",
                "theWitcherModConfig",
                reputationBackup
            );

            config.setInt(
                "reputation",
                Var.reputation.get(p)
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
                reputationBackup
            );

            config.load();
            Var.reputation.set(
                p,
                config.getInt("reputation")
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
        public static SpireField<Integer> reputation = new SpireField<>(() -> {
            return 0;
        });

        private Var() {
        }
    }
}
