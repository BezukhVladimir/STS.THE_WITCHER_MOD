package TheWitcherMod.cards;

import TheWitcherMod.TheWitcherModMain;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.srcCommonCardPool;

public final class MakingABomb extends AbstractDefaultCard {
    public static final String ID = TheWitcherModMain.makeID(MakingABomb.class.getSimpleName());
    public static final String IMG = TheWitcherModMain.makeCardPath("MakingABomb.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final ArrayList<AbstractCard> COMMON_BOMB_CARDS = new ArrayList<>();
    private static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    public MakingABomb() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

        for (AbstractCard card : srcCommonCardPool.group) {
            if (card.hasTag(TheWitcherModMain.THE_WITCHER_BOMB_CARD)) {
                COMMON_BOMB_CARDS.add(card);
            }
        }

        tags.add(TheWitcherModMain.THE_PREPARATION_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard made = COMMON_BOMB_CARDS.get(cardRandomRng.random(COMMON_BOMB_CARDS.size() - 1)).makeCopy();

        if (upgraded) {
            made.upgrade();
        }

        addBomb(made);
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
