package algo_files;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 26.06.13
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
import algo_general.*;
import algo_general.PopupMenu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;


public class FrameCreateAlgo extends JFrame {

    class ReportWindow extends JFrame{
        private JTextArea text;
        private JScrollPane scrollPane;
        private JButton buttonOK;
        public ReportWindow(String reportText){

            new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize(500,250);
            setResizable(false);
            setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().setLayout(null);
            setResizable(true);

            initTextArea(reportText);
            initButton();
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    super.componentResized(e);
                    scrollPane.setBounds(10, 10, getWidth() - 25, getHeight() - 90);
                    buttonOK.setLocation(getWidth()/2-buttonOK.getWidth()/2, getHeight()-65);
                }
            });
        }
        private void initTextArea(String reportText){
            text = new JTextArea(reportText);
            scrollPane = new JScrollPane(text);
            scrollPane.setBounds(10, 10, getWidth() - 25, getHeight() - 90);
            this.add(scrollPane);
        }
        private void initButton(){
            buttonOK = new JButton("OK");
            buttonOK.setSize(Main.buttonSize);
            buttonOK.setLocation(getWidth() / 2 - buttonOK.getWidth() / 2, getHeight() - 65);
            buttonOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            this.add(buttonOK);
        }
    }
    private JTextArea textArea;
    private JTextField fieldNameFile;
    private JTextField fieldCommandLine;
    private JPopupMenu mainPopupMenu;
    private File openedFile;
    private String activField;

    public FrameCreateAlgo(){
        super("Create algo...");
        initialize();
    }
    private void initialize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new JFrame();
        setSize(700,600);
        setResizable(false);
        setLocation(screenSize.width/2-getWidth()/2, screenSize.height/2-getHeight()/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);


        initPopupMenu();
        initTextArea();
        initTextField();
        initButton();

        activField = "";
//        getSelectedText();
//        addText();
//        getContentPane().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                if (e.getButton() == MouseEvent.BUTTON3)
//                    p = e.getPoint();
//
//
//            }
//        });
        setResizable(true);

    }

    private void initTextArea(){
        textArea = new JTextArea();


        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setSize(getWidth()-325, getHeight()-120);
        textAreaScrollPane.setLocation(310, 20);
        add(textAreaScrollPane);



        textArea.addMouseListener(new PopupMenu(mainPopupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                super.maybeShowPopup(e);
                activField = "textArea";

            }
        });


    }
    private void initTextField(){

        Dimension dimensionTextField = new Dimension(290,24);

        JLabel nameFile = new JLabel ("File name");
        nameFile.setBounds(10, 40, 100, 24);
        fieldNameFile = new JTextField();
        fieldNameFile.setSize(dimensionTextField);
        fieldNameFile.setLocation(nameFile.getX(),nameFile.getY()+nameFile.getHeight()+5);

        JLabel labelCompile = new JLabel("Command line");
        labelCompile.setBounds (fieldNameFile.getX(), fieldNameFile.getY()+100, 200, 24);
        fieldCommandLine = new JTextField();
        fieldCommandLine.setSize(dimensionTextField);
        fieldCommandLine.setLocation(labelCompile.getX(), labelCompile.getY()+labelCompile.getHeight()+5);

        add(nameFile);
        add(fieldNameFile);
        add(labelCompile);
        add(fieldCommandLine);

        fieldNameFile.addMouseListener(new PopupMenu(mainPopupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                super.maybeShowPopup(e);
                activField = "fieldNameFile";

            }
        });

        fieldCommandLine.addMouseListener(new PopupMenu(mainPopupMenu){
            @Override
            public void maybeShowPopup(MouseEvent e) {
                super.maybeShowPopup(e);
                activField = "fieldCommandLine";

            }
        });


    }

    private void initButton(){
        JButton buttonExit = new JButton("Exit");
        buttonExit.setSize(Main.buttonSize);
        buttonExit.setLocation(getWidth()-135, getHeight()-65);
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        JButton buttonOpenSource = new JButton("...");
        buttonOpenSource.setSize(Main.buttonSize.width/2,Main.buttonSize.height);
        buttonOpenSource.setLocation(fieldNameFile.getX()+fieldNameFile.getWidth()-buttonOpenSource.getWidth(),
                fieldNameFile.getY()+fieldNameFile.getHeight()+10);
        buttonOpenSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openedFile = openSourceFile();
                if(openedFile != null){
                    readFromFile(openedFile);
                    File pathToSource = new File("").getAbsoluteFile();
                    fieldCommandLine.setText("javac -sourcepath " + pathToSource+"/src "
                            + openedFile.getAbsolutePath());
                }
            }
        });

        JButton buttonSaveToFile = new JButton("Save");
        buttonSaveToFile.setSize(Main.buttonSize.width,Main.buttonSize.height);
        buttonSaveToFile.setLocation(fieldNameFile.getX()+fieldNameFile.getWidth()-buttonSaveToFile.getWidth(),
                buttonOpenSource.getY()+buttonOpenSource.getHeight()+10);
        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparingNewAlgos.saveSourceToFile(openedFile,textArea.getText());

            }
        });

        JButton buttonCompile = new JButton("Compile");
        buttonCompile.setSize(Main.buttonSize.width,Main.buttonSize.height);
        buttonCompile.setLocation(fieldCommandLine.getX()+fieldCommandLine.getWidth()-buttonCompile.getWidth(),
                fieldCommandLine.getY()+buttonCompile.getHeight()+10);
        buttonCompile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               showReport();
            }
        });

        add(buttonExit);
        add(buttonOpenSource);
        add(buttonSaveToFile);
        add(buttonCompile);

    }

//    private void addText(){
//        fieldNameFile.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusLost(FocusEvent e) {
//                super.focusLost(e);
//                if(!fieldNameFile.getText().trim().equals(""))
//                    textArea.insert("public class "+fieldNameFile.getText().trim()+" implements ImportingModule {\n\n\n\n}",0);
//
//
//            }
//        });
//    }
private void initPopupMenu(){
    mainPopupMenu = new JPopupMenu();
    ImageIcon itemCopyIcon = new ImageIcon(Main.ICO_PATH + "page_copy.png");
    JMenuItem itemCopy = new JMenuItem("  Copy    ", itemCopyIcon);
    itemCopy.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            copyToClipboard(getSelectedText());
        }
    });
    ImageIcon itemCutIcon = new ImageIcon(Main.ICO_PATH + "cut.png");
    JMenuItem itemCut = new JMenuItem("  Cut    ", itemCutIcon);
    itemCut.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            cutText(getSelectedText());
        }
    });
    ImageIcon itemPasteIcon = new ImageIcon(Main.ICO_PATH + "page_paste.png");
    JMenuItem itemPaste = new JMenuItem("  Past    ", itemPasteIcon);
    itemPaste.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            pasteText();
        }
    });

    mainPopupMenu.add(itemCopy);
    mainPopupMenu.add(itemCut);
    mainPopupMenu.add(itemPaste);

}

    private void showReport(){
        String reportText = PreparingNewAlgos.compileAlgorythm(fieldCommandLine.getText());
        try{
            if (reportText == null)
                throw new Exception();
            if(reportText.equals("Done")){
                JOptionPane.showMessageDialog(this,
                        "Compile done!" , "Compile", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (reportText != null){
                ReportWindow r = new ReportWindow(reportText);
                r.setVisible(true);
            }
        }catch (Exception ex){}
    }
    private void pasteText(){
        if(activField.equals("textArea"))
            textArea.insert(pasteFromClipboard(),textArea.getCaretPosition());
        if(activField.equals("fieldNameFile"))
            fieldNameFile.setText(pasteFromClipboard());
        if(activField.equals("fieldCommandLine"))
            fieldCommandLine.setText(pasteFromClipboard());
    }

    private String getSelectedText(){
        String text = "";
        if(activField.equals("textArea"))
            text = textArea.getSelectedText();
        if(activField.equals("fieldNameFile"))
            text = fieldNameFile.getSelectedText();
        if(activField.equals("fieldCommandLine"))
            text = fieldCommandLine.getSelectedText();
        return text;
    }
    private void cutText(String text){
        copyToClipboard(text);
        if(activField.equals("textArea")){

        }
        if(activField.equals("fieldNameFile")){

        }
        if(activField.equals("fieldCommandLine")){

        }


    }
    private void copyToClipboard(String selectString){
        if (selectString != null){
            StringSelection stringSelection = new StringSelection (selectString);
            Clipboard clipboard = Toolkit.getDefaultToolkit ().getSystemClipboard ();
            clipboard.setContents (stringSelection, null);
        }
    }
    private String pasteFromClipboard(){
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                ;
        if ( hasTransferableText ) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException ex){
                //highly unlikely since we are using a standard DataFlavor
                System.out.println(ex);
                ex.printStackTrace();
            }
            catch (IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }

    private File openSourceFile(){
        File javaFile = null;
        JFileChooser openDialog = new JFileChooser();
        FileNameExtensionFilter openFilter = new FileNameExtensionFilter("Java source file *.java","java");
        openDialog.setFileFilter(openFilter);
        int ret = openDialog.showDialog(null, "Open");
        if(ret == JFileChooser.APPROVE_OPTION)
            javaFile = openDialog.getSelectedFile();
        return javaFile;
    }
    private void readFromFile(File file){

//        fieldNameFile.setText(file.getName().split(".java")[0]);
        fieldNameFile.setText(file.getName());

        ArrayList<String> buffer =(ArrayList<String>)PreparingNewAlgos.readSourceFromFile(file);

        for(int i = buffer.size()-1; i>=0; i--)
            textArea.insert(buffer.get(i)+"\n",textArea.getRows());

    }

}
