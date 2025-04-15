package org.sudoku.ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishedGameButton extends JButton {

    public FinishedGameButton(final ActionListener actionListener){
        this.setText("Concluir");
        this.addActionListener(actionListener);
    }

    //12:06
}
