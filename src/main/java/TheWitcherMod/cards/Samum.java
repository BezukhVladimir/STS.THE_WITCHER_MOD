package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.cardmods.BombMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

import static TheWitcherMod.TheWitcherModMain.THE_WITCHER_BOMB_CARD;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public final class Samum extends AbstractDefaultCard implements SpawnModificationCard {
    public static final String ID = TheWitcherModMain.makeID(Samum.class.getSimpleName());
    public static final String IMG = makeCardPath("Samum.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    public Samum() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        CardModifierManager.addModifier(this, new BombMod());

        exhaust = true;
        purgeOnUse = true;

        tags.add(CardTags.HEALING);
        tags.add(THE_WITCHER_BOMB_CARD);
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return false;
    }

    @Override
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {
        return SpawnModificationCard.super.replaceWith(currentRewardCards);
    }

    @Override
    public void onRewardListCreated(ArrayList<AbstractCard> rewardCards) {
        SpawnModificationCard.super.onRewardListCreated(rewardCards);
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
            new RemoveAllBlockAction(m, p)
        );

        addToBot(new ApplyPowerAction(
            m,
            p,
            new WeakPower(m, 2, false),
            1
        ));

        if (upgraded) {
            addToBot(new ApplyPowerAction(
                m,
                p,
                new VulnerablePower(m, 1, false),
                1
            ));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
