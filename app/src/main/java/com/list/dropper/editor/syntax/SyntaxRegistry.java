package com.list.dropper.editor.syntax;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class SyntaxRegistry {

    private ArrayList<Pair<String, Integer>> syntaxRegistry;

    public SyntaxRegistry(){
        this.syntaxRegistry = new ArrayList<Pair<String, Integer>>(10);
        this.registerSyntax("@done", 3);
        this.registerSyntax("Hello", 2);
    }

    public void registerSyntax(String word, int highlightId){
        this.syntaxRegistry.add(new Pair<String, Integer>(word, highlightId));
    }

    public ArrayList<Pair<String, Integer>>  getSyntaxRegistry(){return this.syntaxRegistry;}
}
