package net.altdispenser.altytweaker.launch;

import com.google.common.collect.Lists;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AltyTweakerTweaker implements ITweaker {
    private List<String> args = new ArrayList<>();
    private boolean isPrimaryTweaker;

    public static boolean SHOULD_USE_ALTDISPENSER = true; // To be set by clients which want to toggle this functionality.

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = Lists.newArrayList(args);
        this.args.add("--version");
        this.args.add(profile);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        //noinspection unchecked
        List<ITweaker> tweakers = (List<ITweaker>) Launch.blackboard.get("Tweaks");

        if (!tweakers.isEmpty()) {
            isPrimaryTweaker = tweakers.get(0) == this;
        }

        // Patch for shitty LaunchWrapper/log4j bug.
        launchClassLoader.addClassLoaderExclusion("org.apache.logging.log4j.");

        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.altytweaker.json");
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return isPrimaryTweaker ? this.args.toArray(new String[this.args.size()]) : new String[0];
    }
}
