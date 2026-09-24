package software.bernie.geckolib.animatable.stateless;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;

public class StatelessAnimationController<T extends GeoAnimatable> extends AnimationController<T> {
    public StatelessAnimationController(T animatable, String name) {
        super(animatable, name, 0, state -> PlayState.STOP);
    }
}
