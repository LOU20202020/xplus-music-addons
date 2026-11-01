package uno.xplus.musicguard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** 允许操作播放队列的玩家名单（服务器端强制） */
public final class Allow {
    private static final Set<String> NAMES = new HashSet<>(Arrays.asList(
            "LOU0122"
    ));

    public static boolean ok(String name) {
        return name != null && NAMES.contains(name);
    }

    private Allow() {}
}
