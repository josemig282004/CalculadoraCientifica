
package calculadoracientifica;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.script.*;

public class CalculadoraCientifica extends JFrame implements ActionListener {
    private JTextField pantalla;
    private String entrada = "";
    
    public CalculadoraCientifica() {
        setTitle("Calculadora Científica");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Pantalla para la entrada y resultados
        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.BOLD, 24));
        add(pantalla, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 4, 5, 5)); // 6 filas x 4 columnas con espaciado

        String[] botones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "sin", "cos", "tan", "log",
            "sqrt", "^", "(", ")",
            "C", "pi", "e", "exp"
        };

        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.PLAIN, 10));
            
            // Asignar colores según la función del botón
            if (texto.matches("[0-9]")) {
                boton.setBackground(new Color(220, 220, 220)); // Color gris claro para los números
                boton.setForeground(Color.BLACK);
            } else if (texto.matches("[/\\*\\-\\+=^]")) {
                boton.setBackground(new Color(255, 165, 0)); // Color naranja para operaciones
                boton.setForeground(Color.BLUE );
            } else if (texto.equals("C")) {
                boton.setBackground(new Color(255, 69, 0)); // Color rojo para el botón de limpiar
                boton.setForeground(Color.RED);
            } else if (texto.matches("sin|cos|tan|log|sqrt|exp|pi|e")) {
                boton.setBackground(new Color(70, 130, 180)); // Color azul para funciones científicas
                boton.setForeground(Color.WHITE);
            } else if (texto.equals("=")) {
                boton.setBackground(new Color(34, 139, 34)); // Color verde para el botón de resultado
                boton.setForeground(Color.PINK);
            }

            boton.addActionListener(this);
            panelBotones.add(boton);
        }

        // Ajustar los márgenes del panel para mejor apariencia
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelBotones, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "C":
                entrada = "";
                pantalla.setText("");
                break;
            case "=":
                try {
                    double resultado = evaluarExpresion(entrada);
                    pantalla.setText(String.valueOf(resultado));
                } catch (Exception ex) {
                    pantalla.setText("Error");
                }
                entrada = "";
                break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "sqrt":
            case "exp":
                entrada += comando + "(";
                pantalla.setText(entrada);
                break;
            case "pi":
                entrada += String.valueOf(Math.PI);
                pantalla.setText(entrada);
                break;
            case "e":
                entrada += String.valueOf(Math.E);
                pantalla.setText(entrada);
                break;
            default:
                entrada += comando;
                pantalla.setText(entrada);
                break;
        }
    }

    // Método para evaluar la expresión
    private double evaluarExpresion(String expresion) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        expresion = expresion.replace("sin", "Math.sin")
                             .replace("cos", "Math.cos")
                             .replace("tan", "Math.tan")
                             .replace("log", "Math.log")
                             .replace("sqrt", "Math.sqrt")
                             .replace("exp", "Math.exp");

        Object resultado = engine.eval(expresion);
        return Double.parseDouble(resultado.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraCientifica calculadora = new CalculadoraCientifica();
            calculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            calculadora.setVisible(true);
        });
    }
}

