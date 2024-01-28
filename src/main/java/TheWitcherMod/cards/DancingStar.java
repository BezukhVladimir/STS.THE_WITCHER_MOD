package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.cardmods.BombMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static TheWitcherMod.TheWitcherModMain.THE_WITCHER_BOMB_CARD;
import static TheWitcherMod.TheWitcherModMain.makeCardPath;

public final class DancingStar extends AbstractDefaultCard implements SpawnModificationCard {
    public static final String ID = TheWitcherModMain.makeID(DancingStar.class.getSimpleName());
    public static final String IMG = makeCardPath("DancingStar.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int EXTRA_MULTI_DAMAGE = 1;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private static final int UPGRADE_PLUS_EXTRA_MULTI_DAMAGE = 1;

    public DancingStar() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        CardModifierManager.addModifier(this, new BombMod());

        isMultiDamage = true;
        damage = baseDamage = DAMAGE;
        extraMultiDamage = baseExtraMultiDamage = EXTRA_MULTI_DAMAGE;

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
        addToBot(new DamageAction(
            m,
            new DamageInfo(p, damage, damageTypeForTurn),
            AbstractGameAction.AttackEffect.FIRE
        ));

        addToBot(new DamageAllEnemiesAction(
            p,
            extraMultiDamage,
            damageTypeForTurn,
            AbstractGameAction.AttackEffect.FIRE
        ));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeExtraMultiDamage(UPGRADE_PLUS_EXTRA_MULTI_DAMAGE);
            initializeDescription();
        }
    }
}
