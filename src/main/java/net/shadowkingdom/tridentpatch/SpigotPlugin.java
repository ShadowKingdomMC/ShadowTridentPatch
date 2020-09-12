/*
 * MIT License
 *
 * Copyright (c) 2020 Trelous, Inc <https://trelous.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Written by Matthew Hogan <matt@matthogan.co.uk> September 2020
 */
package net.shadowkingdom.tridentpatch;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Matthew Hogan
 */
public class SpigotPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerRiptideEvent(PlayerRiptideEvent event) {
        ItemStack itemStack = event.getItem();

        if (!itemStack.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        if (itemMeta.hasEnchants() && itemMeta.hasEnchant(Enchantment.RIPTIDE)) {

            // If the Riptide enchant is over 3 (the vanilla maximum) remove the
            // enchant and teleport the player to the ground.
            if (itemMeta.getEnchantLevel(Enchantment.RIPTIDE) > 3) {
                itemMeta.removeEnchant(Enchantment.RIPTIDE);
                itemStack.setItemMeta(itemMeta);

                Player player = event.getPlayer();

                Block to = player.getWorld().getHighestBlockAt(player.getLocation());
                player.teleport(to.getLocation().add(0, 2, 0));
            }
        }
    }
}