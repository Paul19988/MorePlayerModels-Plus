package noppes.mpm.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import noppes.mpm.ModelPartData;
import noppes.mpm.client.gui.util.GuiNpcButton;
import noppes.mpm.client.gui.util.GuiNpcLabel;
import noppes.mpm.client.gui.util.GuiModelInterface;

public class GuiModelHead extends GuiModelInterface{

	private GuiScreen parent;

	private final String[] arrHead = new String[]{"gui.no","gui.yes"};
	private final String[] arrHeadwear = new String[]{"gui.no","gui.yes","part.solid"};
	private final String[] arrHair = new String[]{"gui.no","gui.player","hair.long", "hair.thin","hair.stylish","hair.ponytail"};
	private final String[] arrBeard = new String[]{"gui.no","gui.player","beard.standard","beard.viking", "beard.long", "beard.short"};
	private final String[] arrMohawk = new String[]{"gui.no","1"};
	private final String[] arrSnout = new String[]{"gui.no","snout.player_small","snout.player_medium", "snout.player_large", "snout.player_bunny",
			"snout.small", "snout.medium", "snout.large", "snout.bunny", "snout.beak"};
	private final String[] arrEars = new String[]{"gui.no","gui.player","snout.player_bunny","ears.bunny","ears.bear"};
	private final String[] arrHorns = new String[]{"gui.no", "horns.player_bull", "horns.player_antlers", "horns.player_antennab", "horns.player_antennaf", "horns.bull", "horns.antlers", "horns.antennab", "horns.antennaf"};
	public GuiModelHead(GuiScreen parent){
		this.parent = parent;
		this.xOffset = 60;
	}

    @Override
    public void initGui() {
    	super.initGui();

		int y = guiTop + 20;

		addButton(new GuiNpcButton(172, guiLeft + 50, y += 22, 70, 20, arrHead, playerdata.hideHead));
		addLabel(new GuiNpcLabel(172, "gui.hide", guiLeft, y + 5, 0xFFFFFF));

    	addButton(new GuiNpcButton(0, guiLeft + 50, y += 22, 70, 20, arrHeadwear, playerdata.headwear));
		addLabel(new GuiNpcLabel(0, "gui.headwear", guiLeft, y + 5, 0xFFFFFF));

		ModelPartData hair = playerdata.getPartData("hair");
    	addButton(new GuiNpcButton(1, guiLeft + 50, y += 22, 70, 20, arrHair, hair == null?0:hair.type + 1));
		addLabel(new GuiNpcLabel(1, "part.hair", guiLeft, y + 5, 0xFFFFFF));
		if(hair != null)
			addButton(new GuiNpcButton(11, guiLeft + 122, y, 40, 20, hair.getColor()));

		ModelPartData mohawk = playerdata.getPartData("mohawk");
    	addButton(new GuiNpcButton(2, guiLeft + 50, y += 22, 70, 20, arrMohawk, mohawk == null?0:mohawk.type));
		addLabel(new GuiNpcLabel(2, "part.mohawk", guiLeft, y + 5, 0xFFFFFF));
		if(mohawk != null)
			addButton(new GuiNpcButton(12, guiLeft + 122, y, 40, 20, mohawk.getColor()));

		ModelPartData beard = playerdata.getPartData("beard");
    	addButton(new GuiNpcButton(3, guiLeft + 50, y += 22, 70, 20, arrBeard, beard == null?0:beard.type + 1));
		addLabel(new GuiNpcLabel(3, "part.beard", guiLeft, y + 5, 0xFFFFFF));
		if(beard != null)
			addButton(new GuiNpcButton(13, guiLeft + 122, y, 40, 20, beard.getColor()));

		ModelPartData snout = playerdata.getPartData("snout");
    	addButton(new GuiNpcButton(4, guiLeft + 50, y += 22, 70, 20, arrSnout, snout == null?0:snout.type + (snout.playerTexture?1:5)));
		addLabel(new GuiNpcLabel(4, "part.snout", guiLeft, y + 5, 0xFFFFFF));
		if(snout != null)
			addButton(new GuiNpcButton(14, guiLeft + 122, y, 40, 20, snout.getColor()));

		ModelPartData ears = playerdata.getPartData("ears");
    	addButton(new GuiNpcButton(5, guiLeft + 50, y += 22, 70, 20, arrEars, getEars(ears)));
		addLabel(new GuiNpcLabel(5, "part.ears", guiLeft, y + 5, 0xFFFFFF));
		if(ears != null)
			addButton(new GuiNpcButton(15, guiLeft + 122, y, 40, 20, ears.getColor()));
		
		ModelPartData horns = playerdata.getPartData("horns");
    	addButton(new GuiNpcButton(6, guiLeft + 50, y += 22, 70, 20, arrHorns, getHorns(horns)));
		addLabel(new GuiNpcLabel(6, "part.horns", guiLeft, y + 5, 0xFFFFFF));
		if(horns != null)
			addButton(new GuiNpcButton(16, guiLeft + 122, y, 40, 20, horns.getColor()));

    }


    private int getEars(ModelPartData data) {
    	if(data == null)
    		return 0;
    	if(data.playerTexture && data.type == 0)
    		return 1;
    	if(data.playerTexture && data.type == 1)
    		return 2;
    	if(data.type == 0)
    		return 4;
    	if(data.type == 1)
    		return 3;
    	
    	return 0;
	}
    
    private int getHorns(ModelPartData data) {
    	if(data == null)
    		return 0;
    	if(data.playerTexture)
    		return data.type + 1;
    	else
    		return data.type + 5;
	}

	@Override
    protected void actionPerformed(GuiButton btn) {
    	super.actionPerformed(btn);
    	GuiNpcButton button = (GuiNpcButton) btn;
    	
    	if(button.id == 0){
    		playerdata.headwear = (byte) button.getValue();
    	}

		if(button.id == 172){
			playerdata.hideHead = (byte) button.getValue();
		}

    	if(button.id == 1){
    		if(button.getValue() == 0)
    			playerdata.removePart("hair");
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("hair");
    			if(button.getValue() > 1)
    				data.setTexture("hair/hair" + (button.getValue() - 1), button.getValue() - 1);
    		}
    		initGui();
    	}

    	if(button.id == 2){
    		if(button.getValue() == 0)
    			playerdata.removePart("mohawk");
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("mohawk");
    			if(button.getValue() > 0)
    				data.setTexture("hair/mohawk" + button.getValue(),button.getValue());
    		}
    		initGui();
    	}

    	if(button.id == 3){
    		if(button.getValue() == 0)
    			playerdata.removePart("beard");
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("beard");
    			if(button.getValue() > 1)
    				data.setTexture("beard/beard" + (button.getValue() - 1),button.getValue() - 1);
    		}
    		initGui();
    	}

    	if(button.id == 4){
    		if(button.getValue() == 0)
    			playerdata.removePart("snout");
    		else if(button.getValue() < 5){
    			ModelPartData data = playerdata.getOrCreatePart("snout");
    			data.type = (byte) (button.getValue() - 1);
    		}
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("snout");
    			byte type = 0;
    			if(button.displayString.startsWith("Medium"))
    				type = 1;
    			if(button.displayString.startsWith("Large"))
    				type = 2;
    			if(button.displayString.startsWith("Bunny"))
    				type = 3;
    			if(button.displayString.startsWith("Beak"))
    				type = 4;
    			data.setTexture("snout/" + button.displayString.toLowerCase(), type);
    		}
    		initGui();
    	}

    	if(button.id == 5){
    		int value = button.getValue();
    		if(value == 0)
    			playerdata.removePart("ears");
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("ears");
    			if(value == 1)
    				data.setTexture("", 0);
    			if(value == 2)
    				data.setTexture("", 1);
    			if(value == 3)
    				data.setTexture("ears/bunny1", 1);
    			if(value == 4)
    				data.setTexture("ears/type1", 0);
    		}
    		initGui();
    	}

    	if(button.id == 6){
    		int value = button.getValue();
    		if(value == 0)
    			playerdata.removePart("horns");
    		else{
    			ModelPartData data = playerdata.getOrCreatePart("horns");
    			if(value <= 4)
    				data.setTexture("", value - 1);
    			if(value == 5)
    				data.setTexture("horns/bull", 0);
    			if(value == 6)
    				data.setTexture("horns/antlers", 1);
    			if(value == 7)
    				data.setTexture("horns/antennas", 2);
    			if(value == 8)
    				data.setTexture("horns/antennas", 3);
    		}
    		initGui();
    	}

    	if(button.id == 11){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("hair")));
    	}
    	if(button.id == 12){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("mohawk")));
    	}
    	if(button.id == 13){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("beard")));
    	}
    	if(button.id == 14){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("snout")));
    	}
    	if(button.id == 15){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("ears")));
    	}
    	if(button.id == 16){
    		this.mc.displayGuiScreen(new GuiModelColor(this, playerdata.getPartData("horns")));
    	}
    }

    @Override
    public void close(){
        this.mc.displayGuiScreen(parent);
    }
}
