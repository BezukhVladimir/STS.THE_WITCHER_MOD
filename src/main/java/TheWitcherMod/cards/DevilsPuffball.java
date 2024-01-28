package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.cardmods.BombMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

import static TheWitcherMod.TheWitcherModMain.THE_WITCHER_BOMB_CARD;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class DevilsPuffball extends AbstractDefaultCard implements SpawnModificationCard {
    public static final String ID = TheWitcherModMain.makeID(DevilsPuffball.class.getSimpleName());
    public static final String IMG = makeCardPath("Devil'sPuffball.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int POISON_AMOUNT = 3;
    private static final int UPGRADE_POISON_AMOUNT = 1;

    public DevilsPuffball() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        CardModifierManager.addModifier(this, new BombMod());

        isMultiDamage = true;
        magicNumber = baseMagicNumber = POISON_AMOUNT;

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
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(
                        monster,
                        p,
                        new PoisonPower(monster, p, magicNumber),
                        magicNumber
                    ));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_POISON_AMOUNT);
            initializeDescription();
        }
    }
}
