package store.parse;

import static store.constant.ExceptionMessage.FILE_COLUMN_SIZE_WRONG;

import java.util.List;

public interface Parser<T> {

    List<T> parse(List<String[]> table);

    default void validateSize(List<String[]> file, int expectLength) {
        for (String[] columns : file) {
            if (columns.length != expectLength) {
                throw new IllegalArgumentException(FILE_COLUMN_SIZE_WRONG);
            }
        }
    }
}
