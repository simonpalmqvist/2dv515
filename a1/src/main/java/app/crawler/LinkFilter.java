package app.crawler;

public interface LinkFilter {

    public boolean matches(String link);
}
