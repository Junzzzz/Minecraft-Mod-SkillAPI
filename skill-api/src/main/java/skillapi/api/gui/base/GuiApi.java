package skillapi.api.gui.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Jun
 * @date 2021/3/15.
 */
@SideOnly(Side.CLIENT)
public final class GuiApi extends GuiScreen {
    protected static Minecraft minecraft;
    protected static int scaleFactor;

    private BaseGui currentGui;

    public GuiApi(BaseGui currentGui) {
        this.currentGui = currentGui;
    }

    @Override
    public void initGui() {
        currentGui.width = super.width;
        currentGui.height = super.height;
        this.currentGui.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.currentGui.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Causes the screen to lay out its subcomponents again. This is the equivalent of the Java call
     * Container.validate()
     */
    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.mc = mc;
        super.fontRendererObj = mc.fontRenderer;
        super.width = width;
        super.height = height;
        GuiApi.scaleFactor = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
        GuiApi.minecraft = mc;

        this.initGui();
    }

    @Override
    protected void keyTyped(char eventCharacter, int eventKey) {
        this.currentGui.keyTyped(eventCharacter, eventKey);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        this.currentGui.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int which) {
        this.currentGui.mouseMovedOrUp(mouseX, mouseY, which);
    }

    private void switchGui(BaseGui gui) {
        this.currentGui.close();
        this.currentGui = gui;
        initGui();
    }

    public static void displayGui(BaseGui gui) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiApi) {
            ((GuiApi) mc.currentScreen).switchGui(gui);
        } else {
            mc.displayGuiScreen(new GuiApi(gui));
        }
    }

    public static void closeGui() {
        minecraft.displayGuiScreen(null);
        minecraft.setIngameFocus();
    }
}
