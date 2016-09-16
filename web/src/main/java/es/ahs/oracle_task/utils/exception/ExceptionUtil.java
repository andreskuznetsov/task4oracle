package es.ahs.oracle_task.utils.exception;

import java.util.List;

/**
 * Created by akuznetsov on 06.09.2016.
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static List getListOrNull(List checkedList) {
        if (checkedList != null && checkedList.size() > 0) return checkedList;
        return null;
    }

    public static void check(boolean found, int id) {
        check(found, "id=" + id);
    }

    public static <T> T check(T object, int id) {
        return check(object, "id=" + id);
    }

    public static <T> T check(T object, String msg) {
        check(object != null, msg);
        return object;
    }

    public static void check(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}