package com.list.dropper.editor.syntax;

import android.graphics.Color;

import java.util.ArrayList;

public class HighlightRegistry {

    private final ArrayList<SyntaxHighlight> syntaxHighlights;

    public HighlightRegistry(){
       this.syntaxHighlights = new ArrayList<>(10);
       this.syntaxHighlights.add(new SyntaxHighlight(Color.BLACK));
       this.syntaxHighlights.add(new SyntaxHighlight(Color.GRAY, 1));
       this.syntaxHighlights.add(new SyntaxHighlight(Color.RED));
       SyntaxHighlight syntaxHighlight = new SyntaxHighlight(Color.GRAY, 1, -1, 1);
       syntaxHighlight.setAffectRange((byte)2);
       this.syntaxHighlights.add(syntaxHighlight);
    }

    public SyntaxHighlight getHighlight(int index){return this.syntaxHighlights.get(index);}

}
