package stackcalc.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stackcalc.StackCalc;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow
    protected TextFieldWidget chatField;

    @Inject(at = @At("HEAD"), method = "keyPressed(III)Z")
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable callbackInfo) {
        if (keyCode == 258) {
            String str = chatField.getText();
            if(str.endsWith("s")) {
                str = str.substring(0, str.length() - 1);
                if (!isNumeric(str)) {return;}
                chatField.setText(StackCalc.calculateStack(str));
            }
            if (str.endsWith("sb")) {
                str = str.substring(0, str.length() - 2);
                if (!isNumeric(str)) {return;}
                chatField.setText(StackCalc.calculateShulker(str));
            }
            if (str.endsWith("dc")) {
                str = str.substring(0, str.length() - 2);
                if (!isNumeric(str)) {return;}
                chatField.setText(StackCalc.calculateDouble(str));
            }
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("^[0-9]+$");
    }
}
