package algo_general;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 10.07.13
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class PopupMenu extends MouseAdapter {

    private JPopupMenu menu;

    public PopupMenu(JPopupMenu menu){
        this.menu = menu;
    }
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}
