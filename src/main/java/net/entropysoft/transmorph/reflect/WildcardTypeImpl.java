package net.entropysoft.transmorph.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

public final class WildcardTypeImpl implements WildcardType {

    private final Type[] ub;
    private final Type[] lb;

    public WildcardTypeImpl(Type[] ub, Type[] lb) {
        this.ub = ub;
        this.lb = lb;
    }

    public Type[] getUpperBounds() {
        return ub;
    }

    public Type[] getLowerBounds() {
        return lb;
    }

    public int hashCode() {
        return Arrays.hashCode(lb) ^ Arrays.hashCode(ub);
    }

    public boolean equals(Object obj) {
        if (obj instanceof WildcardType) {
            WildcardType that = (WildcardType) obj;
            return Arrays.equals(that.getLowerBounds(),lb)
                && Arrays.equals(that.getUpperBounds(),ub);
        }
        return false;
    }
}
