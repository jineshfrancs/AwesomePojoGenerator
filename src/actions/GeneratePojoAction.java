package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ui.JsonReaderDialog;
public class GeneratePojoAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        JsonReaderDialog jsonReaderDialog=new JsonReaderDialog(anActionEvent);
        jsonReaderDialog.setVisible(true);
    }
}
