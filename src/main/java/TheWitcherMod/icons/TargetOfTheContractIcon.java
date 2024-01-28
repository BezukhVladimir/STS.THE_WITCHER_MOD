package TheWitcherMod.icons;

import TheWitcherMod.TheWitcherModMain;
import TheWitcherMod.utils.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class TargetOfTheContractIcon extends AbstractCustomIcon {
    public static final String ID = TheWitcherModMain.makeID("TargetOfTheContract");
    private static TargetOfTheContractIcon singleton;

    public TargetOfTheContractIcon() {
        super(ID, TextureLoader.getTexture("TheWitcherModResources/images/icons/TargetOfTheContract.png"));
    }

    public static TargetOfTheContractIcon get() {
        if (singleton == null) {
            singleton = new TargetOfTheContractIcon();
        }

        return singleton;
    }
}
