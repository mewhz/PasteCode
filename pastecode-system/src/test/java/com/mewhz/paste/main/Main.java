package com.mewhz.paste.main;

import com.mewhz.paste.utils.UserUtils;
import net.datafaker.Faker;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 2000; i++) {
            System.out.println("UserUtils.userAccountGenerate() = " + UserUtils.userAccountGenerate());
        }
    }
}
