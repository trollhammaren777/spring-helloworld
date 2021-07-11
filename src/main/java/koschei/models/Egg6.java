package koschei.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Egg6 {

    @Autowired
    private Needle7 needle7;

    @Autowired
    public Egg6(Needle7 needle7) {
        this.needle7 = needle7;
    }

    @Autowired
    public void setNeedle(Needle7 needle7) {
        this.needle7 = needle7;
    }

    @Override
    public String toString() {
        return ", в яйце иголка " + needle7.toString();
    }
}
