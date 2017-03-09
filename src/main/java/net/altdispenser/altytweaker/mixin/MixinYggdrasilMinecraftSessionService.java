package net.altdispenser.altytweaker.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.altdispenser.altytweaker.launch.AltyTweakerTweaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(YggdrasilMinecraftSessionService.class)
public abstract class MixinYggdrasilMinecraftSessionService {
    // There is probably an easier / more elegant way to do this.

    @ModifyConstant(method = "<clinit>()V", constant = @Constant(stringValue = "https://sessionserver.mojang.com/session/minecraft/join"))
    private String getJoinURL() {
        return AltyTweakerTweaker.SHOULD_USE_ALTDISPENSER ? "https://altdispenser.net/api/client/joinserver" : "https://sessionserver.mojang.com/session/minecraft/join";
    }
}