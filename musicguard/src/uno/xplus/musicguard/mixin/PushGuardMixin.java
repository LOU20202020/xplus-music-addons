package uno.xplus.musicguard.mixin;

import indi.etern.musichud.network.IPlayerClient;
import indi.etern.musichud.network.payloads.pushMessages.c2s.ClientPushMusicToQueueMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uno.xplus.musicguard.Allow;

/** 拦截「把歌加入队列」，非白名单玩家直接丢弃 */
@Mixin(targets = "indi.etern.musichud.network.payloads.pushMessages.c2s.ClientPushMusicToQueueMessage$RegisterImpl", remap = false)
public class PushGuardMixin {

    @Inject(method = "lambda$register$0", at = @At("HEAD"), cancellable = true, remap = false)
    private static void musicguard$push(ClientPushMusicToQueueMessage msg, IPlayerClient player, CallbackInfo ci) {
        if (!Allow.ok(player == null ? null : player.getName())) {
            System.out.println("[MusicGuard] 拒绝点歌: " + (player == null ? "?" : player.getName()));
            ci.cancel();
        }
    }
}
