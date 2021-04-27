package com.list.dropper.editor.syntax;

import android.text.Editable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.ArrayList;

public class HighlightUtils {

    public static ArrayList<Integer> getLinesIndices(String text){
        ArrayList<Integer> indices = new ArrayList<>(15);
        int currentIndex = text.indexOf("\n");
        while(currentIndex > -1){
            indices.add(currentIndex);
            currentIndex = text.indexOf("\n", currentIndex+1);
        }
        indices.add(text.length()-1);
        return indices;
    }

    public static void clearSpans(Editable editable){
        Object[] spans = editable.getSpans(0, editable.toString().length()-1, Object.class);

        for(Object span : spans){
            if(span instanceof ForegroundColorSpan || span instanceof StyleSpan){
                editable.removeSpan(span);
            }
        }

    }

}
