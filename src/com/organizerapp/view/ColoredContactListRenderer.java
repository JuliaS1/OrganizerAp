package com.organizerapp.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ColoredContactListRenderer extends DefaultListCellRenderer {
    private Color color;

    public ColoredContactListRenderer(Color color) {
        this.color = color;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component rendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        rendererComponent.setForeground(color);
        return rendererComponent;
    }
}
