package com.list.dropper.editor.syntax;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Pair;

import java.util.ArrayList;

public class Highlighter {

    private HighlightRegistry highlightRegistry;
    private SyntaxRegistry syntaxRegistry;

    public Highlighter(){
        this.highlightRegistry = new HighlightRegistry();
        this.syntaxRegistry = new SyntaxRegistry();
    }

    public void highlight(Editable editable) {
        HighlightUtils.clearSpans(editable);

        String fullText = editable.toString();
        int syntaxIndex, syntaxLen = 0;
        ArrayList<Integer> lines = HighlightUtils.getLinesIndices(fullText);
        int lineStart = 0;

        int start, end;
        boolean repeat = false;
        boolean runStrike = false;

        for(int lineEnd : lines){
            for(Pair<String, Integer> syntax : this.syntaxRegistry.getSyntaxRegistry()){
                SyntaxHighlight syntaxHighlight = this.highlightRegistry.getHighlight(syntax.second);
                syntaxIndex = fullText.indexOf(syntax.first, lineStart);
                syntaxLen = (syntax.first).length();

                while((syntaxIndex >= lineStart && syntaxIndex <= lineEnd)){
                    start = syntaxIndex;
                    end = syntaxIndex + syntaxLen;

                    if(syntaxHighlight.getAffectRange() == (byte)2){
                        if(syntaxHighlight.getStrike() == 1) editable.setSpan(new StrikethroughSpan(), lineStart, lineEnd-syntaxLen-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        editable.setSpan(new ForegroundColorSpan(syntaxHighlight.getColour()), lineStart, lineEnd-syntaxLen-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    editable.setSpan(new ForegroundColorSpan(syntaxHighlight.getColour()), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(syntaxHighlight.getBold() == 1) editable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if(syntaxHighlight.getItalic() == 1) editable.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    syntaxIndex = fullText.indexOf(syntax.first, syntaxIndex+syntaxLen);
                }
            }
            lineStart = lineEnd+1;
        }
    }

}
