pragma circom 2.0.0;

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

template Subtractor() {
    signal input minuend;
    signal input subtrahend;
    signal output difference;
    difference <== minuend - subtrahend;
}

function factorial(n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}

function fibonacci(n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

function power(base, exp) {
    var result = 1;
    for (var i = 0; i < exp; i++) {
        result = result * base;
    }
    return result;
}

template Calculator() {
    signal input a;
    signal input b;
    signal output sumResult;
    signal output productResult;
    signal output differenceResult;

    component add = Adder();
    component mul = Multiplier();
    component sub = Subtractor();

    add.a <== a;
    add.b <== b;
    sumResult <== add.sum;

    mul.x <== a;
    mul.y <== b;
    productResult <== mul.product;

    sub.minuend <== a;
    sub.subtrahend <== b;
    differenceResult <== sub.difference;
}

component main = Calculator();
