package com.list.dropper.settings;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import cz.vutbr.web.css.StyleSheet;

public class CssUtils {

    public static void updateColours(Context context, String uiElement, String newColour) {
        try {
            String baseDir = context.getExternalFilesDir(null).getAbsolutePath();

            InputStream inputStream = new FileInputStream(new File(baseDir + "/", "styles.css"));
            String fileText = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            inputStream.close();

            StyleSheet styleSheet = CSSFactory.parseString(fileText, null);
            String oldRule = "";

            if (uiElement.equals("PROJECT_TITLE")) {
                oldRule = styleSheet.get(2).get(0).toString();
                UpdateStyles(baseDir, fileText, oldRule, "color: " + newColour);
            }
            if(uiElement.equals("BACKGROUND")){
                oldRule = styleSheet.get(1).get(0).toString();
                UpdateStyles(baseDir, fileText, oldRule, "background-color: " + newColour);
            }
            if(uiElement.equals("TASK")){
                oldRule = styleSheet.get(3).get(0).toString();
                UpdateStyles(baseDir, fileText, oldRule, "color: " + newColour);
            }

        } catch (IOException | CSSException e) {
            e.printStackTrace();
        }
    }

        public static void UpdateStyles(String baseDir, String fileText, String oldRule, String newRule){
        try {
            fileText = setCssValue(fileText, oldRule, newRule+";\n");
            OutputStream outputStream = new FileOutputStream(new File(baseDir+"/", "styles.css"));
            outputStream.write(fileText.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCssValueFromString(String cssRule){
        String[] ruleValue = cssRule.split(":");
        String value = ruleValue[1].trim();
        return value.substring(0, value.length()-1);
    }

    private static String setCssValue(String fileText, String oldRule, String newRule){
        System.out.println(oldRule + ">>>>>>>>>>>>>>>>" + newRule);
        return fileText.replace(oldRule, newRule);
    }

}
