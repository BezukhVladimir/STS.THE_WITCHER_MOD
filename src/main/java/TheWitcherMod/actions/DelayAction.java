package TheWitcherMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayAction extends AbstractGameAction {
    public DelayAction(float duration) {
        actionType = ActionType.WAIT;
        this.duration = duration;
    }

    @Override
    public void update() {
        this.tickDuration();
    }
}
