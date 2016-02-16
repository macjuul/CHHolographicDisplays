package me.macjuul.chhd.functions;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.gmail.filoghost.holographicdisplays.api.handler.PickupHandler;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;

import me.macjuul.chhd.CHHD;
import me.macjuul.chhd.HoloUtil;

public class HolographicDisplays
{
    public static String docs()
    {
        return "This Extension will add some sick things to CommandHelper!";
    }

    public static CommandHelperPlugin getPlugin()
    {
        return CHHD.chp;
    }

    @api
    public static class chhd_create_hologram
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            String id = null;
            CArray lines = null;
            Location loc = null;
            if (Integer.valueOf(args.length).equals(Integer.valueOf(2)))
            {
                id = HoloUtil.generateId();
                loc = HoloUtil.convertLocation(Static.getArray(args[0], t));
                lines = Static.getArray(args[1], t);
            }
            else
            {
                id = args[0].val();
                loc = HoloUtil.convertLocation(Static.getArray(args[1], t));
                lines = Static.getArray(args[2], t);
            }
            if (hololist.containsKey(id)) {
                throw new ConfigRuntimeException("That hologram ID already exists!", Exceptions.ExceptionType.CastException, t);
            }
            Hologram holo = HologramsAPI.createHologram(CHHD.chp, loc);

            hololist.put(id, holo);

            HoloUtil.lineGenerator(holo, lines);
            return new CString(id, t);
        }

        public String getName()
        {
            return "chhd_create_hologram";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(2), Integer.valueOf(3) };
        }

        public String docs()
        {
            return "String id {[string id], array location, array lines} Spawn a hologram at given location. Using an id you will be able to remove the hologram at any time.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_remove_hologram
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            String id = args[0].val();
            if (!hololist.containsKey(id)) {
                throw new ConfigRuntimeException("That hologram ID does not exists!", Exceptions.ExceptionType.CastException, t);
            }
            Hologram holo = hololist.get(id);
            holo.delete();
            hololist.remove(id);
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_remove_hologram";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(1) };
        }

        public String docs()
        {
            return "void {String id} Remove a hologram.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_list_holograms
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            CArray list = new CArray(t);
            for (String h : hololist.keySet()) {
                list.push(new CString(h, t));
            }
            return list;
        }

        public String getName()
        {
            return "chhd_list_holograms";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(0) };
        }

        public String docs()
        {
            return "array {} Lists all holograms.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_addline
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(final Target t, Environment environment, final Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            Hologram holo = hololist.get(args[0].val());
            if ((args[1] instanceof CArray))
            {
                ItemStack item = HoloUtil.convertItem(Static.getArray(args[1], Target.UNKNOWN));
                ItemLine line = holo.appendItemLine(item);
                if ((Integer.valueOf(args.length).equals(Integer.valueOf(3))) &&
                        (Integer.valueOf(args.length).equals(Integer.valueOf(3))))
                {
                    final CClosure code = (CClosure)args[2];
                    line.setPickupHandler(new PickupHandler()
                    {
                        public void onPickup(Player player)
                        {
                            CString p = new CString(player.getName(), t);
                            CString id = new CString(args[0].val(), t);
                            code.execute(new Construct[] { p, id });
                        }
                    });
                }
            }
            else
            {
                TextLine line = holo.appendTextLine(args[1].val());
                if ((Integer.valueOf(args.length).equals(Integer.valueOf(3))) &&
                        (Integer.valueOf(args.length).equals(Integer.valueOf(3))))
                {
                    final CClosure code = (CClosure)args[2];
                    line.setTouchHandler(new TouchHandler()
                    {
                        public void onTouch(Player player)
                        {
                            CString p = new CString(player.getName(), t);
                            CString id = new CString(args[0].val(), t);
                            code.execute(new Construct[] { p, id });
                        }
                    });
                }
            }
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_hologram_addline";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(2), Integer.valueOf(3) };
        }

        public String docs()
        {
            return "void {String id, String line|Array item, [Closure actionCode]} Add a line to a hologram. If an actionCode closure is given, the closure will be executed when an 'action' happens with the desired hologram. If the line is a text line, the action code will run when the hologram has been Right-clicked. If the line is a item line, the action code will be run when the item would be 'picked up'.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_removeline
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            int line = Integer.valueOf(args[1].val()).intValue();
            Hologram holo = hololist.get(args[0].val());
            if ((line < 1) || (line > holo.size())) {
                throw new ConfigRuntimeException("Invalid line number", Exceptions.ExceptionType.IndexOverflowException, t);
            }
            holo.removeLine(line);

            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_hologram_removeline";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(2) };
        }

        public String docs()
        {
            return "void {String id, Int line} Remove a line from a hologram.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_setline
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            int line = Integer.valueOf(args[1].val()).intValue();
            Hologram holo = hololist.get(args[0].val());
            if ((line < 1) || (line > holo.size())) {
                throw new ConfigRuntimeException("Invalid line number", Exceptions.ExceptionType.IndexOverflowException, t);
            }
            if ((args[1] instanceof CArray))
            {
                holo.removeLine(line);
                ItemStack item = HoloUtil.convertItem(Static.getArray(args[1], Target.UNKNOWN));
                holo.insertItemLine(line - 1, item);
            }
            else
            {
                holo.removeLine(line);
                holo.insertTextLine(line - 1, args[2].val());
            }
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_hologram_setline";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(3) };
        }

        public String docs()
        {
            return "void {String id, Int line, String text} Set a hologram line";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_insertline
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            int line = Integer.valueOf(args[1].val()).intValue();
            Hologram holo = hololist.get(args[0].val());
            if ((line < 1) || (line > holo.size())) {
                throw new ConfigRuntimeException("Invalid line number", Exceptions.ExceptionType.IndexOverflowException, t);
            }
            if ((args[1] instanceof CArray))
            {
                ItemStack item = HoloUtil.convertItem(Static.getArray(args[1], Target.UNKNOWN));
                holo.insertItemLine(Integer.valueOf(args[1].val()).intValue(), item);
            }
            else
            {
                holo.insertTextLine(Integer.valueOf(args[1].val()).intValue(), args[2].val());
            }
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_hologram_insertline";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(3) };
        }

        public String docs()
        {
            return "void {Int id, Int line, String text} Set a hologram line";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_get_hologram_loc
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            Hologram holo = hololist.get(args[0].val());
            Location loc = holo.getLocation();
            return HoloUtil.convertLocation(loc);
        }

        public String getName()
        {
            return "chhd_get_hologram_loc";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(1) };
        }

        public String docs()
        {
            return "array location {String id} Get a holograms location.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_set_hologram_loc
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            Hologram holo = hololist.get(args[0].val());
            CArray loc = Static.getArray(args[1], t);
            holo.teleport(HoloUtil.convertLocation(loc));
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_set_hologram_loc";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(2) };
        }

        public String docs()
        {
            return "void {String id, array location} Set a holograms location.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_timestamp
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            Hologram holo = hololist.get(args[0].val());
            return new CInt(holo.getCreationTimestamp(), t);
        }

        public String getName()
        {
            return "chhd_hologram_timestamp";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(1) };
        }

        public String docs()
        {
            return "int time {String id} Get the time when a hologram was created.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }

    @api
    public static class chhd_hologram_visibility
    extends AbstractFunction
    {
        public Exceptions.ExceptionType[] thrown()
        {
            return new Exceptions.ExceptionType[] { Exceptions.ExceptionType.CastException };
        }

        public boolean isRestricted()
        {
            return true;
        }

        public Boolean runAsync()
        {
            return Boolean.valueOf(false);
        }

        public Construct exec(Target t, Environment environment, Construct... args)
                throws ConfigRuntimeException
        {
            HashMap<String, Hologram> hololist = CHHD.plugin.holoMap();
            Hologram holo = hololist.get(args[0].val());
            VisibilityManager vbm = holo.getVisibilityManager();
            if (Integer.valueOf(args.length).equals(Integer.valueOf(1)))
            {
                CArray list = new CArray(t);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (vbm.isVisibleTo(p)) {
                        list.push(new CString(p.getName(), t));
                    }
                }
                return list;
            }
            if (Static.isNull(args[1]))
            {
                vbm.setVisibleByDefault(true);
            }
            else
            {
                CArray arr = Static.getArray(args[1], t);
                for (int i = 0; i < arr.size(); i++)
                {
                    vbm.setVisibleByDefault(false);
                    Player p = Bukkit.getPlayer(arr.get(i, t).val());
                    if (p != null) {
                        vbm.showTo(p);
                    } else {
                        throw new ConfigRuntimeException("The specified players (" + arr.get(i, t).val() + ") is not online", Exceptions.ExceptionType.PlayerOfflineException, t);
                    }
                }
            }
            return CVoid.VOID;
        }

        public String getName()
        {
            return "chhd_hologram_visibility";
        }

        public Integer[] numArgs()
        {
            return new Integer[] { Integer.valueOf(1), Integer.valueOf(2) };
        }

        public String docs()
        {
            return "void | player array {id | id, null | id, player array} Set or get a holograms visibility. If only id is given, will return an array of players who can see this hologram. If the second argument is null, will reset the holograms visibility (Visible for everyone). If the second argument is a player array the hologram will only be visible to those players.";
        }

        public Version since()
        {
            return CHVersion.V3_3_1;
        }
    }
}
