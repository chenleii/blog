package com.chen.blog.core.sharedkernel.converter;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cl
 * @version 1.0
 * @since 2020/10/29
 */
public interface SourceFromTargetConverter<Source, Target> extends Converter {


    /**
     * source from target
     *
     * @param target target
     * @return source
     */
    Source from(Target target);

    /**
     * source list from target list
     *
     * @param targetList target list
     * @return source list
     */
    default List<Source> listFromList(List<? extends Target> targetList) {
        if (CollectionUtils.isEmpty(targetList)) {
            return Collections.emptyList();
        }

        return targetList.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }
    /**
     * source set from target set
     *
     * @param targetSet target set
     * @return source set
     */
    default Set<Source> setFromSet(Set<? extends Target> targetSet) {
        if (CollectionUtils.isEmpty(targetSet)) {
            return Collections.emptySet();
        }

        return targetSet.stream()
                .map(this::from)
                .collect(Collectors.toSet());
    }

    /**
     * source set from target list
     *
     * @param targetSet target set
     * @return source list
     */
    default List<Source> listFromSet(Set<? extends Target> targetSet) {
        if (CollectionUtils.isEmpty(targetSet)) {
            return Collections.emptyList();
        }

        return targetSet.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    /**
     * source list from target set
     *
     * @param targetList target list
     * @return source set
     */
    default Set<Source> setFromList(List<? extends Target> targetList) {
        if (CollectionUtils.isEmpty(targetList)) {
            return Collections.emptySet();
        }

        return targetList.stream()
                .map(this::from)
                .collect(Collectors.toSet());
    }
}
