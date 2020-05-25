public class Main {

    //1-4 -> 2000 2250
    //5   -> 8000 9000

    public static void main(String[] argv){


                InvertedIndex o = new InvertedIndex(1,
                new InputDir[]{
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\test\\neg\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\test\\pos\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\neg\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\pos\\"),
                        new InputDir(8000, 9000, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\train\\unsup\\"),
                });
    }
}
