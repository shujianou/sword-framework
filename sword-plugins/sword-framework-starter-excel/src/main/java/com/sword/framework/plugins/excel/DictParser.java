package com.sword.framework.plugins.excel;

/**
 * @author shujian.ou
 * @since 2023/2/16 17:11
 */
public interface DictParser {
    String parseDictDataValue(String type, String label);

    String getDictDataLabel(String type, String label);
}
