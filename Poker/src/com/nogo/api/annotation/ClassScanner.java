package com.nogo.api.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Component
public class ClassScanner {
  private final String basePackages = "com.nogo";

  private final ClassPathScanningCandidateComponentProvider scanner =
      new ClassPathScanningCandidateComponentProvider(true);

  public final Collection<Class<?>> findClasses() {
    final List<Class<?>> classes = new ArrayList<Class<?>>();

    for (final BeanDefinition candidate : scanner.findCandidateComponents(basePackages)) {
      classes.add(ClassUtils.resolveClassName(candidate.getBeanClassName(),
          ClassUtils.getDefaultClassLoader()));
    }

    return classes;
  }

  public ClassScanner withIncludeFilter(final TypeFilter filter) {
    scanner.addIncludeFilter(filter);
    return this;
  }

  public ClassScanner withAnnotationFilter(final Class<? extends Annotation> annotationClass) {
    return withIncludeFilter(new AnnotationTypeFilter(annotationClass));
  }
}