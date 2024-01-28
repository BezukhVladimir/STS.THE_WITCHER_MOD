package TheWitcherMod.cards;

import TheWitcherMod.characters.TheWitcher;
import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDefaultCard extends CustomCard {
    private static final float PADDING = 25.0F;

    public int extraMultiDamage;
    public int baseExtraMultiDamage;
    public boolean upgradedExtraMultiDamage;
    public boolean isExtraMultiDamageModified;


    public AbstractDefaultCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardRarity rarity,
                               final CardTarget target) {
        super(
            id,
            languagePack.getCardStrings(id).NAME,
            img,
            cost,
            languagePack.getCardStrings(id).DESCRIPTION,
            type,
            TheWitcher.Enums.COLOR_GRAY,
            rarity,
            target
        );

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isExtraMultiDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();

        if (upgradedExtraMultiDamage) {
            extraMultiDamage = baseExtraMultiDamage;
            isExtraMultiDamageModified = true;
        }
    }

    public void upgradeExtraMultiDamage(int amount) {
        baseExtraMultiDamage += amount;
        extraMultiDamage = baseExtraMultiDamage;
        upgradedExtraMultiDamage = true;
    }

    public void addToDeck(AbstractCard toDeck) {
        addToBot(new AddCardToDeckAction(toDeck));
    }

    public void addToDrawPile(AbstractCard toDrawPile) {
        addToDrawPile(toDrawPile, true);
    }

    public void addToDrawPile(AbstractCard toDrawPile, boolean randomSpot) {
        addToDrawPile(toDrawPile, randomSpot,
            Settings.WIDTH / 2.0F - (PADDING * Settings.scale + AbstractCard.IMG_WIDTH),
            (float) Settings.HEIGHT / 2.0F
        );
    }

    public void addToDrawPile(AbstractCard toDrawPile, boolean randomSpot, float x, float y) {
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(
            toDrawPile, x, y, randomSpot
        ));
    }

    public void addBomb(AbstractCard bomb) {
        addToDeck(bomb.makeStatEquivalentCopy());
        addToDrawPile(bomb.makeStatEquivalentCopy());
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (this.rawDescription.contains("[thewitchermod:TargetOfTheContractIcon]")) {
            List<TooltipInfo> list = new ArrayList<>();

            TooltipInfo info = new TooltipInfo(
                "Target of the Contract [thewitchermod:TargetOfTheContractIcon]",
                GameDictionary.keywords.get("thewitchermod:target_of_the_contract")
            );

            list.add(info);

            return list;
        }

        return null;
    }
}
