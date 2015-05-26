/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Ui;

import java.util.Scanner;

/**
 *
 * @author devaucod
 */
public class TexteUI {
    public static void message(String message){
        System.out.println(message);
    }
    
    public static String question(String question){
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
