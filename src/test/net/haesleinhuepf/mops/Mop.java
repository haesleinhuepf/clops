package net.haesleinhuepf.mops;

public class Mop {
    String name;
    String op;
    String parameters;
    String description;
    String availableForDimensions;

    public Mop(String name_, String op_, String parameters_, String description_, String availableForDimensions_) {
        name = name_;
        op = op_;
        parameters = parameters_;
        description = description_;
        availableForDimensions = availableForDimensions_;
    }
}
