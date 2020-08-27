package utils;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class EmailValidator extends ValidatorBase {
    public  EmailValidator(){
        super("Enter a valid Email");
    }
   @Override
    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl) {
            this.evalTextInputField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();
        this.hasErrors.set(true);
        if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(text)) {
            this.hasErrors.set(false);
        }

    }
}
