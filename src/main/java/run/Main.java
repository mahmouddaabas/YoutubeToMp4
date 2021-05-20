package run;

import utility.Converter;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Converter converter = new Converter();
        converter.convertVideo("https://www.youtube.com/watch?v=-Nw8ZLI9tX0");
    }
}
