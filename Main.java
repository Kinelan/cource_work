public class Main {

    //1-4 -> 2000 2250
    //5   -> 8000 9000

    public static void main(String[] argv){

        long start = System.nanoTime();
                InvertedIndex o = new InvertedIndex(4,
                new InputDir[]{
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\test\\neg\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\test\\pos\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\neg\\"),
                        new InputDir(2000, 2250, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\pos\\"),
                        new InputDir(8000, 9000, "D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\train\\unsup\\"),
                });
      long end = System.nanoTime();
      long time = end - start;
      System.out.println("Time = " + time + " ns ");
        return;
    }
}
