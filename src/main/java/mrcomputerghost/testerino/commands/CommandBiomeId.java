package mrcomputerghost.testerino.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CommandBiomeId extends CommandBase {

    @Override
    public String getCommandName() {
        return "biome";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/export <x1> <y1> <z1> <x2> <y2> <z2> <name>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) sender;
            p.addChatComponentMessage(new ChatComponentText("Biome Id is: " + p.worldObj.getBiomeGenForCoords((int) p.posX, (int) p.posZ).biomeID));
        }
    }

}
