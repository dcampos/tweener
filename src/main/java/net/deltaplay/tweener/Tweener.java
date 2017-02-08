package net.deltaplay.tweener;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

public class Tweener {

    private static int maxSize = 4;

    public static <T> ValueTween tween(TweenAccessor<T> object) {
        return tween(object, object);
    }

    public static <T> ValueTween tween(Object object, TweenAccessor<T> accessor) {
        return Pools.obtain(ValueTween.class)
                .size(maxSize).object(object).accessor(accessor);
    }

    public static SequenceTween sequence() {
        return Pools.obtain(SequenceTween.class);
    }

    public static SequenceTween sequence(Tween... tweens) {
        return Pools.obtain(SequenceTween.class).add(tweens);
    }

    public static RepeatTween repeat() {
        return Pools.obtain(RepeatTween.class);
    }

    public static RepeatTween repeat(Tween tween) {
        return Pools.obtain(RepeatTween.class).set(tween);
    }

    public static ParallelTween parallel() {
        return Pools.obtain(ParallelTween.class);
    }

    public static ParallelTween parallel(Tween... tweens) {
        return Pools.obtain(ParallelTween.class).add(tweens);
    }

    public static TimeTween delay(float delay) {
        return Pools.obtain(TimeTween.class).duration(delay);
    }

    public static void setMaxSize(int maxSize) {
        Tweener.maxSize = maxSize;
    }

    public interface TweenAccessor<T> {
        void set(T object, float[] values);
        void get(T object, float[] values);
        int getCount();
    }

    public interface Tween {
        void update(float delta);
        boolean finished();
        void restart();
    }

    public abstract static class BaseTween implements Tween, Poolable {
        boolean finished;

        @Override
        public boolean finished() {
            return finished;
        }

        @Override
        public void reset() {
            finished = false;
        }
    }

}