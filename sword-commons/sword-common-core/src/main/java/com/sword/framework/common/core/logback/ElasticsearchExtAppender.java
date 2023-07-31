package com.sword.framework.common.core.logback;

import com.internetitem.logback.elasticsearch.ElasticsearchAppender;
import com.internetitem.logback.elasticsearch.config.ElasticsearchProperties;
import com.internetitem.logback.elasticsearch.config.Property;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by shujian.ou 2023/4/28 16:37
 */
public class ElasticsearchExtAppender extends ElasticsearchAppender {

    public void setEsLoggerInfo(EsLoggerInfo info) {
        ElasticsearchProperties elasticsearchProperties = new ElasticsearchProperties();
        elasticsearchProperties.addProperty(new Property("host", info.getHost(), false));
        elasticsearchProperties.addProperty(new Property("level", info.getLevel(), false));
        elasticsearchProperties.addProperty(new Property("thread", info.getThread(), false));
        elasticsearchProperties.addProperty(new Property("stacktrace", info.getStacktrace(), false));
        elasticsearchProperties.addProperty(new Property("pkg", info.getPkg(), false));
        super.setProperties(elasticsearchProperties);
    }

    @Getter
    @Setter
    public static class EsLoggerInfo implements Serializable {

        private String host;

        private String level;

        private String thread;

        private String stacktrace;

        private String pkg;

    }
}
