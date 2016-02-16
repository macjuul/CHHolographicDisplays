package me.macjuul.chhd;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.Exceptions;

public class HoloUtil {

    public static void lineGenerator(Hologram holo, Construct text) {
        try {
            CArray lines = Static.getArray(text, Target.UNKNOWN);
            for(int i = 0; i < lines.size(); i++) {
                Construct line = lines.get(i, Target.UNKNOWN);
                if((line instanceof CArray)) {
                    ItemStack item = convertItem(Static.getArray(line, Target.UNKNOWN));
                    holo.appendItemLine(item);
                } else {
                    System.out.println("Generating line: " + line.val());
                    holo.appendTextLine(line.val());
                }
            }
        } finally {
        }
    }

    public static String generateId() {
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        return String.valueOf(randomInt);
    }

    public static Location convertLocation(CArray arr) {
        Target t = Target.UNKNOWN;
        double x = Double.valueOf(arr.get(0, t).val()).doubleValue();
        double y = Double.valueOf(arr.get(1, t).val()).doubleValue();
        double z = Double.valueOf(arr.get(2, t).val()).doubleValue();
        World world = Bukkit.getWorld(arr.get(3, t).val());

        Location loc = new Location(world, x, y, z);

        return loc;
    }

    public static CArray convertLocation(Location loc) {
        Target t = Target.UNKNOWN;
        CArray arr = new CArray(t);
        arr.set("0", String.valueOf(loc.getX()));
        arr.set("1", String.valueOf(loc.getY()));
        arr.set("2", String.valueOf(loc.getZ()));
        arr.set("3", String.valueOf(loc.getWorld().getName()));
        arr.set("x", String.valueOf(loc.getX()));
        arr.set("y", String.valueOf(loc.getY()));
        arr.set("z", String.valueOf(loc.getZ()));
        arr.set("world", String.valueOf(loc.getWorld().getName()));
        return arr;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack convertItem(CArray arr) {
        Target t = Target.UNKNOWN;

        int qty = 1;
        short data = 0;
        if(!arr.containsKey("type")) {
            throw new ConfigRuntimeException("Could not find item type!", Exceptions.ExceptionType.CastException,
                    Target.UNKNOWN);
        }
        int id = Integer.valueOf(arr.get("type", t).val()).intValue();
        if(arr.containsKey("data")) {
            data = Short.valueOf(arr.get("data", t).val()).shortValue();
        }
        return new ItemStack(id, qty, data);
    }
}
