package net.altdispenser.altytweaker.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.altdispenser.altytweaker.launch.AltyTweakerTweaker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URL;

@Mixin(YggdrasilMinecraftSessionService.class)
public abstract class MixinYggdrasilMinecraftSessionService {
    @Shadow
    @Final
    @Mutable
    private static URL JOIN_URL;

    private static URL ALTY_URL = HttpAuthenticationService.constantURL("https://altdispenser.net/api/client/joinserver");
    private static URL MOJANG_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");

    @Inject(method = "joinServer", at = @At("HEAD"))
    public void preJoinServer(GameProfile profile, String authenticationToken, String serverId, CallbackInfo callbackInfo) {
        JOIN_URL = AltyTweakerTweaker.SHOULD_USE_ALTDISPENSER ? ALTY_URL : MOJANG_URL;
    }
}