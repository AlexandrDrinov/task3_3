package task3_3;

public class Runner {

    public static void main(String[] args) {

        final long WORK_TIME = 15000;
        Artist artist = new Artist(new Pencil(), new Eraser());
        artist.workMiracles(WORK_TIME);
    }

}
