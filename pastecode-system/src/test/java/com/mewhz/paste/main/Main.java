package com.mewhz.paste.main;

import java.util.Scanner;

public class Main{
    static int n, m;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        m = input.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }
        int[] result = new int[n];
        int[] sub = new int[4];
        for (int i = 0; i < 3; i++) {
            sub[i] = input.nextInt();
            sub[3] += sub[i];
        }
        sub[3] = 100 - sub[3];
        int[] subs = new int[4];
        for (int i = 0; i < 4; i++) {
            subs[i] = input.nextInt();
            if (i != 0) {
                subs[i] += subs[i - 1];
            }
        }
        int count = 1, s;
        for (int i = 0; i < 4; i ++) {
            s = (int) (sub[i] * 0.01 * n) + count + ((int)(sub[i] * 0.01 * n) - (sub[i] * 0.01 * n) == 0 ? 0 : 1);
            fun(count, s, result, arr , subs[i]);
            count = s;
        }

        for (int i = 0; i < n; i ++) {
            System.out.print(result[i] +"  ");
        }
    }

    private static void fun(int count, double v) {
    }

    public static void fun(int count, int nn, int[] result, int[] arr, int subs) {
        for (;count < nn; count++) {
            if (count > n) return;
            result[count- 1] = (int) ((arr[count - 1] * 1.0/ m) * 100 - subs);

        }
    }
}