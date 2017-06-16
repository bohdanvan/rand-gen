/*
 * Created by JFormDesigner on Tue Jun 16 07:12:27 AST 2015
 */

package ua.kpi.randgen.gui;

import ua.kpi.randgen.BitwiseNumber;
import ua.kpi.randgen.NFSRFunctionGenerator;
import ua.kpi.randgen.NFSRGenerator;
import ua.kpi.randgen.utils.NFSRUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Форма для роботи з NFSR.
 *
 * @author bvanchuhov
 */
public class NFSRMainForm extends JFrame {
    private int registerLength;
    private BitwiseNumber function;
    private NFSRGenerator generator;

    public NFSRMainForm() {
        initComponents();
    }

    private void generateFunctionActionPerformed(ActionEvent e) {
        new Thread(() -> {
            registerLength = readRegisterLength();

            NFSRFunctionGenerator functionGenerator = new NFSRFunctionGenerator(registerLength);
            function = functionGenerator.generate();

            showGeneratedFunction(function);

            setButtonsEnabled();
        }).start();
    }

    private int readRegisterLength() {
        return Integer.parseInt(registerLengthTextEdit.getText());
    }

    private void setButtonsEnabled() {
        nonlinearityButton.setEnabled(true);
        generateNextNumberButton.setEnabled(true);
        generatorResetButton.setEnabled(true);
        generateAllBitsButton.setEnabled(true);
    }

    private void showGeneratedFunction(BitwiseNumber function) {
        functionTextArea.setText(function.toString());
    }

    private void showFunctionLength(int functionLength) {
        functionLengthTextEdit.setText(Integer.toString(functionLength));
    }

    private void nonlinearityButtonActionPerformed(ActionEvent e) {
        new Thread(() -> {
            int nonlinearity = NFSRUtils.nonlinearity(registerLength, function);
            showNonlinearity(nonlinearity);
        }).start();
    }

    private void showNonlinearity(int nonlinearity) {
        nonlinearityTextEdit.setText(Integer.toString(nonlinearity));
    }

    private void registerLengthTextEditFocusLost(FocusEvent e) {
        registerLength = readRegisterLength();
        int functionLength = NFSRUtils.calcFunctionLength(registerLength);
        showFunctionLength(functionLength);
    }

    private void generateNextBitActionPerformed(ActionEvent e) {
        if (generator == null) {
            clearGenerator();

            BitwiseNumber initVector = readInitVector();
            generator = new NFSRGenerator(registerLength, function);
            generator.setSeed(initVector);
        }

        generateNextBit();
    }

    private void generateNextBit() {
        int bit = generator.generateBit();
        BitwiseNumber register = generator.getRegister();

        generatedBitTextArea.append(Integer.toString(bit));
        registerTextArea.append(register.toString() + "\n");
    }

    private void clearGenerator() {
        generator = null;
        registerTextArea.setText("");
        generatedBitTextArea.setText("");
    }

    private BitwiseNumber readInitVector() {
        return BitwiseNumber.parse(initVectorTextField.getText());
    }

    private void generateAllBitsActionPerformed(ActionEvent e) {
        clearGenerator();

        NFSRGenerator generator = new NFSRGenerator(registerLength, function);
        generator.setSeed(0);

        int valuesQuantity = generator.getPeriod();
        StringBuilder registerText = new StringBuilder();
        StringBuilder bitsText = new StringBuilder();
        for (int i = 0; i < valuesQuantity; i++) {
            bitsText.append(generator.generateBit());
            registerText.append(generator.getRegister()).append("\n");
        }

        registerTextArea.setText(registerText.toString());
        generatedBitTextArea.setText(bitsText.toString());
    }

    private void tabbedPaneStateChanged(ChangeEvent e) {
        if (tabbedPane.getSelectedIndex() == 1) {
            registerLength = readRegisterLength();

            registerLengthTextEdit2.setText(Integer.toString(registerLength));
            functionLengthTextEdit2.setText(functionLengthTextEdit.getText());

            String functionText = function == null ? "" : function.toString();
            functionTextArea2.setText(functionText);

            BitwiseNumber initVector = BitwiseNumber.of(registerLength);
            initVectorTextField.setText(initVector.toString());
        }
    }

    private void generatorResetActionPerformed(ActionEvent e) {
        clearGenerator();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - John Smith
        tabbedPane = new JTabbedPane();
        functionGeneratorPanel = new JPanel();
        registerLengthLabel = new JLabel();
        registerLengthTextEdit = new JTextField();
        functionLengthLabel = new JLabel();
        functionLengthTextEdit = new JTextField();
        scrollPane1 = new JScrollPane();
        functionTextArea = new JTextArea();
        generateFunctionButton = new JButton();
        nonlinearityLabel = new JLabel();
        nonlinearityTextEdit = new JTextField();
        nonlinearityButton = new JButton();
        numberGeneratorPanel = new JPanel();
        registerLengthLabel2 = new JLabel();
        registerLengthTextEdit2 = new JTextField();
        registerLengthLabel3 = new JLabel();
        scrollPane2 = new JScrollPane();
        registerTextArea = new JTextArea();
        functionLengthLabel2 = new JLabel();
        functionLengthTextEdit2 = new JTextField();
        functionLabel = new JLabel();
        scrollPane3 = new JScrollPane();
        functionTextArea2 = new JTextArea();
        initVectorLabel = new JLabel();
        initVectorTextField = new JTextField();
        registerLengthLabel4 = new JLabel();
        generateNextNumberButton = new JButton();
        generatorResetButton = new JButton();
        scrollPane4 = new JScrollPane();
        generatedBitTextArea = new JTextArea();
        generateAllBitsButton = new JButton();

        //======== this ========
        setTitle("\u0413\u0435\u043d\u0435\u0440\u0430\u0442\u043e\u0440 \u041f\u0412\u0414\u041f \u043d\u0430 \u043e\u0441\u043d\u043e\u0432\u0456  \u0440\u0435\u0433\u0456\u0441\u0442\u0440\u0456\u0432 \u0437\u0441\u0443\u0432\u0443 \u0437 \u043d\u0435\u043b\u0456\u043d\u0456\u0439\u043d\u0438\u043c \u0437\u0432\u043e\u0440\u043e\u0442\u043d\u0438\u043c \u0437\u0432'\u044f\u0437\u043a\u043e\u043c");
        setFont(new Font("Dialog", Font.PLAIN, 16));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane ========
        {
            tabbedPane.setFont(new Font("Dialog", Font.PLAIN, 16));
            tabbedPane.addChangeListener(e -> tabbedPaneStateChanged(e));

            //======== functionGeneratorPanel ========
            {
                functionGeneratorPanel.setFont(new Font("Dialog", Font.PLAIN, 16));

                functionGeneratorPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                    public void propertyChange(java.beans.PropertyChangeEvent e) {
                        if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                    }
                });

                functionGeneratorPanel.setLayout(new GridBagLayout());
                ((GridBagLayout) functionGeneratorPanel.getLayout()).columnWidths = new int[]{20, 391, 210, 163, 10, 0};
                ((GridBagLayout) functionGeneratorPanel.getLayout()).rowHeights = new int[]{20, 30, 30, 16, 192, 0, 17, 30, 11, 0};
                ((GridBagLayout) functionGeneratorPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout) functionGeneratorPanel.getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- registerLengthLabel ----
                registerLengthLabel.setText("\u0414\u043e\u0432\u0436\u0438\u043d\u0430 \u0437\u0441\u0443\u0432\u043d\u043e\u0433\u043e \u0440\u0435\u0433\u0456\u0441\u0442\u0440\u0443:");
                registerLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionGeneratorPanel.add(registerLengthLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- registerLengthTextEdit ----
                registerLengthTextEdit.setFont(new Font("Dialog", Font.PLAIN, 16));
                registerLengthTextEdit.setHorizontalAlignment(SwingConstants.CENTER);
                registerLengthTextEdit.setText("4");
                registerLengthTextEdit.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        registerLengthTextEditFocusLost(e);
                    }
                });
                functionGeneratorPanel.add(registerLengthTextEdit, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- functionLengthLabel ----
                functionLengthLabel.setText("\u0414\u043e\u0432\u0436\u0438\u043d\u0430 \u0444\u0443\u043d\u043a\u0446\u0456\u0457 \u0437\u0432\u043e\u0440\u043e\u0442\u043d\u043e\u0433\u043e \u0437\u0432'\u044f\u0437\u043a\u0443:");
                functionLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionGeneratorPanel.add(functionLengthLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- functionLengthTextEdit ----
                functionLengthTextEdit.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionLengthTextEdit.setHorizontalAlignment(SwingConstants.CENTER);
                functionLengthTextEdit.setEditable(false);
                functionLengthTextEdit.setText("16");
                functionGeneratorPanel.add(functionLengthTextEdit, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setFont(new Font("Dialog", Font.PLAIN, 16));

                    //---- functionTextArea ----
                    functionTextArea.setEditable(false);
                    functionTextArea.setFont(new Font("Dialog", Font.PLAIN, 16));
                    functionTextArea.setLineWrap(true);
                    scrollPane1.setViewportView(functionTextArea);
                }
                functionGeneratorPanel.add(scrollPane1, new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- generateFunctionButton ----
                generateFunctionButton.setText("\u0417\u0433\u0435\u043d\u0435\u0440\u0443\u0432\u0430\u0442\u0438 \u0444\u0443\u043d\u043a\u0446\u0456\u044e");
                generateFunctionButton.setFont(new Font("Dialog", Font.PLAIN, 16));
                generateFunctionButton.addActionListener(e -> generateFunctionActionPerformed(e));
                functionGeneratorPanel.add(generateFunctionButton, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- nonlinearityLabel ----
                nonlinearityLabel.setText("\u041d\u0435\u043b\u0456\u043d\u0456\u0439\u043d\u0456\u0441\u0442\u044c:");
                nonlinearityLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionGeneratorPanel.add(nonlinearityLabel, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- nonlinearityTextEdit ----
                nonlinearityTextEdit.setFont(new Font("Dialog", Font.PLAIN, 16));
                nonlinearityTextEdit.setHorizontalAlignment(SwingConstants.CENTER);
                nonlinearityTextEdit.setEditable(false);
                functionGeneratorPanel.add(nonlinearityTextEdit, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- nonlinearityButton ----
                nonlinearityButton.setText("\u041e\u0431\u0447\u0438\u0441\u043b\u0438\u0442\u0438");
                nonlinearityButton.setFont(new Font("Dialog", Font.PLAIN, 16));
                nonlinearityButton.setEnabled(false);
                nonlinearityButton.addActionListener(e -> nonlinearityButtonActionPerformed(e));
                functionGeneratorPanel.add(nonlinearityButton, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));
            }
            tabbedPane.addTab("\u0413\u0435\u043d\u0435\u0440\u0430\u0446\u0456\u044f \u043d\u0435\u043b\u0456\u043d\u0456\u0439\u043d\u043e\u0457 \u0444\u0443\u043d\u043a\u0446\u0456\u0457 \u0437\u0432\u043e\u0440\u043e\u0442\u043d\u043e\u0433\u043e \u0437\u0432'\u044f\u0437\u043a\u0443", functionGeneratorPanel);

            //======== numberGeneratorPanel ========
            {
                numberGeneratorPanel.setFont(new Font("Dialog", Font.PLAIN, 16));
                numberGeneratorPanel.setLayout(new GridBagLayout());
                ((GridBagLayout) numberGeneratorPanel.getLayout()).columnWidths = new int[]{20, 270, 25, 240, 230, 10, 0};
                ((GridBagLayout) numberGeneratorPanel.getLayout()).rowHeights = new int[]{15, 30, 30, 16, 121, 0, 34, 25, 0, 0, 15, 8, 0};
                ((GridBagLayout) numberGeneratorPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout) numberGeneratorPanel.getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- registerLengthLabel2 ----
                registerLengthLabel2.setText("\u0414\u043e\u0432\u0436\u0438\u043d\u0430 \u0437\u0441\u0443\u0432\u043d\u043e\u0433\u043e \u0440\u0435\u0433\u0456\u0441\u0442\u0440\u0443:");
                registerLengthLabel2.setFont(new Font("Dialog", Font.PLAIN, 16));
                numberGeneratorPanel.add(registerLengthLabel2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- registerLengthTextEdit2 ----
                registerLengthTextEdit2.setFont(new Font("Dialog", Font.PLAIN, 16));
                registerLengthTextEdit2.setHorizontalAlignment(SwingConstants.CENTER);
                registerLengthTextEdit2.setText("4");
                registerLengthTextEdit2.setEditable(false);
                numberGeneratorPanel.add(registerLengthTextEdit2, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- registerLengthLabel3 ----
                registerLengthLabel3.setText("\u0417\u0441\u0443\u0432\u043d\u0438\u0439 \u0440\u0435\u0433\u0456\u0441\u0442\u0440");
                registerLengthLabel3.setFont(new Font("Dialog", Font.BOLD, 16));
                registerLengthLabel3.setHorizontalAlignment(SwingConstants.CENTER);
                numberGeneratorPanel.add(registerLengthLabel3, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //======== scrollPane2 ========
                {
                    scrollPane2.setFont(new Font("Dialog", Font.PLAIN, 16));

                    //---- registerTextArea ----
                    registerTextArea.setLineWrap(true);
                    registerTextArea.setFont(new Font("Dialog", Font.PLAIN, 16));
                    registerTextArea.setEditable(false);
                    scrollPane2.setViewportView(registerTextArea);
                }
                numberGeneratorPanel.add(scrollPane2, new GridBagConstraints(4, 2, 1, 4, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- functionLengthLabel2 ----
                functionLengthLabel2.setText("\u0414\u043e\u0432\u0436\u0438\u043d\u0430 \u0444\u0443\u043d\u043a\u0446\u0456\u0457 \u0437\u0432\u043e\u0440\u043e\u0442\u043d\u043e\u0433\u043e \u0437\u0432'\u044f\u0437\u043a\u0443:");
                functionLengthLabel2.setFont(new Font("Dialog", Font.PLAIN, 16));
                numberGeneratorPanel.add(functionLengthLabel2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- functionLengthTextEdit2 ----
                functionLengthTextEdit2.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionLengthTextEdit2.setHorizontalAlignment(SwingConstants.CENTER);
                functionLengthTextEdit2.setEditable(false);
                functionLengthTextEdit2.setText("16");
                numberGeneratorPanel.add(functionLengthTextEdit2, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- functionLabel ----
                functionLabel.setText("\u0424\u0443\u043d\u043a\u0446\u0456\u044f \u0437\u0432\u043e\u0440\u043e\u0442\u043d\u043e\u0433\u043e \u0437\u0432'\u044f\u0437\u043a\u0443:");
                functionLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
                functionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                functionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                numberGeneratorPanel.add(functionLabel, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //======== scrollPane3 ========
                {
                    scrollPane3.setFont(new Font("Dialog", Font.PLAIN, 16));

                    //---- functionTextArea2 ----
                    functionTextArea2.setFont(new Font("Dialog", Font.PLAIN, 16));
                    functionTextArea2.setEditable(false);
                    functionTextArea2.setLineWrap(true);
                    scrollPane3.setViewportView(functionTextArea2);
                }
                numberGeneratorPanel.add(scrollPane3, new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- initVectorLabel ----
                initVectorLabel.setText("\u0412\u0435\u043a\u0442\u043e\u0440 \u0456\u043d\u0456\u0446\u0456\u0430\u043b\u0456\u0437\u0430\u0446\u0456\u0457:");
                initVectorLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
                initVectorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                numberGeneratorPanel.add(initVectorLabel, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- initVectorTextField ----
                initVectorTextField.setFont(new Font("Dialog", Font.PLAIN, 16));
                initVectorTextField.setHorizontalAlignment(SwingConstants.CENTER);
                initVectorTextField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        registerLengthTextEditFocusLost(e);
                    }
                });
                numberGeneratorPanel.add(initVectorTextField, new GridBagConstraints(1, 6, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- registerLengthLabel4 ----
                registerLengthLabel4.setText("\u0417\u0433\u0435\u043d\u0435\u0440\u043e\u0432\u0430\u043d\u0430 \u043f\u043e\u0441\u043b\u0456\u0434\u043e\u0432\u043d\u0456\u0441\u0442\u044c");
                registerLengthLabel4.setFont(new Font("Dialog", Font.BOLD, 16));
                registerLengthLabel4.setHorizontalAlignment(SwingConstants.CENTER);
                numberGeneratorPanel.add(registerLengthLabel4, new GridBagConstraints(4, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- generateNextNumberButton ----
                generateNextNumberButton.setText("\u0417\u0433\u0435\u043d\u0435\u0440\u0443\u0432\u0430\u0442\u0438 \u043d\u0430\u0441\u0442\u0443\u043f\u043d\u0438\u0439 \u0431\u0456\u0442");
                generateNextNumberButton.setFont(new Font("Dialog", Font.PLAIN, 16));
                generateNextNumberButton.setEnabled(false);
                generateNextNumberButton.addActionListener(e -> generateNextBitActionPerformed(e));
                numberGeneratorPanel.add(generateNextNumberButton, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- generatorResetButton ----
                generatorResetButton.setText("Reset");
                generatorResetButton.setFont(new Font("Dialog", Font.PLAIN, 16));
                generatorResetButton.setEnabled(false);
                generatorResetButton.addActionListener(e -> generatorResetActionPerformed(e));
                numberGeneratorPanel.add(generatorResetButton, new GridBagConstraints(2, 8, 2, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //======== scrollPane4 ========
                {
                    scrollPane4.setFont(new Font("Dialog", Font.PLAIN, 16));

                    //---- generatedBitTextArea ----
                    generatedBitTextArea.setFont(new Font("Dialog", Font.PLAIN, 16));
                    generatedBitTextArea.setEditable(false);
                    generatedBitTextArea.setLineWrap(true);
                    scrollPane4.setViewportView(generatedBitTextArea);
                }
                numberGeneratorPanel.add(scrollPane4, new GridBagConstraints(4, 8, 1, 2, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));

                //---- generateAllBitsButton ----
                generateAllBitsButton.setText("\u0417\u0433\u0435\u043d\u0435\u0440\u0443\u0432\u0430\u0442\u0438 \u0432\u0441\u0456 \u0431\u0456\u0442\u0438 \u043f\u0435\u0440\u0456\u043e\u0434\u0443");
                generateAllBitsButton.setFont(new Font("Dialog", Font.PLAIN, 16));
                generateAllBitsButton.setEnabled(false);
                generateAllBitsButton.addActionListener(e -> generateAllBitsActionPerformed(e));
                numberGeneratorPanel.add(generateAllBitsButton, new GridBagConstraints(1, 9, 3, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 10), 0, 0));
            }
            tabbedPane.addTab("\u0413\u0435\u043d\u0435\u0440\u0430\u0442\u043e\u0440 \u041f\u0412\u0414\u041f", numberGeneratorPanel);

            tabbedPane.setSelectedIndex(0);
        }
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        setSize(815, 465);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - John Smith
    private JTabbedPane tabbedPane;
    private JPanel functionGeneratorPanel;
    private JLabel registerLengthLabel;
    private JTextField registerLengthTextEdit;
    private JLabel functionLengthLabel;
    private JTextField functionLengthTextEdit;
    private JScrollPane scrollPane1;
    private JTextArea functionTextArea;
    private JButton generateFunctionButton;
    private JLabel nonlinearityLabel;
    private JTextField nonlinearityTextEdit;
    private JButton nonlinearityButton;
    private JPanel numberGeneratorPanel;
    private JLabel registerLengthLabel2;
    private JTextField registerLengthTextEdit2;
    private JLabel registerLengthLabel3;
    private JScrollPane scrollPane2;
    private JTextArea registerTextArea;
    private JLabel functionLengthLabel2;
    private JTextField functionLengthTextEdit2;
    private JLabel functionLabel;
    private JScrollPane scrollPane3;
    private JTextArea functionTextArea2;
    private JLabel initVectorLabel;
    private JTextField initVectorTextField;
    private JLabel registerLengthLabel4;
    private JButton generateNextNumberButton;
    private JButton generatorResetButton;
    private JScrollPane scrollPane4;
    private JTextArea generatedBitTextArea;
    private JButton generateAllBitsButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
