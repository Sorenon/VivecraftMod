package org.vivecraft.settings;

import org.vivecraft.ClientDataHolder;
import org.vivecraft.utils.LangHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

public class AutoCalibration
{
    public static final float defaultHeight = 1.52F;

    public static void calibrateManual()
    {
        Minecraft minecraft = Minecraft.getInstance();
        ClientDataHolder dataholder = ClientDataHolder.getInstance();
        dataholder.vrSettings.manualCalibration = (float)dataholder.vr.hmdPivotHistory.averagePosition(0.5D).y;
        int i = (int)((float)((double)Math.round(100.0D * (double)getPlayerHeight() / (double)1.52F)));
        minecraft.gui.getChat().addMessage(new TextComponent(LangHelper.get("vivecraft.messages.heightset", i)));
        dataholder.vrSettings.saveOptions();
    }

    public static float getPlayerHeight()
    {
        Minecraft minecraft = Minecraft.getInstance();
        ClientDataHolder dataholder = ClientDataHolder.getInstance();
        float f = 1.52F;

        if (dataholder.vrSettings.seated)
        {
            return f;
        }
        else
        {
            if (dataholder.vrSettings.manualCalibration != -1.0F)
            {
                f = dataholder.vrSettings.manualCalibration;
            }

            return f;
        }
    }
}
