package top.zoyn.customitem.command;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class CommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("test")) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("1")) {
				// 给这个剑增加伤害
				ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta itemMeta = sword.getItemMeta();
				itemMeta.setDisplayName("§f§l[ §a§l暴风大剑 §f§l]");
				itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
						new AttributeModifier("我是加剑的伤害的", 5D, Operation.ADD_NUMBER));
				sword.setItemMeta(itemMeta);
				
				// 给火把加个速，但是只能在副手时这个火把才能加速
				ItemStack torch = new ItemStack(Material.TORCH);
				itemMeta = torch.getItemMeta();
				itemMeta.setDisplayName("§f§l[ §a§l加速火把 §f§l]");
				itemMeta.setLore(Lists.newArrayList("§fPY又痒了? 我来给你加加速!"));;
				itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
						new AttributeModifier(UUID.randomUUID(), "我是加速火把", 0.2D, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
				torch.setItemMeta(itemMeta);

				ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
				itemMeta = helmet.getItemMeta();
				itemMeta.setDisplayName("§f§l[ §a§l适应性头盔 §f§l]");
				// 下面增加两个attribute, 但是护甲我给他只能在副手时才能起作用, 嗨呀气不气嘛
				itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,
						new AttributeModifier(UUID.randomUUID(), "我是加头盔护甲的", 10D, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
				itemMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,
						new AttributeModifier(UUID.randomUUID(), "我是加头盔最大血量的", 20D, Operation.ADD_NUMBER, EquipmentSlot.HEAD));
				helmet.setItemMeta(itemMeta);

				ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
				itemMeta = boots.getItemMeta();
				itemMeta.setDisplayName("§f§l[ §a§l有点神奇之靴 §f§l]");
				itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
						new AttributeModifier("我是给鞋子加移速的", 0.1D, Operation.ADD_NUMBER));
				boots.setItemMeta(itemMeta);
				
				player.getInventory().addItem(sword, torch, helmet, boots);
				return true;
			}

			if (args[0].equalsIgnoreCase("2")) {
				EntityEquipment equipment = player.getEquipment();
				List<ItemStack> items = Lists.newArrayList(equipment.getArmorContents());
				items.add(equipment.getItemInMainHand());
				items.add(equipment.getItemInOffHand());
				
				for (ItemStack item : items) {
					if (item == null) {
						continue;
					}
					ItemMeta itemMeta = item.getItemMeta();
					player.sendMessage("===========");
					player.sendMessage("我系憨憨: §a§l" + item.getType().name());
					itemMeta.getAttributeModifiers().forEach((attribute, modifier) -> {
						player.sendMessage("属性名: §a§l" + attribute);
						player.sendMessage("修改的内容:");
						player.sendMessage("  名字: §a§l" + modifier.getName());
						player.sendMessage("  增加的数值: §a§l" + modifier.getAmount());
						player.sendMessage("  我需要作用在: §a§l" + modifier.getSlot() + " §r才能生效");
					});
					player.sendMessage("===========");
				}
				return true;
			}
		}
		return true;
	}

}
