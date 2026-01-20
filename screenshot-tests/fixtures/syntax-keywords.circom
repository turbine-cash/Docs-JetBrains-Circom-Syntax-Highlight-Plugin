pragma circom 2.0.0;

template Multiplier() {
    signal input a;
    signal input b;
    signal output out;

    var temp;
    component sub = SubComponent();

    out <== a * b;
}

function square(x) {
    return x * x;
}

component main = Multiplier();
