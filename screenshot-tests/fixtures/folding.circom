pragma circom 2.0.0;

/*
 * Multi-line comment block
 * demonstrating code folding
 * for documentation purposes
 */

template Adder() {
    signal input a;
    signal input b;
    signal output sum;
    sum <== a + b;
}

template Multiplier() {
    signal input x;
    signal input y;
    signal output product;
    product <== x * y;
}

template Calculator() {
    signal input num1;
    signal input num2;
    signal output added;
    signal output multiplied;

    component adder = Adder();
    component multiplier = Multiplier();

    adder.a <== num1;
    adder.b <== num2;
    added <== adder.sum;

    multiplier.x <== num1;
    multiplier.y <== num2;
    multiplied <== multiplier.product;
}

function factorial(n) {
    if (n <= 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}
