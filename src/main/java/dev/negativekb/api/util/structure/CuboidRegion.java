package dev.negativekb.api.util.structure;

import dev.negativekb.api.util.UtilPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CuboidRegion {

    @NotNull
    private String name;
    private int priority;
    @NotNull
    private SimpleLocation position1;
    @NotNull
    private SimpleLocation position2;

    @NotNull
    public Collection<Player> getPlayers() {
        Collection<Player> collection = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> UtilPlayer.isInside(player.getLocation(), position1.toLocation(), position2.toLocation()))
                .forEach(collection::add);

        return collection;
    }

    @NotNull
    public Collection<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();

        Location loc1 = position1.toLocation();
        Location loc2 = position2.toLocation();
        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return new ArrayList<>(blocks);
    }


    @Override
    public String toString() {
        return "CuboidRegion{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", position1=" + position1 +
                ", position2=" + position2 +
                '}';
    }
}
