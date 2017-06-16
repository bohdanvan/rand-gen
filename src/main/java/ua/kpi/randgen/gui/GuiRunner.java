package ua.kpi.randgen.gui;

import javax.swing.*;

/**
 * Клас для запуску додатку с графічним інтерфейсом.
 *
 * @author bvanchuhov
 */
public class GuiRunner {

    public static void main(String[] args) {
        JFrame mainForm = new NFSRMainForm();
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainForm.setVisible(true);
    }
}
