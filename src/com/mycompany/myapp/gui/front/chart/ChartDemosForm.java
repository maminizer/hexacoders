/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.front.chart;

import com.codename1.io.Log;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.hexacoders.tunipharma.HomeAdmin;

/**
 *
 * @author yossr
 */
public class ChartDemosForm extends Form {

    Form current;

    List formMenu;

    class ListOption {

        Class<AbstractDemoChart> chartClass;
        String name;

        ListOption(Class cls, String name) {
            this.chartClass = cls;
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    ListOption[] options = new ListOption[]{
        new ListOption(BudgetPieChart.class, "Stock Pie Chart"),};

    public ChartDemosForm() {
        super("A Chart Engine Demo");
        current = this;
        formMenu = new List();
        for (int i = 0; i < options.length; i++) {
            formMenu.addItem(options[i]);
            getToolbar().addMaterialCommandToLeftBar("back",
                    FontImage.MATERIAL_BACKUP, ev -> {
                        new HomeAdmin(current).show();
                    });

        }

        formMenu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                int sel = formMenu.getCurrentSelected();
                ListOption opt = options[sel];
                Class cls = opt.chartClass;

                try {
                    AbstractDemoChart demo = (AbstractDemoChart) cls.newInstance();

                    Form intent = demo.execute();
                    if ("".equals(intent.getTitle())) {
                        intent.setTitle(demo.getName());
                    }
                    Command cmd = new Command("Menu") {

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ChartDemosForm.this.showBack();
                        }

                    };
                    intent.setBackCommand(cmd);
                    intent.getStyle().setBgColor(0x0);
                    intent.getStyle().setBgTransparency(0xff);
                    int numComponents = intent.getComponentCount();
                    for (int i = 0; i < numComponents; i++) {
                        intent.getComponentAt(i).getStyle().setBgColor(0x0);
                        intent.getComponentAt(i).getStyle().setBgTransparency(0xff);
                    }
                    intent.show();
                } catch (InstantiationException ex) {
                    Log.e(ex);
                } catch (IllegalAccessException ex) {
                    Log.e(ex);
                }
            }

        });

        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, formMenu);

    }

}
