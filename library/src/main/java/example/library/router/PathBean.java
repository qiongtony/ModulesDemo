package example.library.router;

public class PathBean {

    private String pathName;
    private Class<?> clazz;


    public PathBean() {
    }

    public PathBean(String pathName, Class<?> clazz) {
        this.pathName = pathName;
        this.clazz = clazz;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
