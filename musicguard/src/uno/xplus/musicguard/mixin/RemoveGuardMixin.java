package uno.xplus.musicguard.mixin;

import indi.etern.musichud.network.IPlayerClient;
import indi.etern.musichud.network.payloads.pushMessages.c2s.ClientRemoveMusicFromQueueMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uno.xplus.musicguard.Allow;

/** 拦截「从队列删除」，非白名单玩家不能删歌 */
@Mixin(targets = "indi.etern.musichud.network.payloads.pushMessages.c2s.ClientRemoveMusicFromQueueMessage$RegisterImpl", remap = false)
public class RemoveGuardMixin {

    @Inject(method = "lambda$register$0", at = @At("HEAD"), cancellable = true, remap = false)
    private static void musicguard$remove(ClientRemoveMusicFromQueueMessage msg, IPlayerClient player, CallbackInfo ci) {
        if (!Allow.ok(player == null ? null : player.getName())) {
            ci.cancel();
        }
    }
}
