package me.macjuul.chhd;

import java.util.HashMap;

import org.bukkit.Bukkit;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHHolographicDisplays")
public class CHHD extends AbstractExtension {
    HashMap<String, Hologram> holograms = new HashMap<String, Hologram>();
    public static CHHD plugin;
    public static CommandHelperPlugin chp;

    public Version getVersion() {
        return new SimpleVersion(1, 0, 0);
    }

    public void onStartup() {
        plugin = this;
        chp = CommandHelperPlugin.self;
        if(!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            System.out.println("*** HolographicDisplays is not installed or not enabled. ***");
            System.out.println("*** CHHD Will NOT function without it! ***");
            System.out.println("*** Please install HolographicDisplays in order to use this extension! ***");
        } else {
            System.out.println("CHHD " + getVersion() + " has sucessfully been enabled!");
        }
    }

    public void onShutdown() {
        for(Hologram h : holograms.values()) {
            h.delete();
        }
        holograms.clear();
        System.out.println("CHHD " + getVersion() + " has sucessfully been disabled!");
    }

    public HashMap<String, Hologram> holoMap() {
        return holograms;
    }
}
