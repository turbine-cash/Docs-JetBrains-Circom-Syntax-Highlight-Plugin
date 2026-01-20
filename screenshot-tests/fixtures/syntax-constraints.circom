pragma circom 2.0.0;

template ConstraintDemo() {
    signal input a;
    signal input b;
    signal output c;
    signal witness;

    // Constraint assignment (signal flow from right to left)
    c <== a * b;

    // Reverse constraint (signal flow from left to right)
    a * b ==> c;

    // Equality constraint (quadratic constraint)
    a * b === c;

    // Non-constraint assignment (witness computation)
    witness <-- a + b;

    // Reverse non-constraint assignment
    a + b --> witness;
}
