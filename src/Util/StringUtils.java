/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author jonh_
 */
public class StringUtils {
        
    // Verifica se a String é null ou vazia ou só tem espaços em branco
    public static boolean isNullOrBlank(int s) {
        String aux = Integer.toString(s);
        return (aux == null || aux.trim().equals(""));
    }
    public static boolean isNullOrEmpty(int s) {
        String aux = Integer.toString(s);
        return (aux == null || aux.equals(""));
    }
    
    public static boolean isNullOrBlank(String s) {
        return (s == null || s.trim().equals(""));
    }
    // Verifica se a String é null ou vazia
    // Pode ser utilizado como suporte em APIs menores que 9 do android onde não está disponivel o metódo de String isEmpty()
    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.equals(""));
    }
}
