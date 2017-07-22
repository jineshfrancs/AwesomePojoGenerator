package ui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import generator.PojoGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;

public class JsonReaderDialog extends JDialog {
    private JPanel contentPane;
    private JTextArea inputField;
    private JRadioButton gsonRadioButton;
    private JRadioButton noneRadioButton;
    private JCheckBox generateGettersAndSettersCheckBox;
    private JCheckBox includeConstructorCheckBox;
    private JTextField className;
    private JButton okButton;
    private JButton cancelButton;
    private AnActionEvent anActionEvent;

    public JsonReaderDialog() {
    }

    public JsonReaderDialog(AnActionEvent actionEvent) {
        anActionEvent=actionEvent;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        int width=500,height=500;
        if(editor!=null) {
            if (editor.getComponent() != null) {
                width = editor.getComponent().getWidth();
                height = editor.getComponent().getHeight();
            }
        }
        setBounds(width/2,height/2,(width/2),(height/2));
        inputField.setVisible(true);
        inputField.setBounds(0,0,200,200);
        inputField.setAutoscrolls(true);
        inputField.setMaximumSize(new Dimension(200,200));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        pack();
    }

    private void onOK() {
        // add your code here
        VirtualFile file = (VirtualFile) anActionEvent.getDataContext().getData(LangDataKeys.VIRTUAL_FILE);
        String data="";
        try {
            data=inputField.getDocument().getText(0,inputField.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if(data.trim().equals("")){
            JOptionPane.showMessageDialog(new JFrame(),"Empty Data");
        }else {
            String text=data.substring(0,1);
            if(text.equals("{")){
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String classNameText=className.getText();
                    if(classNameText.trim().equals("")){
                        classNameText="Example";
                    }
                    boolean isAnnotationRequired=false;
                    boolean isNeedConstructor=false;
                    boolean isGetterSetterRequired=false;
                    isAnnotationRequired= gsonRadioButton.isSelected();
                    isGetterSetterRequired=generateGettersAndSettersCheckBox.isSelected();
                    isNeedConstructor=includeConstructorCheckBox.isSelected();
                    if (file != null)
                        PojoGenerator.generate(file.getCanonicalPath(),data,classNameText,isAnnotationRequired,isNeedConstructor,isGetterSetterRequired);
                    VirtualFile virtualFile = file.getParent();
                    virtualFile.refresh(true, true);
                    dispose();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(new JFrame(),e.getMessage());
                }
            }else{
                try {
                    JSONArray jsonObject = new JSONArray(data);
                    String classNameText=className.getText();
                    if(classNameText.trim().equals("")){
                        classNameText="Example";
                    }
                    boolean isAnnotationRequired=false;
                    boolean isNeedConstructor=false;
                    boolean isGetterSetterRequired=false;
                    isAnnotationRequired= gsonRadioButton.isSelected();
                    isGetterSetterRequired=generateGettersAndSettersCheckBox.isSelected();
                    isNeedConstructor=includeConstructorCheckBox.isSelected();
                    if (file != null)
                        PojoGenerator.generate(file.getCanonicalPath(),data, classNameText, isAnnotationRequired, isNeedConstructor, isGetterSetterRequired);
                    VirtualFile virtualFile = file.getParent();
                    virtualFile.refresh(true, true);
                    dispose();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(new JFrame(),e.getMessage());
                }
            }

        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        JsonReaderDialog dialog = new JsonReaderDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
