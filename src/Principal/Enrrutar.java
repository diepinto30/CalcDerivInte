
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Derivadas.Exponencial;
import Derivadas.derivadaPotencia;
import Derivadas.derivadaProducto;
import Derivadas.derivadaValorAbsoluto;
import Integrales.integralCosiente;
import Integrales.integralPotencia;
import Integrales.integralProducto;
import Procesos.procesoDerivadaCociente;
import Procesos.procesoDerivadaLogaritmo;
import java.util.ArrayList;

/**
 *
 * @author Alexander Batista
 */
public class Enrrutar extends CDI {

    static procesoDerivadaCociente PDC = new procesoDerivadaCociente();
    static procesoDerivadaLogaritmo PDL = new procesoDerivadaLogaritmo();
    private static boolean letra = false;

    public static ExpresionAlgebraica[] Enrrutador(ArrayList partes, ArrayList delimitador, String op, String diferencial) {
        ArrayList Segmentos;
        ArrayList Signos;
        boolean parentesis = false;
        Segmentos = partes;
        Signos = delimitador;
         String cad = "";
        if (!op.toUpperCase().equals("D")) {
            for (int i = 0; i < Segmentos.size(); i++) {
                for (int j = 0; j < Segmentos.get(i).toString().length(); j++) {
                    if (Segmentos.get(i).toString().charAt(j) == '(') {
                        ExpresionAlgebraica[] ccc = SintaxisExpresiones.Sintaxis(Segmentos.get(i).toString().substring(Segmentos.get(i).toString().indexOf("(") + 1, Segmentos.get(i).toString().lastIndexOf(")")), op, false, diferencial);
                        for (ExpresionAlgebraica ccc1 : ccc) {
                            if (ccc1.getVariable().isEmpty()) {
                                ccc1.setVariable(diferencial.substring(0));
                            }
                        }
                        String Cadena = "";
                        for (ExpresionAlgebraica ccc1 : ccc) {
                            Cadena = Cadena + ccc1.getSimbolo() + ccc1.getCoeficiente() + ccc1.getVariable() + "^" + ccc1.getExponente();
                        }
                        if (Cadena.charAt(0) == '+') {
                            Cadena = Cadena.substring(1, Cadena.length());
                        }
                        Segmentos.set(i, "(" + Cadena + ")");
                        letra = true;
                        break;
                    } else if (String.valueOf(Segmentos.get(i).toString().charAt(j)).toUpperCase().hashCode() >= 65
                            && String.valueOf(Segmentos.get(i).toString().charAt(j)).toUpperCase().hashCode() <= 90) {
                        letra = true;
                        break;
                    }
                }
                if (!letra) {
                    Segmentos.set(i, Segmentos.get(i).toString().concat(diferencial.substring(0) + "^0"));
                }
            }
            

            for (int i = 0; i < Segmentos.size(); i++) {
                for (int l = 2; l < Segmentos.get(i).toString().length(); l++) {
                    if (Segmentos.get(i).toString().charAt(l) == '(') {
                        parentesis = true;
                        break;
                    }
                }
                if (Signos.isEmpty()) {
                    if (Segmentos.get(0).toString().length() > 3 && parentesis) {
                        switch (Segmentos.get(0).toString().toLowerCase().substring(0, Segmentos.get(0).toString().indexOf("("))) {
                            case "e^":
                                //resultado = Exponencial.exponencial(Segmentos.get(0).toString());
                                //expon = true;
                                break;
                            case "ln":
                                //resultado = PDL.proceso(Segmentos, Signos, op);
                                break;
                            case "sen":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                            case "cos":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                            case "tan":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                            case "cot":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                            case "sec":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                            case "csc":
                                expz = Integrales.Trigonometricas.correr(Segmentos);
                                break;
                        }
                        break;

                    } else if ("|".equals(Segmentos.get(i).toString().substring(0, 1))) {
                        if (Segmentos.size() == 1) {
                            //derivadaValorAbsoluto.ValorAbsoluto(Segmentos.get(i).toString().substring(1, Segmentos.get(i).toString().length() - 1), op);
                        } else {
                            cad = "";
                            for (Object Segmento : Segmentos) {
                                cad = cad + (Segmento.toString());
                            }
                            //derivadaValorAbsoluto.ValorAbsoluto(cad.substring(1, cad.length() - 1), op);
                        }
                        break;
                    } else if (")".equals(Segmentos.get(0).toString().substring(Segmentos.get(0).toString().length() - 1))
                            && "(".equals(Segmentos.get(1).toString().substring(0, 1)) && Signos.isEmpty()) {
                        
                        resultado = integralProducto.integral_producto(Segmentos, diferencial);
                        break;
                    } else {
                        resultado = integralPotencia.integral_Potencia(ProcesarFunciones.jeraquia(Segmentos, Signos), diferencial);

                        break;
                    }
                } else if (!Signos.isEmpty()) {
                    if (Signos.get(0).toString().equals("/")) {
                        if (Segmentos.get(0).toString().equals("(1.0x^0)")&&Segmentos.get(1).toString().substring(Segmentos.get(1).toString().indexOf("^")+1,Segmentos.get(i).toString().length()-1).equals("1")) {
                            resultado = integralCosiente.cosiente(Segmentos, true, diferencial);
                            break;
                        } else {
                            resultado = integralCosiente.cosiente(Segmentos, false, diferencial);
                            break;
                        }
                    } else if ("|".equals(Segmentos.get(i).toString().substring(0, 1))) {

                        if (Segmentos.size() == Signos.size()) {
                            cad = cad + (Signos.get(0).toString());
                        } else {
                            for (int j = 0; j < Segmentos.size(); j++) {
                                if (j == 0) {
                                    cad = cad + Segmentos.get(j).toString().substring(1, Segmentos.get(j).toString().length());
                                } else if (j == Segmentos.size() - 1) {
                                    cad = cad + Segmentos.get(j).toString().substring(0, Segmentos.get(j).toString().length() - 1);
                                } else {
                                    cad = cad + Segmentos.get(j);
                                }
                                if (j < Signos.size()) {
                                    cad = cad + Signos.get(i);
                                }
                            }
                        }
                        resultado = SintaxisExpresiones.Sintaxis(cad, op, false, diferencial);
                        break;
                    } else {

                        resultado = integralPotencia.integral_Potencia(ProcesarFunciones.jeraquia(Segmentos, Signos),diferencial);
                        break;
                    }
                }//(10x^4+20x^2)/(5x+2)
            }
        } else {

            for (int i = 0; i < Segmentos.size(); i++) {
                for (int l = 2; l < Segmentos.get(i).toString().length(); l++) {
                    if (Segmentos.get(i).toString().charAt(l) == '(') {
                        parentesis = true;
                        break;
                    }
                }
                if (Signos.isEmpty()) {
                    if (Segmentos.get(0).toString().length() > 3 && parentesis) {
                        switch (Segmentos.get(0).toString().toLowerCase().substring(0, Segmentos.get(0).toString().indexOf("("))) {
                            case "e^":
                                resultado = Exponencial.exponencial(Segmentos.get(0).toString(), diferencial);
                                expon = true;
                                break;
                            case "ln":
                                resultado = PDL.proceso(Segmentos, Signos, op, diferencial);
                                break;
                            case "sen":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                            case "cos":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                            case "tan":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                            case "cot":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                            case "sec":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                            case "csc":
                                expz = Derivadas.Trigonometricas.correr(Segmentos, diferencial);
                                break;
                        }
                        break;
                    } else if ("|".equals(Segmentos.get(i).toString().substring(0, 1))) {
                        if (Segmentos.size() == 1) {
                            derivadaValorAbsoluto.ValorAbsoluto(Segmentos.get(i).toString().substring(1, Segmentos.get(i).toString().length() - 1), op, diferencial);
                        } else {
                            cad = "";
                            for (Object Segmento : Segmentos) {
                                cad = cad + (Segmento.toString());
                            }
                            derivadaValorAbsoluto.ValorAbsoluto(cad.substring(1, cad.length() - 1), op, diferencial);
                        }
                        break;
                    } else if (")".equals(Segmentos.get(0).toString().substring(Segmentos.get(0).toString().length() - 1))
                            && "(".equals(Segmentos.get(1).toString().substring(0, 1)) && Signos.isEmpty()) {
                        resultado = derivadaProducto.Derivada_Producto(Segmentos, diferencial);
                        break;
                    } else {
                        resultado = derivadaPotencia.derivada_Potencia(ProcesarFunciones.jeraquia(Segmentos, Signos), diferencial);

                        break;
                    }
                } else if (!Signos.isEmpty()) {
                    if (Signos.get(0).toString().equals("/")) {
                        resultado = PDC.proceso(Segmentos, Signos, op, diferencial);
                        break;
                    } else if ("|".equals(Segmentos.get(i).toString().substring(0, 1))) {

                        if (Segmentos.size() == Signos.size()) {
                            cad = cad + (Signos.get(0).toString());
                        } else {
                            for (int j = 0; j < Segmentos.size(); j++) {
                                if (j == 0) {
                                    cad = cad + Segmentos.get(j).toString().substring(1, Segmentos.get(j).toString().length());
                                } else if (j == Segmentos.size() - 1) {
                                    cad = cad + Segmentos.get(j).toString().substring(0, Segmentos.get(j).toString().length() - 1);
                                } else {
                                    cad = cad + Segmentos.get(j);
                                }
                                if (j < Signos.size()) {
                                    cad = cad + Signos.get(i);
                                }
                            }
                        }
                        derivadaValorAbsoluto.ValorAbsoluto(cad, op, diferencial);
                        break;
                    } else {
                        resultado = derivadaPotencia.derivada_Potencia(ProcesarFunciones.jeraquia(Segmentos, Signos), diferencial);
                        break;
                    }
                }
            }
        }
        return resultado;
    }
}
