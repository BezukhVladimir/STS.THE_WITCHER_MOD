package TheWitcherMod.patches.numberOfAvailablePreparationCards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import java.util.Iterator;

import static TheWitcherMod.TheWitcherModMain.numberOfAvailablePreparationCardsBackup;

public final class NumberOfAvailablePreparationCardsManager {
    private NumberOfAvailablePreparationCardsManager() {
    }

    public static void reset(AbstractPlayer p) {
        Var.numberOfAvailablePreparationCards.set(p, 0);
    }

    public static int get(AbstractPlayer p) {
        return Var.numberOfAvailablePreparationCards.get(p);
    }

    public static void plus(AbstractPlayer p, int value) {
        Var.numberOfAvailablePreparationCards.set(p, get(p) + value);
    }

    public static void minus(AbstractPlayer p, int value) {
        Var.numberOfAvailablePreparationCards.set(p, get(p) - value);
    }

    private static void save(AbstractPlayer p) {
        try {
            SpireConfig config = new SpireConfig(
                "theWitcherMod",
                "theWitcherModConfig",
                numberOfAvailablePreparationCardsBackup
            );

            config.setInt(
                "numberOfAvailablePreparationCards",
                Var.numberOfAvailablePreparationCards.get(p)
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
                numberOfAvailablePreparationCardsBackup
            );

            config.load();
            Var.numberOfAvailablePreparationCards.set(
                p,
                config.getInt("numberOfAvailablePreparationCards")
            );
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        Iterator var4 = p.relics.iterator();

        while (var4.hasNext()) {
            AbstractRelic r = (AbstractRelic) var4.next();

            if (r instanceof NumberOfAvailablePreparationCardsChanger) {
                ((NumberOfAvailablePreparationCardsChanger) r).change(Var.numberOfAvailablePreparationCards.get(p));
            }
        }

        var4 = p.powers.iterator();

        while (var4.hasNext()) {
            AbstractPower power = (AbstractPower) var4.next();

            if (power instanceof NumberOfAvailablePreparationCardsChanger) {
                ((NumberOfAvailablePreparationCardsChanger) power).change(Var.numberOfAvailablePreparationCards.get(p));
            }
        }
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
        public static SpireField<Integer> numberOfAvailablePreparationCards = new SpireField<>(() -> {
            return 0;
        });

        private Var() {
        }
    }
}
