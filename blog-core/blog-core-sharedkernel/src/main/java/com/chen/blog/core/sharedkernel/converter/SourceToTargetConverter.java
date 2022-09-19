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
public interface SourceToTargetConverter<Source, Target> extends Converter {

    /**
     * source to target
     *
     * @param source source
     * @return target
     */
    Target to(Source source);

    /**
     * source list to target list
     *
     * @param sourceList source list
     * @return target list
     */
    default List<Target> listToList(List<? extends Source> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        return sourceList.stream()
                .map(this::to)
                .collect(Collectors.toList());
    }
    /**
     * source set to target set
     *
     * @param sourceSet source set
     * @return target set
     */
    default Set<Target> setToSet(Set<? extends Source> sourceSet) {
        if (CollectionUtils.isEmpty(sourceSet)) {
            return Collections.emptySet();
        }

        return sourceSet.stream()
                .map(this::to)
                .collect(Collectors.toSet());
    }

    /**
     * source set to target list
     *
     * @param sourceSet source set
     * @return target set
     */
    default List<Target> setToList(Set<? extends Source> sourceSet) {
        if (CollectionUtils.isEmpty(sourceSet)) {
            return Collections.emptyList();
        }

        return sourceSet.stream()
                .map(this::to)
                .collect(Collectors.toList());
    }


    /**
     * source list to target set
     *
     * @param sourceList source list
     * @return target set
     */
    default Set<Target> listToSet(List<? extends Source> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptySet();
        }

        return sourceList.stream()
                .map(this::to)
                .collect(Collectors.toSet());
    }
}
