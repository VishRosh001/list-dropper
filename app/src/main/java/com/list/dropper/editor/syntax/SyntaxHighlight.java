package com.list.dropper.editor.syntax;

public class SyntaxHighlight {

    private int[] styles;
    private byte affectRange;

    public SyntaxHighlight(){
        this(-1, -1, -1, -1);
    }

    public SyntaxHighlight(int colour){
        this(colour, -1, -1, -1);
    }

    public SyntaxHighlight(int colour, int bold){
        this(colour, bold, -1, -1);
    }

    public SyntaxHighlight(int colour, int bold, int italic, int strike){
        this.styles = new int[]{colour, bold, italic, strike};
    }

    public void setForeColour(int colour){
       this.styles[0] = colour;
    }
    public void setBold(int bold){
        this.styles[1] = bold;
    }
    public void setItalic(int italic){
        this.styles[2] = italic;
    }
    public void setStrike(int strike){this.styles[3] = strike;}
    public void setAffectRange(byte affectRange){this.affectRange = affectRange;}

    public int getColour(){return this.styles[0];}
    public int getBold(){return this.styles[1];}
    public int getItalic(){return this.styles[2];}
    public int getStrike(){return this.styles[3];}
    public byte getAffectRange(){return this.affectRange;}
}
