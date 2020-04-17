package model;

import java.util.function.Predicate;

public class PredicateFilters {

    private Predicate<Fichier> predicateLeft = f -> false;
    private Predicate<Fichier> predicateRight = f -> false;

    public PredicateFilters(Predicate<Fichier> pLeft, Predicate<Fichier> pRight) {
        predicateLeft = pLeft;
        predicateRight = pRight;
    }

    public Predicate<Fichier> getPredicateLeft() {
        return predicateLeft;
    }

    public Predicate<Fichier> getPredicateRight() {
        return predicateRight;
    }
}