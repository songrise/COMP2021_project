/*
  -*- coding : utf-8 -*-
  @FileName  : IsDocumentCriterion.java
 * @Author    : Ruixiang JIANG (Songrise)
 * @Time      : 2020-11-19
 * @Github    ：https://github.com/songrise
 * @Descriptions: isDocument criteria, singleton pattern.
 */

package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 * Class for isDocument criterion, it should not be subclass of simpleCriterion by the liskov substitution principle.
 * It use singleton pattern.
 */
public final class IsDocumentCriterion extends ConcreteCriterion {
    private final String name;

    private IsDocumentCriterion() {
        this.name = "isDocument";
    }

    private static Criterion instanceCriterion =null;

    @Override
    public boolean eval(File f) {
        return !f.isDirectory();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return a factory method to get this
     */
    public static Criterion getCriterion() {
        if (instanceCriterion == null) {
            instanceCriterion = new IsDocumentCriterion();
        }
        return instanceCriterion;
    }

    @Override
    public String toString() {
        return "isDocument";
    }
}
