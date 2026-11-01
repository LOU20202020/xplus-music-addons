package uno.xplus.musiccmd;

import indi.etern.musichud.client.ui.screen.MainFragment;
import indi.etern.musichud.client.ui.screen.MusicHudScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.client.Minecraft;

/** 注册客户端指令 /music，打开 Music HUD 主界面（带日志） */
public final class MusicCmd implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("music").executes(ctx -> {
                    // 指令是在聊天框回车时执行的，此刻聊天界面还没关；
                    // 直接 setScreen 会被聊天关闭逻辑立刻顶掉。推迟一 tick 再开。
                    Minecraft.getInstance().execute(() -> open("command"));
                    return 1;
                })));
        System.out.println("[XPLUS/music] command registered");
    }

    private static void open(String from) {
        Minecraft mc = Minecraft.getInstance();
        System.out.println("[XPLUS/music] triggered from " + from);
        try {
            MainFragment fragment = MainFragment.getInstance();
            fragment.setDefaultSelectedIndex(0);
            System.out.println("[XPLUS/music] fragment=" + fragment);
            MusicHudScreen screen = MusicHudScreen.createScreen(fragment, null, null, "Music HUD");
            System.out.println("[XPLUS/music] screen=" + screen);
            mc.gui.setScreen(screen);
            System.out.println("[XPLUS/music] setScreen done");
        } catch (Throwable t) {
            System.out.println("[XPLUS/music] FAILED: " + t);
            t.printStackTrace();
        }
    }
}
